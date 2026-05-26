package domain;

import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTest {
    @Test
    public void constructorThrowsExceptionWhenPlayersListIsNull() {
        Deck deck = new Deck(new Random());

        assertThrows(IllegalArgumentException.class, () -> {
            new Game(null, deck);
        });
    }
}