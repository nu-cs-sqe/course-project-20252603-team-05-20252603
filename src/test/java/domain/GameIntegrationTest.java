package domain;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameIntegrationTest {
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

    private void emptyHand(Player player) {
        while (!player.getHand().isEmpty()) {
            player.removeCard(player.getHand().get(0).getType());
        }
    }

    @Test
    public void setupPlayCardThenNormalDrawMovesCardsAndAdvancesTurn() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Deck deck = game.getDeck();
        Card playedCard = new Card(CardType.SHUFFLE);
        Card drawnCard = new Card(CardType.SHUFFLE);

        removeAll(player1, CardType.SHUFFLE);
        player1.addCard(playedCard);

        game.playCard(CardType.SHUFFLE);

        assertFalse(player1.getHand().contains(playedCard));
        assertTrue(game.getDiscardPile().contains(playedCard));
        assertEquals(player1, game.getCurrentPlayer());

        emptyDeck(deck);
        deck.insertBottom(drawnCard);
        int handSizeBeforeDraw = player1.getHand().size();

        game.drawCard();

        assertTrue(player1.getHand().contains(drawnCard));
        assertEquals(handSizeBeforeDraw + 1, player1.getHand().size());
        assertEquals(0, deck.size());
        assertEquals(player2, game.getCurrentPlayer());
    }

    @Test
    public void favorTransfersTargetsFirstCardAndDoesNotAdvanceTurn() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card favor = new Card(CardType.FAVOR);
        Card transferredCard = new Card(CardType.ATTACK);
        Card remainingTargetCard = new Card(CardType.SKIP);

        removeAll(player1, CardType.FAVOR);
        emptyHand(player2);
        player1.addCard(favor);
        player2.addCard(transferredCard);
        player2.addCard(remainingTargetCard);

        game.playCard(CardType.FAVOR, player2);

        assertFalse(player1.getHand().contains(favor));
        assertTrue(game.getDiscardPile().contains(favor));
        assertTrue(player1.getHand().contains(transferredCard));
        assertFalse(player2.getHand().contains(transferredCard));
        assertTrue(player2.getHand().contains(remainingTargetCard));
        assertEquals(player1, game.getCurrentPlayer());
    }

    @Test
    public void catThreeComboDiscardsCatsTransfersRequestedCardAndKeepsTurn() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Card firstCat = new Card(CardType.TACO_CAT);
        Card secondCat = new Card(CardType.TACO_CAT);
        Card thirdCat = new Card(CardType.TACO_CAT);
        Card requestedCard = new Card(CardType.ATTACK);

        removeAll(player1, CardType.TACO_CAT);
        removeAll(player1, CardType.ATTACK);
        emptyHand(player2);
        player1.addCard(firstCat);
        player1.addCard(secondCat);
        player1.addCard(thirdCat);
        player2.addCard(requestedCard);

        game.playCatThreeCombo(CardType.TACO_CAT, player2, CardType.ATTACK);

        assertFalse(player1.getHand().contains(firstCat));
        assertFalse(player1.getHand().contains(secondCat));
        assertFalse(player1.getHand().contains(thirdCat));
        assertTrue(game.getDiscardPile().contains(firstCat));
        assertTrue(game.getDiscardPile().contains(secondCat));
        assertTrue(game.getDiscardPile().contains(thirdCat));
        assertTrue(player1.getHand().contains(requestedCard));
        assertFalse(player2.getHand().contains(requestedCard));
        assertEquals(player1, game.getCurrentPlayer());
    }

    @Test
    public void shieldPreventsExplosionConsumesShieldAndAdvancesTurn() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Deck deck = game.getDeck();
        Card shield = new Card(CardType.SHIELD);

        removeAll(player1, CardType.DEFUSE);
        removeAll(player1, CardType.SHIELD);
        player1.addCard(shield);
        emptyDeck(deck);
        deck.insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertFalse(player1.getHand().contains(shield));
        assertTrue(game.getDiscardPile().contains(shield));
        assertTrue(player1.isActive());
        assertEquals(player2, game.getCurrentPlayer());
    }

    @Test
    public void unprotectedExplosionEliminatesPlayerAndEndsTwoPlayerGame() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Game game = createStartedGame(player1, player2);
        Deck deck = game.getDeck();

        removeAll(player1, CardType.DEFUSE);
        removeAll(player1, CardType.SHIELD);
        emptyDeck(deck);
        deck.insertBottom(new Card(CardType.EXPLODING_KITTEN));

        game.drawCard();

        assertFalse(player1.isActive());
        assertTrue(game.isGameOver());
        assertEquals(player2, game.getWinner());
    }
}
