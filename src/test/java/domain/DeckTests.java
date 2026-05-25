package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeckTests {
    @Test
    public void TC1_Constructor_InitializesNonEmptyDeck() {
        Deck deck = new Deck();
        assertTrue(deck.size() > 0);
    }



}
