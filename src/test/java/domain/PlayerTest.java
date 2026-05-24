package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    public void constructor_oneCharacterName_createsPlayer() {
        Player player = new Player("A");

        assertEquals("A", player.getName());
        assertTrue(player.getHand().isEmpty());
    }

    @Test
    public void constructor_normalName_createsPlayer() {
        Player player = new Player("Anthony");

        assertEquals("Anthony", player.getName());
        assertTrue(player.getHand().isEmpty());
    }

    @Test
    public void getHand_newPlayer_returnsEmptyHand() {
        Player player = new Player("Anthony");

        assertTrue(player.getHand().isEmpty());
    }

    @Test
    public void getName_oneCharacterName_returnsName() {
        Player player = new Player("A");

        assertEquals("A", player.getName());
    }

    @Test
    public void getName_normalName_returnsName() {
        Player player = new Player("Anthony");

        assertEquals("Anthony", player.getName());
    }

    @Test
    public void constructor_nullName_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Player(null));
    }

    @Test
    public void constructor_emptyName_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Player(""));
    }

    @Test
    public void constructor_whitespaceName_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Player("   "));
    }

    @Test
    public void addCard_nullCard_throwsException() {
        Player player = new Player("Anthony");

        assertThrows(IllegalArgumentException.class, () -> player.addCard(null));
    }

    @Test
    public void addCard_playerHasNoCards_addsOneCard() {
        Player player = new Player("Anthony");
        Card card = new Card(CardType.DEFUSE);

        player.addCard(card);

        assertEquals(1, player.getHand().size());
        assertTrue(player.getHand().contains(card));
    }

    @Test
    public void addCard_playerAlreadyHasOneCard_addsSecondCard() {
        Player player = new Player("Anthony");
        Card firstCard = new Card(CardType.DEFUSE);
        Card secondCard = new Card(CardType.SKIP);

        player.addCard(firstCard);
        player.addCard(secondCard);

        assertEquals(2, player.getHand().size());
        assertTrue(player.getHand().contains(firstCard));
        assertTrue(player.getHand().contains(secondCard));
    }

    @Test
    public void getHand_playerHasOneCard_returnsThatCard() {
        Player player = new Player("Anthony");
        Card card = new Card(CardType.DEFUSE);

        player.addCard(card);

        assertEquals(1, player.getHand().size());
        assertTrue(player.getHand().contains(card));
    }

    @Test
    public void getHand_playerHasMultipleCards_returnsAllCards() {
        Player player = new Player("Anthony");
        Card firstCard = new Card(CardType.DEFUSE);
        Card secondCard = new Card(CardType.SKIP);
        Card thirdCard = new Card(CardType.SHUFFLE);

        player.addCard(firstCard);
        player.addCard(secondCard);
        player.addCard(thirdCard);

        assertEquals(3, player.getHand().size());
        assertTrue(player.getHand().contains(firstCard));
        assertTrue(player.getHand().contains(secondCard));
        assertTrue(player.getHand().contains(thirdCard));
    }

}
