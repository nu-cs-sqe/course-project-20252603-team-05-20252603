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
    public void TC10_Draw_FromEmptyDeck_ThrowsException() {
        Deck deck = new Deck();
        while (deck.size() > 0) {
            deck.draw();
        }
        assertThrows(IllegalStateException.class, deck::draw);
    }

    @Test
    public void TC11_Draw_FromOneCardDeck_ReturnsCardAndDeckBecomesEmpty() {
        Deck deck = new Deck();
        while (deck.size() > 1) {
            deck.draw();
        }
        Card card = deck.draw();
        assertNotNull(card);
        assertEquals(0, deck.size());
    }




}
