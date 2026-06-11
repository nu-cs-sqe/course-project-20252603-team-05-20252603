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

public class GameCurrentPlayerTest {
    // G20
    @Test
    public void getCurrentPlayerThrowsExceptionWhenSetupHasNotCompleted() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        assertThrows(IllegalStateException.class, () -> {
            game.getCurrentPlayer();
        });
    }

    // G21
    @Test
    public void getCurrentPlayerReturnsFirstPlayerAfterSetupWithTwoPlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        assertEquals(player1, game.getCurrentPlayer());
    }

    // G22
    @Test
    public void getCurrentPlayerReturnsFirstPlayerAfterSetupWithFourPlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Player player4 = new Player("Player 4");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3, player4), deck);

        game.setupGame();

        assertEquals(player1, game.getCurrentPlayer());
    }

    // G23
    @Test
    public void getCurrentPlayerSkipsEliminatedCurrentPlayer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3), deck);

        game.setupGame();
        player1.eliminate();

        assertEquals(player2, game.getCurrentPlayer());
    }

    // G24
    @Test
    public void getCurrentPlayerThrowsExceptionWhenGameIsOver() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();
        player1.eliminate();

        assertThrows(IllegalStateException.class, () -> {
            game.getCurrentPlayer();
        });
    }

    @Test
    public void getCurrentPlayerThrowsExceptionWhenNoActivePlayerCanBeFound() {
        Player player1 = new ActiveThenInactivePlayer("Player 1");
        Player player2 = new ActiveThenInactivePlayer("Player 2");
        Game game = new Game(List.of(player1, player2), new Deck(new Random()));

        game.setupGame();

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            game.getCurrentPlayer();
        });

        assertEquals("No active players available", exception.getMessage());
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
