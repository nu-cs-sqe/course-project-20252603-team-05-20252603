package domain;

import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import java.util.List;

public class GameTest {
    // G1
    @Test
    public void constructorThrowsExceptionWhenPlayersListIsNull() {
        Deck deck = new Deck(new Random());

        assertThrows(IllegalArgumentException.class, () -> {
            new Game(null, deck);
        });
    }

    // G2
    @Test
    public void constructorThrowsExceptionWhenDeckIsNull() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        assertThrows(IllegalArgumentException.class, () -> {
            new Game(List.of(player1, player2), null);
        });
    }

    // G3
    @Test
    public void constructorThrowsExceptionWhenPlayersListIsEmpty() {
        Deck deck = new Deck(new Random());

        assertThrows(IllegalArgumentException.class, () -> {
            new Game(List.of(), deck);
        });
    }

    // G4
    @Test
    public void constructorThrowsExceptionWhenPlayersListHasOnePlayer() {
        Player player1 = new Player("Player 1");
        Deck deck = new Deck(new Random());

        assertThrows(IllegalArgumentException.class, () -> {
            new Game(List.of(player1), deck);
        });
    }

    // G5
    @Test
    public void constructorCreatesGameWhenPlayersListHasTwoPlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());

        assertDoesNotThrow(() -> {
            new Game(List.of(player1, player2), deck);
        });
    }

    // G6
    @Test
    public void constructorCreatesGameWhenPlayersListHasThreePlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Deck deck = new Deck(new Random());

        assertDoesNotThrow(() -> {
            new Game(List.of(player1, player2, player3), deck);
        });
    }

    // G7
    @Test
    public void constructorCreatesGameWhenPlayersListHasFourPlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Player player4 = new Player("Player 4");
        Deck deck = new Deck(new Random());

        assertDoesNotThrow(() -> {
            new Game(List.of(player1, player2, player3, player4), deck);
        });
    }
}