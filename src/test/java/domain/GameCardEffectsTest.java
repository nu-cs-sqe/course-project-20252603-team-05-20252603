package domain;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;

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
}
