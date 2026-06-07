package domain;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameCardEffectsTest {
    private Game createStartedGame(Player... players) {
        Game game = new Game(List.of(players), new Deck(new Random(0)));
        game.setupGame();
        return game;
    }

    private void emptyDeck(Deck deck) {
        while (deck.size() > 0) {
            deck.draw();
        }
    }

    private void removeAll(Player player, CardType type) {
        while (player.hasCard(type)) {
            player.removeCard(type);
        }
    }

    // G65
    @Test
    public void explodingKittenEliminatesPlayerWithoutDefuseOrShield() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Game game = createStartedGame(player1, player2, player3);

        removeAll(player1, CardType.DEFUSE);
        removeAll(player1, CardType.SHIELD);
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertFalse(player1.isActive());
    }

    // G66
    @Test
    public void explodingKittenLeavesTwoActivePlayersAndGameContinues() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Game game = createStartedGame(player1, player2, player3);

        removeAll(player1, CardType.DEFUSE);
        removeAll(player1, CardType.SHIELD);
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertEquals(player2, game.getCurrentPlayer());
    }

    // G67
    @Test
    public void explodingKittenEndsTwoPlayerGameAndDeclaresWinner() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.DEFUSE);
        removeAll(player1, CardType.SHIELD);
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertTrue(game.isGameOver());
        assertEquals(player2, game.getWinner());
    }

    // G68
    @Test
    public void explodingKittenConsumesOneDefuseAndKeepsPlayerActive() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.DEFUSE);
        player1.addCard(new Card(CardType.DEFUSE));
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertTrue(player1.isActive());
        assertEquals(0, player1.countCardsOfType(CardType.DEFUSE));
    }

    // G69
    @Test
    public void explodingKittenConsumesShieldWhenNoDefuseIsAvailable() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.DEFUSE);
        player1.addCard(new Card(CardType.SHIELD));
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertTrue(player1.isActive());
        assertEquals(0, player1.countCardsOfType(CardType.SHIELD));
    }

    // G70
    @Test
    public void drawnExplodingKittenIsNotAddedToPlayersHand() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Game game = createStartedGame(player1, player2, player3);

        removeAll(player1, CardType.DEFUSE);
        removeAll(player1, CardType.SHIELD);
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertEquals(0, player1.countCardsOfType(CardType.EXPLODING_KITTEN));
    }

    // G71
    @Test
    public void zeroDefusesCannotPreventElimination() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");
        Game game = createStartedGame(player1, player2, player3);

        removeAll(player1, CardType.DEFUSE);
        removeAll(player1, CardType.SHIELD);
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertFalse(player1.isActive());
    }

    // G72
    @Test
    public void oneDefuseIsRemovedWhenExplodingKittenIsDrawn() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.DEFUSE);
        player1.addCard(new Card(CardType.DEFUSE));
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertEquals(0, player1.countCardsOfType(CardType.DEFUSE));
        assertTrue(player1.isActive());
    }

    // G73
    @Test
    public void oneDefuseRemainsWhenPlayerStartsWithTwo() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.DEFUSE);
        player1.addCard(new Card(CardType.DEFUSE));
        player1.addCard(new Card(CardType.DEFUSE));
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertEquals(1, player1.countCardsOfType(CardType.DEFUSE));
    }

    // G74
    @Test
    public void usedDefuseIsAddedToDiscardPile() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.DEFUSE);
        Card defuse = new Card(CardType.DEFUSE);
        player1.addCard(defuse);
        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertTrue(game.getDiscardPile().contains(defuse));
    }

    // G75
    @Test
    public void usingDefuseEndsTurn() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        emptyDeck(game.getDeck());
        game.getDeck().insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertEquals(player2, game.getCurrentPlayer());
    }

    // G76
    @Test
    public void playingSkipWithoutSkipThrowsException() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAll(player1, CardType.SKIP);

        assertThrows(IllegalStateException.class, () -> game.playCard(CardType.SKIP));
    }
}
