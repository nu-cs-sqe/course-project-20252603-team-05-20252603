package domain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Card {
    private final CardType type;

    public Card(CardType type) {
        this.type = Objects.requireNonNull(type, "type");
    }

    public CardType getType() {
        return type;
    }

    public void play(){
        if (type == CardType.SEE_THE_FUTURE){
            SeeTheFuture();
        }
    }

    private List<CardType> SeeTheFuture(){
        return Arrays.asList(CardType.SEE_THE_FUTURE);
    }
}
