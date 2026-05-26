package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private final String name;
    private final List<Card> hand;
    private boolean active;

    public Player(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }

        this.name = name;
        this.hand = new ArrayList<>();
        this.active = true;
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return Collections.unmodifiableList(hand);
    }

    public void addCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Card cannot be null");
        }
        hand.add(card);
    }

    public boolean isActive() {
        return active;
    }

    public void eliminate() {
        active = false;
    }

    public Card removeCard(CardType type) {
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);

            if (card.getType() == type) {
                return hand.remove(i);
            }
        }

        throw new IllegalStateException("Player does not have card of type " + type);
    }

    public boolean hasCard(CardType type) {
        for (Card card : hand) {
            if (card.getType() == type) {
                return true;
            }
        }

        return false;
    }
}