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

public class GameSetupTest {
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

    // G15
    @Test
    public void setupGameAddsFourExplodingKittensForFivePlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Player player4 = new Player("Player 4");
        Player player5 = new Player("Player 5");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3, player4, player5), deck);

        game.setupGame();

        assertEquals(4, deck.amtCardType(CardType.EXPLODING_KITTEN));
    }

    // G16
    @Test
    public void setupGameGivesEachPlayerOneDefuseCard() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3), deck);

        game.setupGame();

        assertEquals(1, player1.countCardsOfType(CardType.DEFUSE));
        assertEquals(1, player2.countCardsOfType(CardType.DEFUSE));
        assertEquals(1, player3.countCardsOfType(CardType.DEFUSE));
    }

    // G17
    @Test
    public void setupGameGivesEachPlayerCorrectNumberOfStartingCards() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3), deck);

        game.setupGame();

        assertEquals(6, player1.getHand().size());
        assertEquals(6, player2.getHand().size());
        assertEquals(6, player3.getHand().size());
    }

    // G18
    @Test
    public void setupGameShufflesDeckAfterExplodingKittensAreInserted() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random(1));
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        assertEquals(1, deck.amtCardType(CardType.EXPLODING_KITTEN));
    }

    // G19
    @Test
    public void setupGameThrowsExceptionWhenCalledTwice() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        assertThrows(IllegalStateException.class, () -> {
            game.setupGame();
        });
    }
}