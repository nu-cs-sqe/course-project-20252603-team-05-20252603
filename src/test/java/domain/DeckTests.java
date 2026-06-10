package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTests {
    @Test
    public void TC1_Constructor_InitializesNonEmptyDeck() {
        Random rand = new Random();
        Deck deck = new Deck(rand);
        assertTrue(deck.size() > 0);
    }

    @Test
    public void TC2_Constructor_ContainsCorrectAmountCardTypes() {
        Random rand = new Random();
        Deck deck = new Deck(rand);
        assertEquals(46, deck.size());
        assertEquals(3, deck.amtCardType(CardType.ATTACK));
        assertEquals(4, deck.amtCardType(CardType.REVERSE));
        assertEquals(4, deck.amtCardType(CardType.ALTER_THE_FUTURE));
        assertEquals(4, deck.amtCardType(CardType.SHUFFLE));
        assertEquals(3, deck.amtCardType(CardType.SKIP));
        assertEquals(4, deck.amtCardType(CardType.SEE_THE_FUTURE));
        assertEquals(4, deck.amtCardType(CardType.STEAL));
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
        Random rand = new Random();
        Deck deck = new Deck(rand);
        while (deck.size() > 0) {
            deck.draw();
        }
        assertThrows(IllegalStateException.class, deck::draw);
    }

    @Test
    public void TC10_Draw_FromOneCardDeck_ReturnsCardAndDeckBecomesEmpty() {
        Random rand = new Random();
        Deck deck = new Deck(rand);
        while (deck.size() > 1) {
            deck.draw();
        }
        Card card = deck.draw();
        assertNotNull(card);
        assertEquals(0, deck.size());
    }

    @Test
    public void TC11_Draw_FromFullCardDeck() {
        Random rand = new Random();
        Deck deck = new Deck(rand);
        Card card = deck.draw();
        assertNotNull(card);
        assertEquals(45, deck.size());
    }

    @Test
    public void TC3_Size_EmptyDeck() {
        Random rand = new Random();
        Deck deck = new Deck(rand);
        while (deck.size() > 0) {
            deck.draw();
        }
        assertEquals(0, deck.size());
    }

    @Test
    public void TC4_Size_OneCardDeck() {
        Random rand = new Random();
        Deck deck = new Deck(rand);
        while (deck.size() > 1) {
            deck.draw();
        }
        assertEquals(1, deck.size());
    }

    @Test
    public void TC5_Size_TwoCardDeck() {
        Random rand = new Random();
        Deck deck = new Deck(rand);
        while (deck.size() > 2) {
            deck.draw();
        }
        assertEquals(2, deck.size());
    }

    @Test
    public void TC12_InsertBottom_IntoEmptyDeck() {
        Random rand = new Random();
        Deck deck = new Deck(rand);
        while (deck.size() > 0) {
            deck.draw();
        }
        deck.insertBottom(new Card(CardType.DEFUSE));
        assertEquals(1, deck.size());
        assertEquals(1, deck.amtCardType(CardType.DEFUSE));
    }

    @Test
    public void TC13_InsertBottom_IntoNonEmptyDeck() {
        Random rand = new Random();
        Deck deck = new Deck(rand);
        int originalSize = deck.size();
        deck.insertBottom(new Card(CardType.EXPLODING_KITTEN));
        assertEquals(originalSize + 1, deck.size());
        assertEquals(1, deck.amtCardType(CardType.EXPLODING_KITTEN));
    }

    @Test
    public void TC14_InsertBottom_DuplicateCardType() {
        Random rand = new Random();
        Deck deck = new Deck(rand);
        deck.insertBottom(new Card(CardType.DEFUSE));
        deck.insertBottom(new Card(CardType.DEFUSE));
        assertEquals(2, deck.amtCardType(CardType.DEFUSE));
    }

    @Test
    public void TC6_AmtCardType_NotPresent_ReturnsZero() {
        Random rand = new Random();
        Deck deck = new Deck(rand);
        assertEquals(0, deck.amtCardType(CardType.EXPLODING_KITTEN));
    }

    @Test
    public void TC7_AmtCardType_OnePresent_ReturnsOne() {
        Random rand = new Random();
        Deck deck = new Deck(rand);
        deck.insertBottom(new Card(CardType.EXPLODING_KITTEN));
        assertEquals(1, deck.amtCardType(CardType.EXPLODING_KITTEN));
    }

    @Test
    public void TC8_AmtCardType_MultiplePresent_ReturnsCount() {
        Random rand = new Random();
        Deck deck = new Deck(rand);
        assertEquals(3,deck.amtCardType(CardType.ATTACK));
    }

    @Test
    public void TC15_Shuffle_EmptyDeck() {
        Random rand = new Random();
        Deck deck = new Deck(rand);
        while (deck.size() > 0) {
            deck.draw();
        }
        deck.shuffle();
        assertEquals(0, deck.size());
    }

    @Test
    public void TC16_Shuffle_OneCardDeck() {
        Random rand = new Random();
        Deck deck = new Deck(rand);
        while (deck.size() > 1) {
            deck.draw();
        }
        Card expected = deck.draw();
        deck.insertBottom(expected);
        deck.shuffle();
        Card actual = deck.draw();
        assertEquals(0, deck.size());
        assertEquals(expected, actual);
    }

    @Test
    public void TC17_Shuffle_MultipleCardDeck_KeepsSameAmtCardTypes() {
        Random rand = new Random();
        Deck deck = new Deck(rand);
        int originalSize = deck.size();
        deck.shuffle();
        assertEquals(originalSize, deck.size());
        assertEquals(3, deck.amtCardType(CardType.ATTACK));
    }

    @Test
    public void TC18_shuffle_multipleCardDeck_ChangesOrder(){
        Random randMock = EasyMock.createMock(Random.class);
        EasyMock.expect(randMock.nextInt(2)).andReturn(0);
        EasyMock.replay(randMock);
        Deck deck = new Deck(randMock);
        while (deck.size() > 0) {
            deck.draw();
        }

        Card firstCard = new Card(CardType.DEFUSE);
        Card secondCard = new Card(CardType.EXPLODING_KITTEN);
        deck.insertBottom(firstCard);
        deck.insertBottom(secondCard);
        deck.shuffle();
        Card newTop = deck.draw();
        assertEquals(CardType.DEFUSE, newTop.getType());
        EasyMock.verify(randMock);
    }

    @Test
    public void TC19_draw_fromBottom(){
        Random rand = new Random();
        Deck deck = new Deck(rand);
        while (deck.size() > 0) {
            deck.draw();
        }
        Card firstCard = new Card(CardType.DEFUSE);
        Card secondCard = new Card(CardType.EXPLODING_KITTEN);
        deck.insertBottom(firstCard);
        deck.insertBottom(secondCard);
        assertEquals(secondCard, deck.draw());
    }

    @Test
    public void TC20_peek_zeroCards_ReturnsError(){
        Random rand = new Random();
        Deck deck = new Deck(rand);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> deck.peek(0));
        assertEquals("Cannot peek at 0 cards", exception.getMessage());
    }

    @Test
    public void TC21_Peek_NegativeCards() {
        Random rand = new Random();
        Deck deck = new Deck(rand);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> deck.peek(-1));
        assertEquals("Cannot peek at a negative number of cards", exception.getMessage());
    }

    @Test
    public void TC22_Peek_OneCard_ChecksOrder() {
        Random rand = new Random();
        Deck deck = new Deck(rand);

        while (deck.size() > 0) {
            deck.draw();
        }

        deck.insertBottom(new Card(CardType.SKIP));
        deck.insertBottom(new Card(CardType.ATTACK));

        List<Card> peeked = deck.peek(1);
        assertEquals(1, peeked.size());
        assertEquals(CardType.ATTACK, peeked.get(0).getType());
        assertEquals(2, deck.size());
    }

    @Test
    public void TC23_Peek_TwoCards_ChecksOrder() {
        Random rand = new Random();
        Deck deck = new Deck(rand);

        while (deck.size() > 0) {
            deck.draw();
        }

        deck.insertBottom(new Card(CardType.SKIP));
        deck.insertBottom(new Card(CardType.ATTACK));
        deck.insertBottom(new Card(CardType.DEFUSE));

        List<Card> peeked = deck.peek(2);
        assertEquals(2, peeked.size());
        assertEquals(CardType.DEFUSE, peeked.get(0).getType());
        assertEquals(CardType.ATTACK, peeked.get(1).getType());
        assertEquals(3, deck.size());
    }

    @Test
    public void TC24_Peek_ThreeCards_ChecksOrder() {
        Random rand = new Random();
        Deck deck = new Deck(rand);

        while (deck.size() > 0) {
            deck.draw();
        }

        deck.insertBottom(new Card(CardType.SKIP));
        deck.insertBottom(new Card(CardType.ATTACK));
        deck.insertBottom(new Card(CardType.DEFUSE));

        List<Card> peeked = deck.peek(3);
        assertEquals(3, peeked.size());
        assertEquals(CardType.DEFUSE, peeked.get(0).getType());
        assertEquals(CardType.ATTACK, peeked.get(1).getType());
        assertEquals(CardType.SKIP, peeked.get(2).getType());
        assertEquals(3, deck.size());
    }

    @Test
    public void TC25_Peek_DeckSize_Duplicates() {
        Random rand = new Random();
        Deck deck = new Deck(rand);
        int original_size = deck.size();

        List<Card> peeked = deck.peek(3);
        assertEquals(3, peeked.size());
        assertEquals(CardType.HAIRY_POTATO_CAT, peeked.get(0).getType());
        assertEquals(CardType.HAIRY_POTATO_CAT, peeked.get(1).getType());
        assertEquals(CardType.HAIRY_POTATO_CAT, peeked.get(2).getType());

        assertEquals(original_size, deck.size());
    }

    @Test
    public void TC26_Peek_MoreThanDeckSize() {
        Random rand = new Random();
        Deck deck = new Deck(rand);

        while (deck.size() > 0) {
            deck.draw();
        }

        deck.insertBottom(new Card(CardType.SKIP));
        deck.insertBottom(new Card(CardType.ATTACK));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> deck.peek(3));
        assertEquals("Cannot peek at more cards than exist in deck", exception.getMessage()
        );
    }

    @Test
    public void TC27_DrawBottom_FromEmptyDeck() {
        Deck deck = new Deck(new Random());

        while (deck.size() > 0) {
            deck.draw();
        }

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                deck::drawBottom
        );

        assertEquals("Cannot draw from empty deck", exception.getMessage());
    }

    @Test
    public void TC28_DrawBottom_FromOneCardDeck() {
        Deck deck = new Deck(new Random());

        while (deck.size() > 0) {
            deck.draw();
        }

        Card onlyCard = new Card(CardType.DEFUSE);
        deck.insertBottom(onlyCard);

        Card drawn = deck.drawBottom();

        assertEquals(onlyCard, drawn);
        assertEquals(0, deck.size());
    }

    @Test
    public void TC29_DrawBottom_FromTwoCardDeck_ChecksBottomCard() {
        Deck deck = new Deck(new Random());

        while (deck.size() > 0) {
            deck.draw();
        }

        Card bottomCard = new Card(CardType.DEFUSE);
        Card topCard = new Card(CardType.EXPLODING_KITTEN);

        deck.insertBottom(bottomCard);
        deck.insertBottom(topCard);

        Card drawn = deck.drawBottom();

        assertEquals(bottomCard, drawn);
        assertEquals(1, deck.size());
    }

    @Test
    public void TC30_DrawBottom_UpdatesAmtCardType() {
        Deck deck = new Deck(new Random());

        while (deck.size() > 0) {
            deck.draw();
        }

        deck.insertBottom(new Card(CardType.DEFUSE));

        assertEquals(1, deck.amtCardType(CardType.DEFUSE));

        deck.drawBottom();

        assertEquals(0, deck.amtCardType(CardType.DEFUSE));
    }

    @Test
    public void TC31_SwapTopTwo_EmptyDeck() {
        Deck deck = new Deck(new Random());

        while (deck.size() > 0) {
            deck.draw();
        }

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                deck::swapTopTwo
        );

        assertEquals(
                "Cannot swap top two cards when deck has fewer than 2 cards",
                exception.getMessage()
        );
    }

    @Test
    public void TC32_SwapTopTwo_OneCardDeck() {
        Deck deck = new Deck(new Random());

        while (deck.size() > 0) {
            deck.draw();
        }

        deck.insertBottom(new Card(CardType.DEFUSE));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                deck::swapTopTwo
        );

        assertEquals(
                "Cannot swap top two cards when deck has fewer than 2 cards",
                exception.getMessage()
        );
    }

    @Test
    public void TC33_SwapTopTwo_TwoCardDeck_SwapsOrder() {
        Deck deck = new Deck(new Random());

        while (deck.size() > 0) {
            deck.draw();
        }

        deck.insertBottom(new Card(CardType.SKIP));
        deck.insertBottom(new Card(CardType.ATTACK));

        deck.swapTopTwo();

        List<Card> peeked = deck.peek(2);

        assertEquals(CardType.SKIP, peeked.get(0).getType());
        assertEquals(CardType.ATTACK, peeked.get(1).getType());
        assertEquals(2, deck.size());
    }

    @Test
    public void TC34_SwapTopTwo_ThreeCardDeck_OnlySwapsTopTwo() {
        Deck deck = new Deck(new Random());

        while (deck.size() > 0) {
            deck.draw();
        }

        deck.insertBottom(new Card(CardType.NOPE));
        deck.insertBottom(new Card(CardType.SKIP));
        deck.insertBottom(new Card(CardType.ATTACK));

        deck.swapTopTwo();

        List<Card> peeked = deck.peek(3);

        assertEquals(CardType.SKIP, peeked.get(0).getType());
        assertEquals(CardType.ATTACK, peeked.get(1).getType());
        assertEquals(CardType.NOPE, peeked.get(2).getType());
        assertEquals(3, deck.size());
    }

    @Test
    public void TC35_SwapTopTwo_DuplicateCards_SizeUnchanged() {
        Deck deck = new Deck(new Random());

        while (deck.size() > 0) {
            deck.draw();
        }

        deck.insertBottom(new Card(CardType.DEFUSE));
        deck.insertBottom(new Card(CardType.DEFUSE));

        deck.swapTopTwo();

        assertEquals(2, deck.size());
        assertEquals(2, deck.amtCardType(CardType.DEFUSE));
    }
}
