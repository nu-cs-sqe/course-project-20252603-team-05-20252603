package domain;

import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;

public class GameTest {
    @Test
    public void constructorThrowsExceptionWhenPlayersListIsNull() {
        Deck deck = new Deck(new Random());

        assertThrows(IllegalArgumentException.class, () -> {
            new Game(null, deck);
        });
    }

    @Test
    public void constructorThrowsExceptionWhenDeckIsNull() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        assertThrows(IllegalArgumentException.class, () -> {
            new Game(List.of(player1, player2), null);
        });
    }

    @Test
    public void constructorThrowsExceptionWhenPlayersListIsEmpty() {
        Deck deck = new Deck(new Random());

        assertThrows(IllegalArgumentException.class, () -> {
            new Game(List.of(), deck);
        });
    }
}