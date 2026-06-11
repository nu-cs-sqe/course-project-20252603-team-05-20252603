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

public class GameGetWinnerTest {
    // G46
    @Test
    public void getWinnerReturnsNullWhenGameHasNotStarted() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        assertNull(game.getWinner());
    }

    @Test
    public void getWinnerReturnsNullWhenPlayerHasFifteenCardsBeforeSetup() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        addCardsUntilHandSize(player1, 15);

        assertNull(game.getWinner());
    }

    // G47
    @Test
    public void getWinnerReturnsNullWhenGameHasMoreThanOneActivePlayer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3), deck);

        game.setupGame();

        assertNull(game.getWinner());
    }

    // G48
    @Test
    public void getWinnerReturnsRemainingActivePlayerWhenGameHasExactlyOneActivePlayer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3), deck);

        game.setupGame();

        player1.eliminate();
        player2.eliminate();

        assertEquals(player3, game.getWinner());
    }

    // G49
    @Test
    public void getWinnerReturnsNullWhenGameHasZeroActivePlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        player1.eliminate();
        player2.eliminate();

        assertNull(game.getWinner());
    }

    @Test
    public void getWinnerReturnsNullWhenNoActivePlayerCanBeFoundAfterActiveCount() {
        Player player1 = new ActiveThenInactivePlayer("Player 1");
        Player player2 = new Player("Player 2");
        Game game = new Game(List.of(player1, player2), new Deck(new Random()));

        game.setupGame();
        player2.eliminate();

        assertNull(game.getWinner());
    }

    @Test
    public void getWinnerReturnsActivePlayerWithExactlyFifteenCardsAfterSetup() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        addCardsUntilHandSize(player1, 15);

        assertEquals(player1, game.getWinner());
    }

    @Test
    public void getWinnerReturnsActivePlayerWithMostCardsWhenMultiplePlayersMeetThreshold() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3), deck);

        game.setupGame();

        addCardsUntilHandSize(player1, 15);
        addCardsUntilHandSize(player2, 16);

        assertEquals(player2, game.getWinner());
    }

    @Test
    public void getWinnerReturnsNullWhenMultipleActivePlayersTieAtThreshold() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3), deck);

        game.setupGame();

        addCardsUntilHandSize(player1, 15);
        addCardsUntilHandSize(player2, 15);

        assertNull(game.getWinner());
    }

    @Test
    public void getWinnerIgnoresInactivePlayerWithFifteenCards() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3), deck);

        game.setupGame();

        addCardsUntilHandSize(player1, 15);
        player1.eliminate();

        assertNull(game.getWinner());
    }

    private void addCardsUntilHandSize(Player player, int handSize) {
        while (player.getHand().size() < handSize) {
            player.addCard(new Card(CardType.SHUFFLE));
        }
    }

    private static class ActiveThenInactivePlayer extends Player {
        private int activeChecks;

        ActiveThenInactivePlayer(String name) {
            super(name);
        }

        @Override
        public boolean isActive() {
            activeChecks++;
            return activeChecks == 1;
        }
    }
}
