package domain;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        player1.addCard(new Card(CardType.SKIP));

        game.playCard(CardType.SKIP);

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
}
