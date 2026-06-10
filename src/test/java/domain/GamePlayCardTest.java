package domain;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GamePlayCardTest {
    private Game createStartedGame(Player... players) {
        Game game = new Game(List.of(players), new Deck(new Random()));
        game.setupGame();
        return game;
    }

    private void emptyDeck(Deck deck) {
        while (deck.size() > 0) {
            deck.draw();
        }
    }

    private void removeAll(Player player, CardType type) {
        while (player.hasCard(type)) {
            player.removeCard(type);
        }
    }

    private void assertCatPairComboSucceeds(CardType catType) {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, catType);
        while (!player2.getHand().isEmpty()) {
            player2.removeCard(player2.getHand().get(0).getType());
        }
        player1.addCard(new Card(catType));
        player1.addCard(new Card(catType));
        player2.addCard(new Card(CardType.SKIP));

        game.playCatPairCombo(catType, player2);

        assertEquals(2, game.getDiscardPile().size());
    }

    private void assertCatThreeComboSucceeds(CardType catType) {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, catType);
        while (!player2.getHand().isEmpty()) {
            player2.removeCard(player2.getHand().get(0).getType());
        }
        player1.addCard(new Card(catType));
        player1.addCard(new Card(catType));
        player1.addCard(new Card(catType));

        game.playCatThreeCombo(catType, player2, CardType.SKIP);

        assertEquals(3, game.getDiscardPile().size());
    }

    // G55
    @Test
    public void getDiscardPileReturnsEmptyListForNewGame() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        assertTrue(game.getDiscardPile().isEmpty());
    }

    // G56
    @Test
    public void getDiscardPileReturnsUnmodifiableList() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        List<Card> discardPile = game.getDiscardPile();

        assertThrows(UnsupportedOperationException.class, () -> {
            discardPile.add(new Card(CardType.SKIP));
        });
        assertEquals(0, game.getDiscardPile().size());
    }

    // G57
    @Test
    public void playCardThrowsExceptionWhenTypeIsNull() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        assertThrows(IllegalArgumentException.class, () -> {
            game.playCard(null);
        });
    }

    // G58
    @Test
    public void playCardThrowsExceptionWhenGameHasNotStarted() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        assertThrows(IllegalStateException.class, () -> {
            game.playCard(CardType.SKIP);
        });
    }

    // G59
    @Test
    public void playCardThrowsExceptionWhenGameIsOver() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();
        player1.eliminate();

        assertThrows(IllegalStateException.class, () -> {
            game.playCard(CardType.SKIP);
        });
    }

    // G60
    @Test
    public void playCardThrowsExceptionWhenCurrentPlayerDoesNotHaveCard() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        while (player1.hasCard(CardType.EXPLODING_KITTEN)) {
            player1.removeCard(CardType.EXPLODING_KITTEN);
        }

        assertThrows(IllegalStateException.class, () -> {
            game.playCard(CardType.EXPLODING_KITTEN);
        });
    }

    // G61
    @Test
    public void playCardRemovesMatchingCardFromCurrentPlayersHand() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);
        Card card = new Card(CardType.SKIP);

        game.setupGame();
        player1.addCard(card);

        game.playCard(CardType.SKIP);

        assertFalse(player1.getHand().contains(card));
    }

    // G62
    @Test
    public void playCardAddsRemovedCardToDiscardPile() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);
        Card card = new Card(CardType.SKIP);

        game.setupGame();
        player1.addCard(card);

        game.playCard(CardType.SKIP);

        assertEquals(1, game.getDiscardPile().size());
        assertTrue(game.getDiscardPile().contains(card));
    }

    // G63
    @Test
    public void playCardWithMultipleMatchingCardsRemovesOnlyOneCard() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();
        while (player1.hasCard(CardType.SKIP)) {
            player1.removeCard(CardType.SKIP);
        }
        player1.addCard(new Card(CardType.SKIP));
        player1.addCard(new Card(CardType.SKIP));

        game.playCard(CardType.SKIP);

        assertEquals(1, player1.countCardsOfType(CardType.SKIP));
        assertEquals(1, game.getDiscardPile().size());
    }

    // G64
    @Test
    public void playCardDoesNotAdvanceTurn() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();
        player1.addCard(new Card(CardType.SHUFFLE));

        game.playCard(CardType.SHUFFLE);

        assertEquals(player1, game.getCurrentPlayer());
    }

    // G65
    @Test
    public void playCardWithTacoCatMovesCardFromHandToDiscardPile() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);
        Card card = new Card(CardType.TACO_CAT);

        game.setupGame();
        while (player1.hasCard(CardType.TACO_CAT)) {
            player1.removeCard(CardType.TACO_CAT);
        }
        player1.addCard(card);

        game.playCard(CardType.TACO_CAT);

        assertFalse(player1.getHand().contains(card));
        assertTrue(game.getDiscardPile().contains(card));
    }

    // G66
    @Test
    public void playCardWithBeardCatMovesCardFromHandToDiscardPile() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);
        Card card = new Card(CardType.BEARD_CAT);

        game.setupGame();
        while (player1.hasCard(CardType.BEARD_CAT)) {
            player1.removeCard(CardType.BEARD_CAT);
        }
        player1.addCard(card);

        game.playCard(CardType.BEARD_CAT);

        assertFalse(player1.getHand().contains(card));
        assertTrue(game.getDiscardPile().contains(card));
    }

    // G67
    @Test
    public void playCardWithRainbowRalphingCatMovesCardFromHandToDiscardPile() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);
        Card card = new Card(CardType.RAINBOW_RALPHING_CAT);

        game.setupGame();
        while (player1.hasCard(CardType.RAINBOW_RALPHING_CAT)) {
            player1.removeCard(CardType.RAINBOW_RALPHING_CAT);
        }
        player1.addCard(card);

        game.playCard(CardType.RAINBOW_RALPHING_CAT);

        assertFalse(player1.getHand().contains(card));
        assertTrue(game.getDiscardPile().contains(card));
    }

    // G68
    @Test
    public void playCardWithHairyPotatoCatMovesCardFromHandToDiscardPile() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);
        Card card = new Card(CardType.HAIRY_POTATO_CAT);

        game.setupGame();
        while (player1.hasCard(CardType.HAIRY_POTATO_CAT)) {
            player1.removeCard(CardType.HAIRY_POTATO_CAT);
        }
        player1.addCard(card);

        game.playCard(CardType.HAIRY_POTATO_CAT);

        assertFalse(player1.getHand().contains(card));
        assertTrue(game.getDiscardPile().contains(card));
    }

    // G69
    @Test
    public void playCardWithCatCardDoesNotAdvanceTurn() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();
        player1.addCard(new Card(CardType.TACO_CAT));

        game.playCard(CardType.TACO_CAT);

        assertEquals(player1, game.getCurrentPlayer());
    }

    // G70
    @Test
    public void playCardWithCatCardDoesNotChangeDeckSize() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();
        player1.addCard(new Card(CardType.TACO_CAT));
        int deckSizeBeforePlay = deck.size();

        game.playCard(CardType.TACO_CAT);

        assertEquals(deckSizeBeforePlay, deck.size());
    }

    // G71
    @Test
    public void playCardWithCatCardDoesNotEliminateAnyPlayer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();
        player1.addCard(new Card(CardType.TACO_CAT));

        game.playCard(CardType.TACO_CAT);

        assertTrue(player1.isActive());
        assertTrue(player2.isActive());
    }

    // G72
    @Test
    public void playCardWithAttackMovesCardFromHandToDiscardPile() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);
        Card card = new Card(CardType.ATTACK);

        game.setupGame();
        while (player1.hasCard(CardType.ATTACK)) {
            player1.removeCard(CardType.ATTACK);
        }
        player1.addCard(card);

        game.playCard(CardType.ATTACK);

        assertFalse(player1.getHand().contains(card));
        assertTrue(game.getDiscardPile().contains(card));
    }

    // G73
    @Test
    public void playCardWithAttackAdvancesTurnToNextPlayer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();
        player1.addCard(new Card(CardType.ATTACK));

        game.playCard(CardType.ATTACK);

        assertEquals(player2, game.getCurrentPlayer());
    }

    // G74
    @Test
    public void attackedPlayerRemainsCurrentPlayerAfterFirstDraw() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();
        player1.addCard(new Card(CardType.ATTACK));
        game.playCard(CardType.ATTACK);
        deck.insertBottom(new Card(CardType.SKIP));

        game.drawCard();

        assertEquals(player2, game.getCurrentPlayer());
    }

    // G75
    @Test
    public void attackedPlayerAdvancesTurnAfterSecondDraw() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();
        player1.addCard(new Card(CardType.ATTACK));
        game.playCard(CardType.ATTACK);
        deck.insertBottom(new Card(CardType.SKIP));
        game.drawCard();
        deck.insertBottom(new Card(CardType.SHUFFLE));

        game.drawCard();

        assertEquals(player1, game.getCurrentPlayer());
    }

    // G76
    @Test
    public void playCardWithAttackSkipsEliminatedPlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3), deck);

        game.setupGame();
        player2.eliminate();
        player1.addCard(new Card(CardType.ATTACK));

        game.playCard(CardType.ATTACK);

        assertEquals(player3, game.getCurrentPlayer());
    }

    // G77
    @Test
    public void playCardWithAttackDoesNotChangeDeckSize() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();
        player1.addCard(new Card(CardType.ATTACK));
        int deckSizeBeforePlay = deck.size();

        game.playCard(CardType.ATTACK);

        assertEquals(deckSizeBeforePlay, deck.size());
    }

    // G78
    @Test
    public void playCardWithAttackDoesNotEliminateAnyPlayer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();
        player1.addCard(new Card(CardType.ATTACK));

        game.playCard(CardType.ATTACK);

        assertTrue(player1.isActive());
        assertTrue(player2.isActive());
    }

    // G79
    @Test
    public void attackStacksToFourTurnsWhenAttackedPlayerAttacksBeforeDrawing() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3), deck);

        game.setupGame();
        player1.addCard(new Card(CardType.ATTACK));
        player2.addCard(new Card(CardType.ATTACK));

        game.playCard(CardType.ATTACK);
        game.playCard(CardType.ATTACK);

        deck.insertBottom(new Card(CardType.SKIP));
        game.drawCard();
        assertEquals(player3, game.getCurrentPlayer());

        deck.insertBottom(new Card(CardType.SHUFFLE));
        game.drawCard();
        assertEquals(player3, game.getCurrentPlayer());

        deck.insertBottom(new Card(CardType.NOPE));
        game.drawCard();
        assertEquals(player3, game.getCurrentPlayer());

        deck.insertBottom(new Card(CardType.TACO_CAT));
        game.drawCard();
        assertEquals(player1, game.getCurrentPlayer());
    }

    // G80
    @Test
    public void attackStacksToThreeTurnsWhenAttackedPlayerAttacksAfterOneDraw() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3), deck);

        game.setupGame();
        player1.addCard(new Card(CardType.ATTACK));
        player2.addCard(new Card(CardType.ATTACK));

        game.playCard(CardType.ATTACK);
        deck.insertBottom(new Card(CardType.SKIP));
        game.drawCard();
        game.playCard(CardType.ATTACK);

        deck.insertBottom(new Card(CardType.SHUFFLE));
        game.drawCard();
        assertEquals(player3, game.getCurrentPlayer());

        deck.insertBottom(new Card(CardType.NOPE));
        game.drawCard();
        assertEquals(player3, game.getCurrentPlayer());

        deck.insertBottom(new Card(CardType.TACO_CAT));
        game.drawCard();
        assertEquals(player1, game.getCurrentPlayer());
    }

    // G81
    @Test
    public void explodingKittenEliminatesUnprotectedPlayerWithThreeActivePlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Game game = createStartedGame(player1, player2, player3);

        removeAll(player1, CardType.DEFUSE);
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertFalse(player1.isActive());
    }

    // G82
    @Test
    public void explodingKittenContinuesGameWithNextActivePlayer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Game game = createStartedGame(player1, player2, player3);

        removeAll(player1, CardType.DEFUSE);
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertEquals(player2, game.getCurrentPlayer());
    }

    // G83
    @Test
    public void explodingKittenEndsTwoPlayerGameWithOtherPlayerAsWinner() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.DEFUSE);
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertFalse(player1.isActive());
        assertTrue(game.isGameOver());
        assertEquals(player2, game.getWinner());
    }

    // G84
    @Test
    public void explodingKittenIsNotAddedToUnprotectedPlayersHand() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Game game = createStartedGame(player1, player2, player3);

        removeAll(player1, CardType.DEFUSE);
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertEquals(0, player1.countCardsOfType(CardType.EXPLODING_KITTEN));
    }

    // G85
    @Test
    public void zeroDefusesCannotPreventExplodingKittenElimination() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Game game = createStartedGame(player1, player2, player3);

        removeAll(player1, CardType.DEFUSE);
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertFalse(player1.isActive());
    }

    // G86
    @Test
    public void oneDefuseIsRemovedAndPreventsExplodingKittenElimination() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.DEFUSE);
        player1.addCard(new Card(CardType.DEFUSE));
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertEquals(0, player1.countCardsOfType(CardType.DEFUSE));
        assertTrue(player1.isActive());
    }

    // G87
    @Test
    public void oneDefuseRemainsAfterExplodingKittenWhenPlayerHasTwo() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.DEFUSE);
        player1.addCard(new Card(CardType.DEFUSE));
        player1.addCard(new Card(CardType.DEFUSE));
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertEquals(1, player1.countCardsOfType(CardType.DEFUSE));
    }

    // G88
    @Test
    public void usedDefuseIsAddedToDiscardPile() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.DEFUSE);
        Card defuse = new Card(CardType.DEFUSE);
        player1.addCard(defuse);
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertTrue(game.getDiscardPile().contains(defuse));
    }

    // G89
    @Test
    public void usingDefuseEndsTurnAndAdvancesToNextPlayer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertEquals(player2, game.getCurrentPlayer());
    }

    // G90
    @Test
    public void playingSkipWithoutSkipThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.SKIP);

        assertThrows(IllegalStateException.class, () -> game.playCard(CardType.SKIP));
    }

    // G91
    @Test
    public void playingOneSkipRemovesAndDiscardsIt() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.SKIP);
        Card skip = new Card(CardType.SKIP);
        player1.addCard(skip);

        game.playCard(CardType.SKIP);

        assertEquals(0, player1.countCardsOfType(CardType.SKIP));
        assertTrue(game.getDiscardPile().contains(skip));
    }

    // G92
    @Test
    public void playingSkipLeavesSecondSkipInHand() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.SKIP);
        player1.addCard(new Card(CardType.SKIP));
        player1.addCard(new Card(CardType.SKIP));

        game.playCard(CardType.SKIP);

        assertEquals(1, player1.countCardsOfType(CardType.SKIP));
    }

    // G93
    @Test
    public void playingSkipEndsTurnWithoutDrawing() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.SKIP));
        int deckSize = game.getDeck().size();

        game.playCard(CardType.SKIP);

        assertEquals(deckSize, game.getDeck().size());
        assertEquals(player2, game.getCurrentPlayer());
    }

    // G94
    @Test
    public void playingSkipAdvancesToNextActivePlayer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.SKIP));

        game.playCard(CardType.SKIP);

        assertEquals(player2, game.getCurrentPlayer());
    }

    // G95
    @Test
    public void playingSkipBypassesEliminatedNextPlayer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Game game = createStartedGame(player1, player2, player3);

        player1.addCard(new Card(CardType.SKIP));
        player2.eliminate();

        game.playCard(CardType.SKIP);

        assertEquals(player3, game.getCurrentPlayer());
    }

    // G96
    @Test
    public void playingSkipFromLastPlayerWrapsToFirstPlayer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Game game = createStartedGame(player1, player2, player3);

        game.endTurn();
        game.endTurn();
        player3.addCard(new Card(CardType.SKIP));

        game.playCard(CardType.SKIP);

        assertEquals(player1, game.getCurrentPlayer());
    }

    // G97, G98, G99
    @Test
    public void playingFavorWithoutTargetThrowsExceptionAndDoesNotConsumeFavor() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card favor = new Card(CardType.FAVOR);

        player1.addCard(favor);

        assertThrows(IllegalArgumentException.class, () -> game.playCard(CardType.FAVOR));
        assertTrue(player1.getHand().contains(favor));
        assertFalse(game.getDiscardPile().contains(favor));
    }

    // G100
    @Test
    public void playingFavorWithNullTargetThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.FAVOR));

        assertThrows(IllegalArgumentException.class, () -> game.playCard(CardType.FAVOR, null));
    }

    // G101
    @Test
    public void playingFavorWithTargetNotInGameThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.FAVOR));
        player3.addCard(new Card(CardType.SKIP));

        assertThrows(IllegalArgumentException.class, () -> game.playCard(CardType.FAVOR, player3));
    }

    // G102
    @Test
    public void playingFavorTargetingCurrentPlayerThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.FAVOR));

        assertThrows(IllegalArgumentException.class, () -> game.playCard(CardType.FAVOR, player1));
    }

    // G103
    @Test
    public void playingFavorTargetingPlayerWithNoCardsThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        while (!player2.getHand().isEmpty()) {
            player2.removeCard(player2.getHand().get(0).getType());
        }
        player1.addCard(new Card(CardType.FAVOR));

        assertThrows(IllegalStateException.class, () -> game.playCard(CardType.FAVOR, player2));
    }

    // G104
    @Test
    public void playingFavorWithoutFavorThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.FAVOR);

        assertThrows(IllegalStateException.class, () -> game.playCard(CardType.FAVOR, player2));
    }

    // G105
    @Test
    public void invalidTargetedFavorDoesNotDiscardFavor() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card favor = new Card(CardType.FAVOR);

        player1.addCard(favor);

        assertThrows(IllegalArgumentException.class, () -> game.playCard(CardType.FAVOR, player1));
        assertTrue(player1.getHand().contains(favor));
        assertFalse(game.getDiscardPile().contains(favor));
    }

    // G106
    @Test
    public void validFavorRemovesAndDiscardsFavor() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card favor = new Card(CardType.FAVOR);

        player1.addCard(favor);

        game.playCard(CardType.FAVOR, player2);

        assertFalse(player1.getHand().contains(favor));
        assertTrue(game.getDiscardPile().contains(favor));
    }

    // G107
    @Test
    public void validFavorTransfersOneCardFromTargetToCurrentPlayer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card targetCard = new Card(CardType.SHUFFLE);

        while (!player2.getHand().isEmpty()) {
            player2.removeCard(player2.getHand().get(0).getType());
        }
        player1.addCard(new Card(CardType.FAVOR));
        player2.addCard(targetCard);

        game.playCard(CardType.FAVOR, player2);

        assertFalse(player2.getHand().contains(targetCard));
        assertTrue(player1.getHand().contains(targetCard));
    }

    // G108
    @Test
    public void validFavorTransfersFirstCardFromTargetPlayer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card firstCard = new Card(CardType.SHUFFLE);
        Card secondCard = new Card(CardType.ATTACK);

        while (!player2.getHand().isEmpty()) {
            player2.removeCard(player2.getHand().get(0).getType());
        }
        player1.addCard(new Card(CardType.FAVOR));
        player2.addCard(firstCard);
        player2.addCard(secondCard);

        game.playCard(CardType.FAVOR, player2);

        assertTrue(player1.getHand().contains(firstCard));
        assertFalse(player1.getHand().contains(secondCard));
        assertFalse(player2.getHand().contains(firstCard));
        assertTrue(player2.getHand().contains(secondCard));
    }

    // G109
    @Test
    public void validFavorDoesNotAdvanceTurn() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.FAVOR));

        game.playCard(CardType.FAVOR, player2);

        assertEquals(player1, game.getCurrentPlayer());
    }

    // G110, G111, G112
    @Test
    public void playingTradeWithoutTargetThrowsExceptionAndDoesNotConsumeTrade() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card trade = new Card(CardType.TRADE);

        player1.addCard(trade);

        assertThrows(IllegalArgumentException.class, () -> game.playCard(CardType.TRADE));
        assertTrue(player1.getHand().contains(trade));
        assertFalse(game.getDiscardPile().contains(trade));
    }

    // G113
    @Test
    public void playingTradeWithNullTargetThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.TRADE));

        assertThrows(IllegalArgumentException.class, () -> game.playCard(CardType.TRADE, null));
    }

    // G114
    @Test
    public void playingTradeWithTargetNotInGameThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.TRADE));
        player1.addCard(new Card(CardType.SHUFFLE));
        player3.addCard(new Card(CardType.SKIP));

        assertThrows(IllegalArgumentException.class, () -> game.playCard(CardType.TRADE, player3));
    }

    // G115
    @Test
    public void playingTradeTargetingCurrentPlayerThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.TRADE));
        player1.addCard(new Card(CardType.SHUFFLE));

        assertThrows(IllegalArgumentException.class, () -> game.playCard(CardType.TRADE, player1));
    }

    // G116
    @Test
    public void playingTradeWithoutTradeThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.TRADE);

        assertThrows(IllegalStateException.class, () -> game.playCard(CardType.TRADE, player2));
    }

    // G117
    @Test
    public void playingTradeWithNoRemainingCardToSwapThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        while (!player1.getHand().isEmpty()) {
            player1.removeCard(player1.getHand().get(0).getType());
        }
        player1.addCard(new Card(CardType.TRADE));

        assertThrows(IllegalStateException.class, () -> game.playCard(CardType.TRADE, player2));
    }

    // G118
    @Test
    public void playingTradeTargetingPlayerWithNoCardsThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        while (!player2.getHand().isEmpty()) {
            player2.removeCard(player2.getHand().get(0).getType());
        }
        player1.addCard(new Card(CardType.TRADE));
        player1.addCard(new Card(CardType.SHUFFLE));

        assertThrows(IllegalStateException.class, () -> game.playCard(CardType.TRADE, player2));
    }

    // G119
    @Test
    public void invalidTargetedTradeDoesNotDiscardTrade() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card trade = new Card(CardType.TRADE);

        player1.addCard(trade);
        player1.addCard(new Card(CardType.SHUFFLE));

        assertThrows(IllegalArgumentException.class, () -> game.playCard(CardType.TRADE, player1));
        assertTrue(player1.getHand().contains(trade));
        assertFalse(game.getDiscardPile().contains(trade));
    }

    // G120
    @Test
    public void validTradeRemovesAndDiscardsTrade() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card trade = new Card(CardType.TRADE);

        player1.addCard(trade);
        player1.addCard(new Card(CardType.SHUFFLE));

        game.playCard(CardType.TRADE, player2);

        assertFalse(player1.getHand().contains(trade));
        assertTrue(game.getDiscardPile().contains(trade));
    }

    // G121
    @Test
    public void validTradeSwapsOneCardBetweenCurrentPlayerAndTargetPlayer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card currentPlayerCard = new Card(CardType.SHUFFLE);
        Card targetPlayerCard = new Card(CardType.ATTACK);

        while (!player1.getHand().isEmpty()) {
            player1.removeCard(player1.getHand().get(0).getType());
        }
        while (!player2.getHand().isEmpty()) {
            player2.removeCard(player2.getHand().get(0).getType());
        }
        player1.addCard(new Card(CardType.TRADE));
        player1.addCard(currentPlayerCard);
        player2.addCard(targetPlayerCard);

        game.playCard(CardType.TRADE, player2);

        assertTrue(player1.getHand().contains(targetPlayerCard));
        assertTrue(player2.getHand().contains(currentPlayerCard));
        assertFalse(player1.getHand().contains(currentPlayerCard));
        assertFalse(player2.getHand().contains(targetPlayerCard));
    }

    // G122
    @Test
    public void validTradeSwapsFirstRemainingCurrentCardWithFirstTargetCard() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card firstCurrentCard = new Card(CardType.SHUFFLE);
        Card secondCurrentCard = new Card(CardType.SKIP);
        Card firstTargetCard = new Card(CardType.ATTACK);
        Card secondTargetCard = new Card(CardType.NOPE);

        while (!player1.getHand().isEmpty()) {
            player1.removeCard(player1.getHand().get(0).getType());
        }
        while (!player2.getHand().isEmpty()) {
            player2.removeCard(player2.getHand().get(0).getType());
        }
        player1.addCard(new Card(CardType.TRADE));
        player1.addCard(firstCurrentCard);
        player1.addCard(secondCurrentCard);
        player2.addCard(firstTargetCard);
        player2.addCard(secondTargetCard);

        game.playCard(CardType.TRADE, player2);

        assertTrue(player1.getHand().contains(firstTargetCard));
        assertTrue(player1.getHand().contains(secondCurrentCard));
        assertFalse(player1.getHand().contains(firstCurrentCard));
        assertTrue(player2.getHand().contains(firstCurrentCard));
        assertTrue(player2.getHand().contains(secondTargetCard));
        assertFalse(player2.getHand().contains(firstTargetCard));
    }

    // G123
    @Test
    public void validTradeDoesNotSwapPlayedTradeCard() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card trade = new Card(CardType.TRADE);

        player1.addCard(trade);
        player1.addCard(new Card(CardType.SHUFFLE));

        game.playCard(CardType.TRADE, player2);

        assertTrue(game.getDiscardPile().contains(trade));
        assertFalse(player1.getHand().contains(trade));
        assertFalse(player2.getHand().contains(trade));
    }

    // G124
    @Test
    public void validTradeDoesNotAdvanceTurn() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.TRADE));
        player1.addCard(new Card(CardType.SHUFFLE));

        game.playCard(CardType.TRADE, player2);

        assertEquals(player1, game.getCurrentPlayer());
    }

    // G125
    @Test
    public void validTradeDoesNotChangeDeckSize() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.TRADE));
        player1.addCard(new Card(CardType.SHUFFLE));
        int deckSizeBeforeTrade = game.getDeck().size();

        game.playCard(CardType.TRADE, player2);

        assertEquals(deckSizeBeforeTrade, game.getDeck().size());
    }

    // G126
    @Test
    public void validTradeDoesNotEliminatePlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.TRADE));
        player1.addCard(new Card(CardType.SHUFFLE));

        game.playCard(CardType.TRADE, player2);

        assertTrue(player1.isActive());
        assertTrue(player2.isActive());
    }

    // G127
    @Test
    public void playingMarkWithNullTargetThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.MARK));

        assertThrows(IllegalArgumentException.class, () -> game.playMark(null));
    }

    // G128
    @Test
    public void playingMarkWithTargetNotInGameThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.MARK));
        player3.addCard(new Card(CardType.SKIP));

        assertThrows(IllegalArgumentException.class, () -> game.playMark(player3));
    }

    // G129
    @Test
    public void playingMarkTargetingCurrentPlayerThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.MARK));

        assertThrows(IllegalArgumentException.class, () -> game.playMark(player1));
    }

    // G130
    @Test
    public void playingMarkWithoutMarkThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.MARK);

        assertThrows(IllegalStateException.class, () -> game.playMark(player2));
    }

    // G131
    @Test
    public void playingMarkTargetingPlayerWithNoCardsThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        while (!player2.getHand().isEmpty()) {
            player2.removeCard(player2.getHand().get(0).getType());
        }
        player1.addCard(new Card(CardType.MARK));

        assertThrows(IllegalStateException.class, () -> game.playMark(player2));
    }

    // G132
    @Test
    public void invalidMarkDoesNotDiscardMark() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card mark = new Card(CardType.MARK);

        player1.addCard(mark);

        assertThrows(IllegalArgumentException.class, () -> game.playMark(player1));
        assertTrue(player1.getHand().contains(mark));
        assertFalse(game.getDiscardPile().contains(mark));
    }

    // G133
    @Test
    public void validMarkRemovesMarkFromCurrentPlayersHand() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card mark = new Card(CardType.MARK);

        player1.addCard(mark);

        game.playMark(player2);

        assertFalse(player1.getHand().contains(mark));
    }

    // G134
    @Test
    public void validMarkAddsMarkToDiscardPile() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card mark = new Card(CardType.MARK);

        player1.addCard(mark);

        game.playMark(player2);

        assertTrue(game.getDiscardPile().contains(mark));
    }

    // G135
    @Test
    public void validMarkReturnsFirstCardFromTargetPlayersHand() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card firstCard = new Card(CardType.SHUFFLE);
        Card secondCard = new Card(CardType.ATTACK);

        while (!player2.getHand().isEmpty()) {
            player2.removeCard(player2.getHand().get(0).getType());
        }
        player1.addCard(new Card(CardType.MARK));
        player2.addCard(firstCard);
        player2.addCard(secondCard);

        Card revealedCard = game.playMark(player2);

        assertSame(firstCard, revealedCard);
    }

    // G136
    @Test
    public void validMarkLeavesRevealedCardInTargetPlayersHand() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card firstCard = new Card(CardType.SHUFFLE);

        while (!player2.getHand().isEmpty()) {
            player2.removeCard(player2.getHand().get(0).getType());
        }
        player1.addCard(new Card(CardType.MARK));
        player2.addCard(firstCard);

        Card revealedCard = game.playMark(player2);

        assertTrue(player2.getHand().contains(revealedCard));
        assertEquals(1, player2.getHand().size());
    }

    // G137
    @Test
    public void validMarkDoesNotAdvanceTurn() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.MARK));

        game.playMark(player2);

        assertEquals(player1, game.getCurrentPlayer());
    }

    // G138
    @Test
    public void validMarkDoesNotChangeDeckSize() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.MARK));
        int deckSizeBeforeMark = game.getDeck().size();

        game.playMark(player2);

        assertEquals(deckSizeBeforeMark, game.getDeck().size());
    }

    // G139
    @Test
    public void validMarkDoesNotEliminatePlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.MARK));

        game.playMark(player2);

        assertTrue(player1.isActive());
        assertTrue(player2.isActive());
    }

    // G140
    @Test
    public void catPairComboWithNullCardTypeThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        assertThrows(IllegalArgumentException.class, () -> {
            game.playCatPairCombo(null, player2);
        });
    }

    // G141
    @Test
    public void catPairComboWithNonCatCardTypeThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        assertThrows(IllegalArgumentException.class, () -> {
            game.playCatPairCombo(CardType.ATTACK, player2);
        });
    }

    // G142
    @Test
    public void catPairComboWithNullTargetThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));

        assertThrows(IllegalArgumentException.class, () -> {
            game.playCatPairCombo(CardType.TACO_CAT, null);
        });
    }

    // G143
    @Test
    public void catPairComboWithTargetNotInGameThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));
        player3.addCard(new Card(CardType.SKIP));

        assertThrows(IllegalArgumentException.class, () -> {
            game.playCatPairCombo(CardType.TACO_CAT, player3);
        });
    }

    // G144
    @Test
    public void catPairComboTargetingCurrentPlayerThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));

        assertThrows(IllegalArgumentException.class, () -> {
            game.playCatPairCombo(CardType.TACO_CAT, player1);
        });
    }

    // G145
    @Test
    public void catPairComboWithFewerThanTwoMatchingCatCardsThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.TACO_CAT);
        player1.addCard(new Card(CardType.TACO_CAT));

        assertThrows(IllegalStateException.class, () -> {
            game.playCatPairCombo(CardType.TACO_CAT, player2);
        });
    }

    // G146
    @Test
    public void catPairComboTargetingPlayerWithNoCardsThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        while (!player2.getHand().isEmpty()) {
            player2.removeCard(player2.getHand().get(0).getType());
        }
        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));

        assertThrows(IllegalStateException.class, () -> {
            game.playCatPairCombo(CardType.TACO_CAT, player2);
        });
    }

    // G147
    @Test
    public void invalidCatPairComboDoesNotConsumeOrDiscardCatCards() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card firstCat = new Card(CardType.TACO_CAT);
        Card secondCat = new Card(CardType.TACO_CAT);

        removeAll(player1, CardType.TACO_CAT);
        player1.addCard(firstCat);
        player1.addCard(secondCat);

        assertThrows(IllegalArgumentException.class, () -> {
            game.playCatPairCombo(CardType.TACO_CAT, player1);
        });
        assertTrue(player1.getHand().contains(firstCat));
        assertTrue(player1.getHand().contains(secondCat));
        assertFalse(game.getDiscardPile().contains(firstCat));
        assertFalse(game.getDiscardPile().contains(secondCat));
    }

    // G148
    @Test
    public void validCatPairComboRemovesExactlyTwoMatchingCatCards() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.TACO_CAT);
        while (!player2.getHand().isEmpty()) {
            player2.removeCard(player2.getHand().get(0).getType());
        }
        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));
        player2.addCard(new Card(CardType.SKIP));

        game.playCatPairCombo(CardType.TACO_CAT, player2);

        assertEquals(1, player1.countCardsOfType(CardType.TACO_CAT));
    }

    // G149
    @Test
    public void validCatPairComboAddsBothCatCardsToDiscardPile() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card firstCat = new Card(CardType.TACO_CAT);
        Card secondCat = new Card(CardType.TACO_CAT);

        removeAll(player1, CardType.TACO_CAT);
        while (!player2.getHand().isEmpty()) {
            player2.removeCard(player2.getHand().get(0).getType());
        }
        player1.addCard(firstCat);
        player1.addCard(secondCat);
        player2.addCard(new Card(CardType.SKIP));

        game.playCatPairCombo(CardType.TACO_CAT, player2);

        assertTrue(game.getDiscardPile().contains(firstCat));
        assertTrue(game.getDiscardPile().contains(secondCat));
    }

    // G150
    @Test
    public void catPairComboWithTacoCatSucceeds() {
        assertCatPairComboSucceeds(CardType.TACO_CAT);
    }

    // G151
    @Test
    public void catPairComboWithBeardCatSucceeds() {
        assertCatPairComboSucceeds(CardType.BEARD_CAT);
    }

    // G152
    @Test
    public void catPairComboWithRainbowRalphingCatSucceeds() {
        assertCatPairComboSucceeds(CardType.RAINBOW_RALPHING_CAT);
    }

    // G153
    @Test
    public void catPairComboWithHairyPotatoCatSucceeds() {
        assertCatPairComboSucceeds(CardType.HAIRY_POTATO_CAT);
    }

    // G154, G155, G156
    @Test
    public void validCatPairComboTransfersFirstTargetCardToCurrentPlayer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card firstTargetCard = new Card(CardType.ATTACK);
        Card secondTargetCard = new Card(CardType.NOPE);

        removeAll(player1, CardType.TACO_CAT);
        while (!player2.getHand().isEmpty()) {
            player2.removeCard(player2.getHand().get(0).getType());
        }
        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));
        player2.addCard(firstTargetCard);
        player2.addCard(secondTargetCard);

        game.playCatPairCombo(CardType.TACO_CAT, player2);

        assertTrue(player1.getHand().contains(firstTargetCard));
        assertFalse(player1.getHand().contains(secondTargetCard));
        assertFalse(player2.getHand().contains(firstTargetCard));
        assertTrue(player2.getHand().contains(secondTargetCard));
    }

    // G157
    @Test
    public void validCatPairComboDoesNotAdvanceTurn() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));

        game.playCatPairCombo(CardType.TACO_CAT, player2);

        assertEquals(player1, game.getCurrentPlayer());
    }

    // G158
    @Test
    public void validCatPairComboDoesNotChangeDeckSize() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));
        int deckSizeBeforeCombo = game.getDeck().size();

        game.playCatPairCombo(CardType.TACO_CAT, player2);

        assertEquals(deckSizeBeforeCombo, game.getDeck().size());
    }

    // G159
    @Test
    public void validCatPairComboDoesNotEliminatePlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));

        game.playCatPairCombo(CardType.TACO_CAT, player2);

        assertTrue(player1.isActive());
        assertTrue(player2.isActive());
    }

    // G160
    @Test
    public void zeroShieldsCannotPreventEliminationWithoutDefuse() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Game game = createStartedGame(player1, player2, player3);

        removeAll(player1, CardType.DEFUSE);
        removeAll(player1, CardType.SHIELD);
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertFalse(player1.isActive());
    }

    // G161
    @Test
    public void oneShieldIsRemovedAndPreventsExplodingKittenElimination() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.DEFUSE);
        player1.addCard(new Card(CardType.SHIELD));
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertEquals(0, player1.countCardsOfType(CardType.SHIELD));
        assertTrue(player1.isActive());
    }

    // G162
    @Test
    public void oneShieldRemainsAfterExplodingKittenWhenPlayerHasTwo() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.DEFUSE);
        player1.addCard(new Card(CardType.SHIELD));
        player1.addCard(new Card(CardType.SHIELD));
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertEquals(1, player1.countCardsOfType(CardType.SHIELD));
    }

    // G163
    @Test
    public void usedShieldIsAddedToDiscardPile() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.DEFUSE);
        Card shield = new Card(CardType.SHIELD);
        player1.addCard(shield);
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertTrue(game.getDiscardPile().contains(shield));
    }

    // G164
    @Test
    public void usingShieldEndsTurnAndAdvancesToNextPlayer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.DEFUSE);
        player1.addCard(new Card(CardType.SHIELD));
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertEquals(player2, game.getCurrentPlayer());
    }

    // G165
    @Test
    public void defuseTakesPriorityWhenPlayerAlsoHasShield() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.DEFUSE);
        player1.addCard(new Card(CardType.DEFUSE));
        player1.addCard(new Card(CardType.SHIELD));
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertEquals(0, player1.countCardsOfType(CardType.DEFUSE));
        assertEquals(1, player1.countCardsOfType(CardType.SHIELD));
    }

    // G166
    @Test
    public void catThreeComboWithNullCardTypeThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        assertThrows(IllegalArgumentException.class, () -> {
            game.playCatThreeCombo(null, player2, CardType.SKIP);
        });
    }

    // G167
    @Test
    public void catThreeComboWithNonCatCardTypeThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        assertThrows(IllegalArgumentException.class, () -> {
            game.playCatThreeCombo(CardType.ATTACK, player2, CardType.SKIP);
        });
    }

    // G168
    @Test
    public void catThreeComboWithNullTargetThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));

        assertThrows(IllegalArgumentException.class, () -> {
            game.playCatThreeCombo(CardType.TACO_CAT, null, CardType.SKIP);
        });
    }

    // G169
    @Test
    public void catThreeComboWithTargetNotInGameThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));

        assertThrows(IllegalArgumentException.class, () -> {
            game.playCatThreeCombo(CardType.TACO_CAT, player3, CardType.SKIP);
        });
    }

    // G170
    @Test
    public void catThreeComboTargetingCurrentPlayerThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));

        assertThrows(IllegalArgumentException.class, () -> {
            game.playCatThreeCombo(CardType.TACO_CAT, player1, CardType.SKIP);
        });
    }

    // G171
    @Test
    public void catThreeComboWithNullRequestedTypeThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));

        assertThrows(IllegalArgumentException.class, () -> {
            game.playCatThreeCombo(CardType.TACO_CAT, player2, null);
        });
    }

    // G172
    @Test
    public void catThreeComboWithFewerThanThreeMatchingCatCardsThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.TACO_CAT);
        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));

        assertThrows(IllegalStateException.class, () -> {
            game.playCatThreeCombo(CardType.TACO_CAT, player2, CardType.SKIP);
        });
    }

    // G173
    @Test
    public void invalidCatThreeComboDoesNotConsumeOrDiscardCatCards() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card firstCat = new Card(CardType.TACO_CAT);
        Card secondCat = new Card(CardType.TACO_CAT);
        Card thirdCat = new Card(CardType.TACO_CAT);

        removeAll(player1, CardType.TACO_CAT);
        player1.addCard(firstCat);
        player1.addCard(secondCat);
        player1.addCard(thirdCat);

        assertThrows(IllegalArgumentException.class, () -> {
            game.playCatThreeCombo(CardType.TACO_CAT, player1, CardType.SKIP);
        });
        assertTrue(player1.getHand().contains(firstCat));
        assertTrue(player1.getHand().contains(secondCat));
        assertTrue(player1.getHand().contains(thirdCat));
        assertFalse(game.getDiscardPile().contains(firstCat));
        assertFalse(game.getDiscardPile().contains(secondCat));
        assertFalse(game.getDiscardPile().contains(thirdCat));
    }

    // G174
    @Test
    public void validCatThreeComboRemovesExactlyThreeMatchingCatCards() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.TACO_CAT);
        while (!player2.getHand().isEmpty()) {
            player2.removeCard(player2.getHand().get(0).getType());
        }
        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));

        game.playCatThreeCombo(CardType.TACO_CAT, player2, CardType.SKIP);

        assertEquals(1, player1.countCardsOfType(CardType.TACO_CAT));
    }

    // G175
    @Test
    public void validCatThreeComboAddsAllThreeCatCardsToDiscardPile() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card firstCat = new Card(CardType.TACO_CAT);
        Card secondCat = new Card(CardType.TACO_CAT);
        Card thirdCat = new Card(CardType.TACO_CAT);

        removeAll(player1, CardType.TACO_CAT);
        while (!player2.getHand().isEmpty()) {
            player2.removeCard(player2.getHand().get(0).getType());
        }
        player1.addCard(firstCat);
        player1.addCard(secondCat);
        player1.addCard(thirdCat);

        game.playCatThreeCombo(CardType.TACO_CAT, player2, CardType.SKIP);

        assertTrue(game.getDiscardPile().contains(firstCat));
        assertTrue(game.getDiscardPile().contains(secondCat));
        assertTrue(game.getDiscardPile().contains(thirdCat));
    }

    // G176
    @Test
    public void catThreeComboWithTacoCatSucceeds() {
        assertCatThreeComboSucceeds(CardType.TACO_CAT);
    }

    // G177
    @Test
    public void catThreeComboWithBeardCatSucceeds() {
        assertCatThreeComboSucceeds(CardType.BEARD_CAT);
    }

    // G178
    @Test
    public void catThreeComboWithRainbowRalphingCatSucceeds() {
        assertCatThreeComboSucceeds(CardType.RAINBOW_RALPHING_CAT);
    }

    // G179
    @Test
    public void catThreeComboWithHairyPotatoCatSucceeds() {
        assertCatThreeComboSucceeds(CardType.HAIRY_POTATO_CAT);
    }

    // G180, G181, G182
    @Test
    public void validCatThreeComboTransfersRequestedCardToCurrentPlayer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card firstRequestedCard = new Card(CardType.ATTACK);
        Card secondRequestedCard = new Card(CardType.ATTACK);

        removeAll(player1, CardType.TACO_CAT);
        while (!player2.getHand().isEmpty()) {
            player2.removeCard(player2.getHand().get(0).getType());
        }
        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));
        player2.addCard(firstRequestedCard);
        player2.addCard(secondRequestedCard);

        game.playCatThreeCombo(CardType.TACO_CAT, player2, CardType.ATTACK);

        assertTrue(player1.getHand().contains(firstRequestedCard));
        assertFalse(player2.getHand().contains(firstRequestedCard));
        assertTrue(player2.getHand().contains(secondRequestedCard));
    }

    // G183
    @Test
    public void catThreeComboWithRequestedCardAbsentSucceedsWithoutTransfer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.TACO_CAT);
        while (!player2.getHand().isEmpty()) {
            player2.removeCard(player2.getHand().get(0).getType());
        }
        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));
        int currentPlayerHandSize = player1.getHand().size();

        game.playCatThreeCombo(CardType.TACO_CAT, player2, CardType.SKIP);

        assertEquals(currentPlayerHandSize - 3, player1.getHand().size());
        assertTrue(player2.getHand().isEmpty());
        assertEquals(3, game.getDiscardPile().size());
    }

    // G184
    @Test
    public void validCatThreeComboDoesNotAdvanceTurn() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));

        game.playCatThreeCombo(CardType.TACO_CAT, player2, CardType.SKIP);

        assertEquals(player1, game.getCurrentPlayer());
    }

    // G185
    @Test
    public void validCatThreeComboDoesNotChangeDeckSize() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));
        int deckSizeBeforeCombo = game.getDeck().size();

        game.playCatThreeCombo(CardType.TACO_CAT, player2, CardType.SKIP);

        assertEquals(deckSizeBeforeCombo, game.getDeck().size());
    }

    // G186
    @Test
    public void validCatThreeComboDoesNotEliminatePlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));
        player1.addCard(new Card(CardType.TACO_CAT));

        game.playCatThreeCombo(CardType.TACO_CAT, player2, CardType.SKIP);

        assertTrue(player1.isActive());
        assertTrue(player2.isActive());
    }

    @Test
    public void playingShuffleWithoutShuffleThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        removeAll(player1, CardType.SHUFFLE);

        assertThrows(IllegalStateException.class, () -> {
            game.playCard(CardType.SHUFFLE);
        });
    }

    @Test
    public void PlayShuffle_RemovesOneShuffleFromHand() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        removeAll(player1, CardType.SHUFFLE);
        // give current player one SHUFFLE
        player1.addCard(new Card(CardType.SHUFFLE));
        game.playCard(CardType.SHUFFLE);
        assertEquals(0, player1.countCardsOfType(CardType.SHUFFLE));
    }

    @Test
    public void PlayShuffle_AddsShuffleToDiscardPile() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        // give current player one SHUFFLE
        Card shuffle = new Card(CardType.SHUFFLE);
        player1.addCard(shuffle);
        game.playCard(CardType.SHUFFLE);
        assertTrue(game.getDiscardPile().contains(shuffle));
    }

    @Test
    public void PlayShuffle_DeckSizeDoesNotChange() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        removeAll(player1, CardType.SHUFFLE);
        player1.addCard(new Card(CardType.SHUFFLE));
        int deckSizeBeforeShuffle = game.getDeck().size();
        game.playCard(CardType.SHUFFLE);
        assertEquals(deckSizeBeforeShuffle, game.getDeck().size());
    }

    @Test
    public void PlayShuffle_CurrentPlayerDoesNotChange() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        removeAll(player1, CardType.SHUFFLE);
        player1.addCard(new Card(CardType.SHUFFLE));
        game.playCard(CardType.SHUFFLE);
        assertEquals(player1, game.getCurrentPlayer());
    }

    @Test
    public void playingShuffleLeavesSecondShuffleInHand() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        removeAll(player1, CardType.SHUFFLE);
        player1.addCard(new Card(CardType.SHUFFLE));
        player1.addCard(new Card(CardType.SHUFFLE));
        game.playCard(CardType.SHUFFLE);
        assertEquals(1, player1.countCardsOfType(CardType.SHUFFLE));
    }

    @Test
    public void PlayShuffle_DeckCardCountsDoNotChange() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        player1.addCard(new Card(CardType.SHUFFLE));
        int attackCount = game.getDeck().amtCardType(CardType.ATTACK);
        int shuffleCount = game.getDeck().amtCardType(CardType.SHUFFLE);
        int skipCount = game.getDeck().amtCardType(CardType.SKIP);
        int seeTheFutureCount = game.getDeck().amtCardType(CardType.SEE_THE_FUTURE);
        int nopeCount = game.getDeck().amtCardType(CardType.NOPE);
        int tacoCatCount = game.getDeck().amtCardType(CardType.TACO_CAT);
        int beardCatCount = game.getDeck().amtCardType(CardType.BEARD_CAT);
        int rainbowCatCount = game.getDeck().amtCardType(CardType.RAINBOW_RALPHING_CAT);
        int potatoCatCount = game.getDeck().amtCardType(CardType.HAIRY_POTATO_CAT);
        int explodingKittenCount = game.getDeck().amtCardType(CardType.EXPLODING_KITTEN);
        int defuseCount = game.getDeck().amtCardType(CardType.DEFUSE);

        game.playCard(CardType.SHUFFLE);
        assertEquals(attackCount, game.getDeck().amtCardType(CardType.ATTACK));
        assertEquals(shuffleCount, game.getDeck().amtCardType(CardType.SHUFFLE));
        assertEquals(skipCount, game.getDeck().amtCardType(CardType.SKIP));
        assertEquals(seeTheFutureCount, game.getDeck().amtCardType(CardType.SEE_THE_FUTURE));
        assertEquals(nopeCount, game.getDeck().amtCardType(CardType.NOPE));
        assertEquals(tacoCatCount, game.getDeck().amtCardType(CardType.TACO_CAT));
        assertEquals(beardCatCount, game.getDeck().amtCardType(CardType.BEARD_CAT));
        assertEquals(rainbowCatCount, game.getDeck().amtCardType(CardType.RAINBOW_RALPHING_CAT));
        assertEquals(potatoCatCount, game.getDeck().amtCardType(CardType.HAIRY_POTATO_CAT));
        assertEquals(explodingKittenCount, game.getDeck().amtCardType(CardType.EXPLODING_KITTEN));
        assertEquals(defuseCount, game.getDeck().amtCardType(CardType.DEFUSE));
    }

    @Test
    public void playingShuffleDoesNotEliminateAnyPlayer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        removeAll(player1, CardType.SHUFFLE);
        player1.addCard(new Card(CardType.SHUFFLE));
        game.playCard(CardType.SHUFFLE);
        assertTrue(player1.isActive());
        assertTrue(player2.isActive());
    }

    @Test
    public void playingSeeTheFutureWithoutSeeTheFutureThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.SEE_THE_FUTURE);

        assertThrows(IllegalStateException.class, () -> {game.playSeeTheFuture();});
    }

    @Test
    public void playingOneSeeTheFutureRemovesFromHand() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        removeAll(player1, CardType.SEE_THE_FUTURE);
        Card seeTheFuture = new Card(CardType.SEE_THE_FUTURE);
        player1.addCard(seeTheFuture);
        game.playSeeTheFuture();
        assertEquals(0, player1.countCardsOfType(CardType.SEE_THE_FUTURE));
    }

    @Test
    public void playingOneSeeTheFutureDiscardsIt() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card seeTheFuture = new Card(CardType.SEE_THE_FUTURE);
        player1.addCard(seeTheFuture);
        game.playSeeTheFuture();
        assertTrue(game.getDiscardPile().contains(seeTheFuture));
    }

    @Test
    public void playingSeeTheFutureLeavesSecondSeeTheFutureInHand() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        removeAll(player1, CardType.SEE_THE_FUTURE);
        player1.addCard(new Card(CardType.SEE_THE_FUTURE));
        player1.addCard(new Card(CardType.SEE_THE_FUTURE));
        game.playSeeTheFuture();
        assertEquals(1, player1.countCardsOfType(CardType.SEE_THE_FUTURE));
    }

    @Test
    public void playingSeeTheFutureReturnsTopThreeCardsInOrder() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        removeAll(player1, CardType.SEE_THE_FUTURE);
        player1.addCard(new Card(CardType.SEE_THE_FUTURE));
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.SKIP));
        game.getDeck().insertBottom(new Card(CardType.ATTACK));
        game.getDeck().insertBottom(new Card(CardType.DEFUSE));
        List<Card> seenCards = game.playSeeTheFuture();
        assertEquals(3, seenCards.size());
        assertEquals(CardType.DEFUSE, seenCards.get(0).getType());
        assertEquals(CardType.ATTACK, seenCards.get(1).getType());
        assertEquals(CardType.SKIP, seenCards.get(2).getType());
    }

    @Test
    public void playingSeeTheFutureDoesNotRemoveCardsFromDeck() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        player1.addCard(new Card(CardType.SEE_THE_FUTURE));
        int deckSizeBeforePlay = game.getDeck().size();
        game.playSeeTheFuture();
        assertEquals(deckSizeBeforePlay, game.getDeck().size());
    }

    @Test
    public void playingSeeTheFutureDoesNotAdvanceTurn() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        removeAll(player1, CardType.SEE_THE_FUTURE);
        player1.addCard(new Card(CardType.SEE_THE_FUTURE));
        game.playSeeTheFuture();
        assertEquals(player1, game.getCurrentPlayer());
    }

}
