package domain;

import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.Arrays;

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

    // G8
    @Test
    public void constructorCreatesGameWhenPlayersListHasFivePlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Player player4 = new Player("Player 4");
        Player player5 = new Player("Player 5");
        Deck deck = new Deck(new Random());

        assertDoesNotThrow(() -> {
            new Game(List.of(player1, player2, player3, player4, player5), deck);
        });
    }

    // G9
    @Test
    public void constructorThrowsExceptionWhenPlayersListHasSixPlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Player player4 = new Player("Player 4");
        Player player5 = new Player("Player 5");
        Player player6 = new Player("Player 6");
        Deck deck = new Deck(new Random());

        assertThrows(IllegalArgumentException.class, () -> {
            new Game(List.of(player1, player2, player3, player4, player5, player6), deck);
        });
    }

    // G10
    @Test
    public void constructorThrowsExceptionWhenPlayersListContainsNullPlayer() {
        Player player1 = new Player("Player 1");
        Deck deck = new Deck(new Random());

        assertThrows(IllegalArgumentException.class, () -> {
            new Game(Arrays.asList(player1, null), deck);
        });
    }

    // G11
    @Test
    public void setupGameCompletesSuccessfullyWithValidPlayersAndDeck() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        assertDoesNotThrow(() -> {
            game.setupGame();
        });
    }

    // G12
    @Test
    public void setupGameAddsOneExplodingKittenForTwoPlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        assertEquals(1, deck.amtCardType(CardType.EXPLODING_KITTEN));
    }

    // G13
    @Test
    public void setupGameAddsTwoExplodingKittensForThreePlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3), deck);

        game.setupGame();

        assertEquals(2, deck.amtCardType(CardType.EXPLODING_KITTEN));
    }

    // G14
    @Test
    public void setupGameAddsThreeExplodingKittensForFourPlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Player player4 = new Player("Player 4");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3, player4), deck);

        game.setupGame();

        assertEquals(3, deck.amtCardType(CardType.EXPLODING_KITTEN));
    }
}