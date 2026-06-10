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

public class GameGetDeckTest {
    // G53
    @Test
    public void getDeckReturnsGameDeck() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        assertEquals(deck, game.getDeck());
    }

    // G54
    @Test
    public void getDeckReturnsUpdatedDeckAfterSetup() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        assertEquals(33, game.getDeck().size());
        assertEquals(1, game.getDeck().amtCardType(CardType.EXPLODING_KITTEN));
    }
}
