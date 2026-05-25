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

        addCards(CardType.ATTACK, 3);
        addCards(CardType.SHUFFLE, 4);
        addCards(CardType.SKIP, 3);
        addCards(CardType.SEE_THE_FUTURE, 4);
        addCards(CardType.NOPE, 4);
        addCards(CardType.TACO_CAT, 4);
        addCards(CardType.BEARD_CAT, 4);
        addCards(CardType.RAINBOW_RALPHING_CAT, 4);
        addCards(CardType.HAIRY_POTATO_CAT, 4);
    }

    private void addCards(CardType type, int amount){
        for(int i = 0; i < amount; i++){
            cards.add(new Card(type));
        }
        cardAmounts.put(type, cardAmounts.get(type) + amount);
    }

    public int size(){
        return cards.size();
    }

    public int amtCardType(CardType type){
        return cardAmounts.get(type);
    }

    // draws from the bottom of the deck
    public Card draw(){
        if (cards.isEmpty()){
            throw new IllegalStateException("Cannot draw from empty deck");
        }

        Card drawn = cards.remove(cards.size() - 1);
        cardAmounts.put(drawn.getType(), cardAmounts.get(drawn.getType()) - 1);
        return drawn;
    }

    public void insertBottom(Card card){
        cards.add(card);
        cardAmounts.put(card.getType(), cardAmounts.get(card.getType()) + 1);
    }

    public void shuffle(){

    }

}
