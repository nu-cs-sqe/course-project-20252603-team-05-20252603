package domain;

import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.List;
import java.util.Arrays;

public class GameTest {
    // helper functions:
    private int countCardsOfType(Player player, CardType type) {
        int count = 0;

        for (Card card : player.getHand()) {
            if (card.getType() == type) {
                count++;
            }
        }

        return count;
    }
    
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

        assertEquals(1, countCardsOfType(player1, CardType.DEFUSE));
        assertEquals(1, countCardsOfType(player2, CardType.DEFUSE));
        assertEquals(1, countCardsOfType(player3, CardType.DEFUSE));
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

    // G31
    @Test
    public void drawCardThrowsExceptionWhenGameHasNotStarted() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        assertThrows(IllegalStateException.class, () -> {
            game.drawCard();
        });
    }

    // G32
    @Test
    public void drawCardThrowsExceptionWhenDeckIsEmpty() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        while (deck.size() > 0) {
            deck.draw();
        }

        assertThrows(IllegalStateException.class, () -> {
            game.drawCard();
        });
    }

    // G33
    @Test
    public void drawCardAddsNormalCardToCurrentPlayersHand() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        int handSizeBeforeDraw = player1.getHand().size();

        game.drawCard();

        assertEquals(handSizeBeforeDraw + 1, player1.getHand().size());
    }

    // G34
    @Test
    public void drawCardEndsTurnAfterDrawingNormalCard() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        assertEquals(player1, game.getCurrentPlayer());

        game.drawCard();

        assertEquals(player2, game.getCurrentPlayer());
    }

    // G35
    @Test
    public void drawCardUsesDefuseWhenCurrentPlayerDrawsExplodingKittenAndHasDefuse() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        while (deck.size() > 0) {
            deck.draw();
        }

        deck.insertBottom(new Card(CardType.EXPLODING_KITTEN));

        assertEquals(1, countCardsOfType(player1, CardType.DEFUSE));

        game.drawCard();

        assertTrue(player1.isActive());
        assertEquals(0, countCardsOfType(player1, CardType.DEFUSE));
    }

    // G36
    @Test
    public void drawCardEliminatesPlayerWhenCurrentPlayerDrawsExplodingKittenAndHasNoDefuse() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3), deck);

        game.setupGame();

        player1.removeCard(CardType.DEFUSE);

        while (deck.size() > 0) {
            deck.draw();
        }

        deck.insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertFalse(player1.isActive());
    }

    // G37
    @Test
    public void drawCardContinuesGameWhenPlayerExplodesWithThreeOrMorePlayersAlive() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3), deck);

        game.setupGame();

        player1.removeCard(CardType.DEFUSE);

        while (deck.size() > 0) {
            deck.draw();
        }

        deck.insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertFalse(player1.isActive());
        assertEquals(player2, game.getCurrentPlayer());
    }

    // G38
    @Test
    public void drawCardEndsGameWhenPlayerExplodesWithExactlyTwoPlayersAlive() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        player1.removeCard(CardType.DEFUSE);

        while (deck.size() > 0) {
            deck.draw();
        }

        deck.insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertFalse(player1.isActive());

        assertThrows(IllegalStateException.class, () -> {
            game.getCurrentPlayer();
        });
    }

    // G39
    @Test
    public void drawCardThrowsExceptionWhenGameIsAlreadyOver() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        player1.eliminate();

        assertThrows(IllegalStateException.class, () -> {
            game.drawCard();
        });
    }

    // G40
    @Test
    public void isGameOverReturnsFalseWhenGameHasNotStarted() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        assertFalse(game.isGameOver());
    }

    // G41
    @Test
    public void isGameOverReturnsFalseWhenGameHasFiveActivePlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Player player4 = new Player("Player 4");
        Player player5 = new Player("Player 5");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3, player4, player5), deck);

        game.setupGame();

        assertFalse(game.isGameOver());
    }

    // G42
    @Test
    public void isGameOverReturnsFalseWhenGameHasThreeActivePlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2, player3), deck);

        game.setupGame();

        assertFalse(game.isGameOver());
    }

    // G43
    @Test
    public void isGameOverReturnsFalseWhenGameHasTwoActivePlayers() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        assertFalse(game.isGameOver());
    }

    // G44
    @Test
    public void isGameOverReturnsTrueWhenGameHasExactlyOneActivePlayer() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Deck deck = new Deck(new Random());
        Game game = new Game(List.of(player1, player2), deck);

        game.setupGame();

        player1.eliminate();

        assertTrue(game.isGameOver());
    }
}