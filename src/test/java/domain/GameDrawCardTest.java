package domain;

import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.List;
import java.util.Arrays;

public class GameDrawCardTest {
    // G31
    @Test
    public void drawCardThrowsExceptionWhenGameHasNotStarted() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        assertThrows(IllegalStateException.class, () -> {
            game.drawCard();
        });
    }

    // G32
    @Test
    public void drawCardThrowsExceptionWhenDeckIsEmpty() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        while (deck.size() > 0) {
            deck.draw();
        }

        assertThrows(IllegalStateException.class, () -> {
            game.drawCard();
        });
    }

    // G33
    @Test
    public void drawCardAddsNormalCardToCurrentPlayersHand() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        while (deck.size() > 0) {
            deck.draw();
        }

        deck.insertBottom(new Card(CardType.SHUFFLE));

        int handSizeBeforeDraw = player1.getHand().size();

        game.drawCard();

        assertEquals(handSizeBeforeDraw + 1, player1.getHand().size());
    }

    // G34
    @Test
    public void drawCardEndsTurnAfterDrawingNormalCard() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        while (deck.size() > 0) {
            deck.draw();
        }

        deck.insertBottom(new Card(CardType.SHUFFLE));

        assertEquals(player1, game.getCurrentPlayer());

        game.drawCard();

        assertEquals(player2, game.getCurrentPlayer());
    }

    // G35
    @Test
    public void drawCardUsesDefuseWhenCurrentPlayerDrawsExplodingKittenAndHasDefuse() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        while (deck.size() > 0) {
            deck.draw();
        }

        deck.insertBottom(new Card(CardType.EXPLODING_KITTEN));

        assertEquals(1, player1.countCardsOfType(CardType.DEFUSE));

        game.drawCard();

        assertTrue(player1.isActive());
        assertEquals(0, player1.countCardsOfType(CardType.DEFUSE));
    }

    // G36
    @Test
    public void drawCardEliminatesPlayerWhenCurrentPlayerDrawsExplodingKittenAndHasNoDefuse() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3), deck);

        game.setupGame();

        player1.removeCard(CardType.DEFUSE);

        while (deck.size() > 0) {
            deck.draw();
        }

        deck.insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertFalse(player1.isActive());
    }

    // G37
    @Test
    public void drawCardContinuesGameWhenPlayerExplodesWithThreeOrMorePlayersAlive() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3), deck);

        game.setupGame();

        player1.removeCard(CardType.DEFUSE);

        while (deck.size() > 0) {
            deck.draw();
        }

        deck.insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertFalse(player1.isActive());
        assertEquals(player2, game.getCurrentPlayer());
    }

    // G38
    @Test
    public void drawCardEndsGameWhenPlayerExplodesWithExactlyTwoPlayersAlive() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        player1.removeCard(CardType.DEFUSE);

        while (deck.size() > 0) {
            deck.draw();
        }

        deck.insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertFalse(player1.isActive());

        assertThrows(IllegalStateException.class, () -> {
            game.getCurrentPlayer();
        });
    }

    // G39
    @Test
    public void drawCardThrowsExceptionWhenGameIsAlreadyOver() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        player1.eliminate();

        assertThrows(IllegalStateException.class, () -> {
            game.drawCard();
        });
    }
}