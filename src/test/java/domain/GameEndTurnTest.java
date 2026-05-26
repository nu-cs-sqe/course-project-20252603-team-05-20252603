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

public class GameEndTurnTest {
    // G25
    @Test
    public void endTurnThrowsExceptionWhenGameHasNotStarted() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        assertThrows(IllegalStateException.class, () -> {
            game.endTurn();
        });
    }

    // G26
    @Test
    public void endTurnChangesCurrentPlayerWhenGameHasTwoActivePlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        assertEquals(player1, game.getCurrentPlayer());

        game.endTurn();

        assertEquals(player2, game.getCurrentPlayer());
    }

    // G27
    @Test
    public void endTurnAdvancesToNextPlayerWhenCurrentPlayerIsNotLast() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Player player4 = new Player("Player 4");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3, player4), deck);

        game.setupGame();

        assertEquals(player1, game.getCurrentPlayer());

        game.endTurn();

        assertEquals(player2, game.getCurrentPlayer());

        game.endTurn();

        assertEquals(player3, game.getCurrentPlayer());
    }

    // G28
    @Test
    public void endTurnWrapsAroundWhenCurrentPlayerIsLast() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Player player4 = new Player("Player 4");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3, player4), deck);

        game.setupGame();

        assertEquals(player1, game.getCurrentPlayer());

        game.endTurn();
        assertEquals(player2, game.getCurrentPlayer());

        game.endTurn();
        assertEquals(player3, game.getCurrentPlayer());

        game.endTurn();
        assertEquals(player4, game.getCurrentPlayer());

        game.endTurn();
        assertEquals(player1, game.getCurrentPlayer());
    }

    // G29
    @Test
    public void endTurnSkipsEliminatedPlayerAndAdvancesToNextActivePlayer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3), deck);

        game.setupGame();

        player2.eliminate();

        assertEquals(player1, game.getCurrentPlayer());

        game.endTurn();

        assertEquals(player3, game.getCurrentPlayer());
    }

    // G30
    @Test
    public void endTurnThrowsExceptionWhenGameIsAlreadyOver() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();
        player1.eliminate();

        assertThrows(IllegalStateException.class, () -> {
            game.endTurn();
        });
    }
}