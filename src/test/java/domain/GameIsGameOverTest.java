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

public class GameIsGameOverTest {
    // G40
    @Test
    public void isGameOverReturnsFalseWhenGameHasNotStarted() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        assertFalse(game.isGameOver());
    }

    @Test
    public void isGameOverReturnsFalseWhenPlayerHasFifteenCardsBeforeSetup() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        addCardsUntilHandSize(player1, 15);

        assertFalse(game.isGameOver());
    }

    // G41
    @Test
    public void isGameOverReturnsFalseWhenGameHasFiveActivePlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Player player4 = new Player("Player 4");
        Player player5 = new Player("Player 5");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3, player4, player5), deck);

        game.setupGame();

        assertFalse(game.isGameOver());
    }

    // G42
    @Test
    public void isGameOverReturnsFalseWhenGameHasThreeActivePlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3), deck);

        game.setupGame();

        assertFalse(game.isGameOver());
    }

    // G43
    @Test
    public void isGameOverReturnsFalseWhenGameHasTwoActivePlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        assertFalse(game.isGameOver());
    }

    // G44
    @Test
    public void isGameOverReturnsTrueWhenGameHasExactlyOneActivePlayer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        player1.eliminate();

        assertTrue(game.isGameOver());
    }

    // G45
    @Test
    public void isGameOverReturnsTrueWhenGameHasZeroActivePlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        player1.eliminate();
        player2.eliminate();

        assertTrue(game.isGameOver());
    }

    @Test
    public void isGameOverReturnsTrueWhenActivePlayerHasExactlyFifteenCardsAfterSetup() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        addCardsUntilHandSize(player1, 15);

        assertTrue(game.isGameOver());
    }

    @Test
    public void isGameOverReturnsFalseWhenHighestActivePlayerHasFourteenCardsAfterSetup() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        addCardsUntilHandSize(player1, 14);

        assertFalse(game.isGameOver());
    }

    @Test
    public void isGameOverIgnoresInactivePlayerWithFifteenCards() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3), deck);

        game.setupGame();

        addCardsUntilHandSize(player1, 15);
        player1.eliminate();

        assertFalse(game.isGameOver());
    }

    private void addCardsUntilHandSize(Player player, int handSize) {
        while (player.getHand().size() < handSize) {
            player.addCard(new Card(CardType.SHUFFLE));
        }
    }
}
