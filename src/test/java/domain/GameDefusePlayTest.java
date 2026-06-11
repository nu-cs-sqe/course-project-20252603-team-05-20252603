package domain;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameDefusePlayTest {
    private Game createStartedGame(Player player1, Player player2) {
        Game game = new Game(List.of(player1, player2), new Deck(new Random()));
        game.setupGame();
        return game;
    }

    private void removeAllDefuses(Player player) {
        while (player.hasCard(CardType.DEFUSE)) {
            player.removeCard(CardType.DEFUSE);
        }
    }

    // G89D1
    @Test
    public void oneDefuseCannotBePlayedWithoutDrawingExplodingKitten() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAllDefuses(player1);
        player1.addCard(new Card(CardType.DEFUSE));

        assertThrows(IllegalStateException.class, () -> game.playCard(CardType.DEFUSE));
    }

    // G89D2
    @Test
    public void rejectedDefusePlayKeepsCardOutOfDiscardPile() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAllDefuses(player1);
        Card defuse = new Card(CardType.DEFUSE);
        player1.addCard(defuse);

        assertThrows(IllegalStateException.class, () -> game.playCard(CardType.DEFUSE));

        assertTrue(player1.getHand().contains(defuse));
        assertFalse(game.getDiscardPile().contains(defuse));
    }

    // G89D3
    @Test
    public void twoDefusesRemainWhenManualPlayIsRejected() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);

        removeAllDefuses(player1);
        player1.addCard(new Card(CardType.DEFUSE));
        player1.addCard(new Card(CardType.DEFUSE));

        assertThrows(IllegalStateException.class, () -> game.playCard(CardType.DEFUSE));

        assertEquals(2, player1.countCardsOfType(CardType.DEFUSE));
    }
}
