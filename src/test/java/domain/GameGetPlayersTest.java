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
import java.util.ArrayList;

public class GameGetPlayersTest {
    // G50
    @Test
    public void getPlayersReturnsListOfTwoPlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        assertEquals(2, game.getPlayers().size());
    }

    // G51
    @Test
    public void getPlayersReturnsListOfFivePlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Player player4 = new Player("Player 4");
        Player player5 = new Player("Player 5");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3, player4, player5), deck);

        assertEquals(5, game.getPlayers().size());
    }

    // G52
    @Test
    public void getPlayersReturnsUnmodifiableList() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        Game game = new Game(players, deck);

        assertThrows(UnsupportedOperationException.class, () -> {
            game.getPlayers().add(new Player("Player 3"));
        });

        assertEquals(2, game.getPlayers().size());
    }
}