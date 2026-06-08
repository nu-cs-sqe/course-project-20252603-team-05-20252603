package domain;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GamePlayCardTest {
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
}
