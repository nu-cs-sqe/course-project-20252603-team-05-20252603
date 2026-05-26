package domain;

import java.util.Objects;

public class Card {
    private final CardType type;

    public Card(CardType type) {
        this.type = Objects.requireNonNull(type, "type");
    }

    public CardType getType() {
        return type;
    }
}
