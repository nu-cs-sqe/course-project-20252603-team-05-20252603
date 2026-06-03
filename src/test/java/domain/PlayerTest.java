package domain;

import org.junit.jupiter.api.Test;
import java.util.List;
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

    @Test
    public void getHand_externalModification_doesNotModifyPlayerHand() {
        Player player = new Player("Anthony");
        Card originalCard = new Card(CardType.DEFUSE);
        Card extraCard = new Card(CardType.SKIP);

        player.addCard(originalCard);
        List<Card> returnedHand = player.getHand();

        try {
            returnedHand.add(extraCard);
        } catch (UnsupportedOperationException ignored) {
        }

        assertEquals(1, player.getHand().size());
        assertTrue(player.getHand().contains(originalCard));
        assertFalse(player.getHand().contains(extraCard));
    }

    @Test
    public void isActive_newPlayer_returnsTrue() {
        Player player = new Player("Anthony");

        assertTrue(player.isActive());
    }

    @Test
    public void isActive_eliminatedPlayer_returnsFalse() {
        Player player = new Player("Anthony");

        player.eliminate();

        assertFalse(player.isActive());
    }

    @Test
    public void eliminate_activePlayer_becomesInactive() {
        Player player = new Player("Anthony");

        player.eliminate();

        assertFalse(player.isActive());
    }

    @Test
    public void eliminate_alreadyInactivePlayer_remainsInactive() {
        Player player = new Player("Anthony");

        player.eliminate();
        player.eliminate();

        assertFalse(player.isActive());
    }

    @Test
    public void hasCard_emptyHand_returnsFalse() {
        Player player = new Player("Anthony");

        assertFalse(player.hasCard(CardType.DEFUSE));
    }

    @Test
    public void hasCard_noMatchingCard_returnsFalse() {
        Player player = new Player("Anthony");

        player.addCard(new Card(CardType.SKIP));

        assertFalse(player.hasCard(CardType.DEFUSE));
    }

    @Test
    public void hasCard_oneMatchingCard_returnsTrue() {
        Player player = new Player("Anthony");

        player.addCard(new Card(CardType.DEFUSE));

        assertTrue(player.hasCard(CardType.DEFUSE));
    }

    @Test
    public void hasCard_nullType_returnsFalse() {
        Player player = new Player("Anthony");

        player.addCard(new Card(CardType.DEFUSE));

        assertFalse(player.hasCard(null));
    }

    @Test
    public void removeCard_emptyHand_throwsException() {
        Player player = new Player("Anthony");

        assertThrows(IllegalStateException.class, () -> player.removeCard(CardType.DEFUSE));
    }

    @Test
    public void removeCard_noMatchingCard_throwsException() {
        Player player = new Player("Anthony");

        player.addCard(new Card(CardType.SKIP));

        assertThrows(IllegalStateException.class, () -> player.removeCard(CardType.DEFUSE));
    }

    @Test
    public void removeCard_oneMatchingCard_removesAndReturnsCard() {
        Player player = new Player("Anthony");
        Card card = new Card(CardType.DEFUSE);

        player.addCard(card);

        Card removedCard = player.removeCard(CardType.DEFUSE);

        assertSame(card, removedCard);
        assertTrue(player.getHand().isEmpty());
    }

    @Test
    public void removeCard_multipleMatchingCards_removesOnlyOneMatchingCard() {
        Player player = new Player("Anthony");
        Card firstCard = new Card(CardType.DEFUSE);
        Card secondCard = new Card(CardType.DEFUSE);
        Card otherCard = new Card(CardType.SKIP);

        player.addCard(firstCard);
        player.addCard(otherCard);
        player.addCard(secondCard);

        Card removedCard = player.removeCard(CardType.DEFUSE);

        assertSame(firstCard, removedCard);
        assertEquals(2, player.getHand().size());
        assertFalse(player.getHand().contains(firstCard));
        assertTrue(player.getHand().contains(secondCard));
        assertTrue(player.getHand().contains(otherCard));
    }

    @Test
    public void removeCard_nullType_throwsException() {
        Player player = new Player("Anthony");

        player.addCard(new Card(CardType.DEFUSE));

        assertThrows(IllegalStateException.class, () -> player.removeCard(null));
    }

}
