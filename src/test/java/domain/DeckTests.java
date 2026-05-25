package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTests {
    @Test
    public void TC1_Constructor_InitializesNonEmptyDeck() {
        Deck deck = new Deck();
        assertTrue(deck.size() > 0);
    }

    @Test
    public void TC2_Constructor_ContainsCorrectAmountCardTypes() {
        Deck deck = new Deck();
        assertEquals(34, deck.size());
        assertEquals(3, deck.amtCardType(CardType.ATTACK));
        assertEquals(4, deck.amtCardType(CardType.SHUFFLE));
        assertEquals(3, deck.amtCardType(CardType.SKIP));
        assertEquals(4, deck.amtCardType(CardType.SEE_THE_FUTURE));
        assertEquals(4, deck.amtCardType(CardType.NOPE));
        assertEquals(4, deck.amtCardType(CardType.TACO_CAT));
        assertEquals(4, deck.amtCardType(CardType.BEARD_CAT));
        assertEquals(4, deck.amtCardType(CardType.RAINBOW_RALPHING_CAT));
        assertEquals(4, deck.amtCardType(CardType.HAIRY_POTATO_CAT));
        assertEquals(0, deck.amtCardType(CardType.DEFUSE));
        assertEquals(0, deck.amtCardType(CardType.EXPLODING_KITTEN));
    }

    @Test
    public void TC9_Draw_FromEmptyDeck_ThrowsException() {
        Deck deck = new Deck();
        while (deck.size() > 0) {
            deck.draw();
        }
        assertThrows(IllegalStateException.class, deck::draw);
    }

    @Test
    public void TC10_Draw_FromOneCardDeck_ReturnsCardAndDeckBecomesEmpty() {
        Deck deck = new Deck();
        while (deck.size() > 1) {
            deck.draw();
        }
        Card card = deck.draw();
        assertNotNull(card);
        assertEquals(0, deck.size());
    }

    @Test
    public void TC11_Draw_FromFullCardDeck() {
        Deck deck = new Deck();
        Card card = deck.draw();
        assertNotNull(card);
        assertEquals(33, deck.size());
    }

    @Test
    public void TC3_Size_EmptyDeck() {
        Deck deck = new Deck();
        while (deck.size() > 0) {
            deck.draw();
        }
        assertEquals(0, deck.size());
    }

    @Test
    public void TC4_Size_OneCardDeck() {
        Deck deck = new Deck();
        while (deck.size() > 1) {
            deck.draw();
        }
        assertEquals(1, deck.size());
    }

    @Test
    public void TC5_Size_TwoCardDeck() {
        Deck deck = new Deck();
        while (deck.size() > 2) {
            deck.draw();
        }
        assertEquals(2, deck.size());
    }

    @Test
    public void TC12_InsertBottom_IntoEmptyDeck() {
        Deck deck = new Deck();
        while (deck.size() > 0) {
            deck.draw();
        }
        deck.insertBottom(new Card(CardType.DEFUSE));
        assertEquals(1, deck.size());
        assertEquals(1, deck.amtCardType(CardType.DEFUSE));
    }

    @Test
    public void TC13_InsertBottom_IntoNonEmptyDeck() {
        Deck deck = new Deck();
        int originalSize = deck.size();
        deck.insertBottom(new Card(CardType.EXPLODING_KITTEN));
        assertEquals(originalSize + 1, deck.size());
        assertEquals(1, deck.amtCardType(CardType.EXPLODING_KITTEN));
    }

    @Test
    public void TC14_InsertBottom_DuplicateCardType() {
        Deck deck = new Deck();
        deck.insertBottom(new Card(CardType.DEFUSE));
        deck.insertBottom(new Card(CardType.DEFUSE));
        assertEquals(2, deck.amtCardType(CardType.DEFUSE));
    }




}
