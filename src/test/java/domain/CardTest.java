package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CardTest {
    @Test
    void constructorAcceptsFirstCardType() {
        Card card = new Card(CardType.EXPLODING_KITTEN);

        assertEquals(CardType.EXPLODING_KITTEN, card.getType());
    }

    @Test
    void constructorAcceptsMiddleCardType() {
        Card card = new Card(CardType.DEFUSE);

        assertEquals(CardType.DEFUSE, card.getType());
    }

    @Test
    void constructorAcceptsLastCardType() {
        Card card = new Card(CardType.NOPE);

        assertEquals(CardType.NOPE, card.getType());
    }

    @Test
    void constructorRejectsNullCardType() {
        assertThrows(NullPointerException.class, () -> new Card(null));
    }

    @Test
    void getTypeReturnsFirstCardType() {
        Card card = new Card(CardType.EXPLODING_KITTEN);

        assertEquals(CardType.EXPLODING_KITTEN, card.getType());
    }

    @Test
    void getTypeReturnsMiddleCardType() {
        Card card = new Card(CardType.DEFUSE);

        assertEquals(CardType.DEFUSE, card.getType());
    }

    @Test
    void getTypeReturnsLastCardType() {
        Card card = new Card(CardType.NOPE);

        assertEquals(CardType.NOPE, card.getType());
    }
}
