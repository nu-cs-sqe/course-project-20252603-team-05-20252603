package domain;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
