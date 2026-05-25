package domain;

import java.util.*;

public class Deck {
    final private List<Card> cards;
    final private Map<CardType, Integer> cardAmounts;

    public Deck(){
        cards = new ArrayList<>();
        cardAmounts = new EnumMap<>(CardType.class);

        for (CardType type : CardType.values()) {
            cardAmounts.put(type, 0);
        }

    }

    public int size(){
        return 1;
    }

}
