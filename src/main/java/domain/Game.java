package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Game {
    private static final int STARTING_RANDOM_CARDS = 5;

    private final List<Player> players;
    private final Deck deck;
    private final List<Card> discardPile;

    private boolean setupComplete;

    private int currentPlayerIndex;
    private int pendingTurnsForCurrentPlayer;

    private boolean canSwapPeekedCards;

    public Game(List<Player> players, Deck deck) {
        if (players == null) {
            throw new IllegalArgumentException("Players list cannot be null");
        }

        if (deck == null) {
            throw new IllegalArgumentException("Deck cannot be null");
        }

        if (players.size() < 2) {
            throw new IllegalArgumentException("Game must have at least 2 players");
        }

        if (players.size() > 5) {
            throw new IllegalArgumentException("Game cannot have more than 5 players");
        }

        for (Player player : players) {
            if (player == null) {
                throw new IllegalArgumentException("Players list cannot contain null players");
            }
        }

        this.players = players;
        this.deck = deck;
        this.discardPile = new ArrayList<>();
        this.setupComplete = false;
        this.currentPlayerIndex = 0;
        this.pendingTurnsForCurrentPlayer = 0;
        this.canSwapPeekedCards = false;
    }

    public void setupGame() {
        if (setupComplete) {
            throw new IllegalStateException("Game setup has already been completed");
        }

        for (Player player : players) {
            player.addCard(new Card(CardType.DEFUSE));
        }

        for (int i = 0; i < STARTING_RANDOM_CARDS; i++) {
            for (Player player : players) {
                player.addCard(deck.draw());
            }
        }

        for (int i = 0; i < players.size() - 1; i++) {
            deck.insertBottom(new Card(CardType.EXPLODING_KITTEN));
        }

        deck.shuffle();
        setupComplete = true;
    }

    public Player getCurrentPlayer() {
        if (!setupComplete) {
            throw new IllegalStateException("Game setup has not been completed");
        }

        if (getActivePlayerCount() <= 1) {
            throw new IllegalStateException("Game is over");
        }

        int playersChecked = 0;

        while (playersChecked < players.size()) {
            Player currentPlayer = players.get(currentPlayerIndex);

            if (currentPlayer.isActive()) {
                return currentPlayer;
            }

            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            playersChecked++;
        }

        throw new IllegalStateException("No active players available");
    }

    private int getActivePlayerCount() {
        int count = 0;

        for (Player player : players) {
            if (player.isActive()) {
                count++;
            }
        }

        return count;
    }

    public void endTurn() {
        if (!setupComplete) {
            throw new IllegalStateException("Game setup has not been completed");
        }

        if (getActivePlayerCount() <= 1) {
            throw new IllegalStateException("Game is over");
        }

        pendingTurnsForCurrentPlayer = 0;
        moveToNextActivePlayer();
    }

    public void drawCard() {
        validateGameCanPlayCard();
        Card drawnCard = deck.draw();
        handleDrawnCard(drawnCard);
    }

    private void handleDrawnCard(Card drawnCard) {
        Player currentPlayer = getCurrentPlayer();
        if (drawnCard.getType() == CardType.EXPLODING_KITTEN) {
            if (currentPlayer.hasCard(CardType.DEFUSE)) {
                discardPile.add(currentPlayer.removeCard(CardType.DEFUSE));
                finishCurrentDrawTurn();
            } else if (currentPlayer.hasCard(CardType.SHIELD)) {
                discardPile.add(currentPlayer.removeCard(CardType.SHIELD));
                finishCurrentDrawTurn();
            } else {
                currentPlayer.eliminate();

                if (getActivePlayerCount() > 1) {
                    pendingTurnsForCurrentPlayer = 0;
                    moveToNextActivePlayer();
                }
            }
            return;
        }
        currentPlayer.addCard(drawnCard);
        finishCurrentDrawTurn();
    }

    public boolean isGameOver() {
        if (!setupComplete) {
            return false;
        }

        return getActivePlayerCount() <= 1;
    }

    public Player getWinner() {
        if (!setupComplete) {
            return null;
        }

        if (getActivePlayerCount() != 1) {
            return null;
        }

        for (Player player : players) {
            if (player.isActive()) {
                return player;
            }
        }

        return null;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public Deck getDeck() {
        return deck;
    }

    public List<Card> getDiscardPile() {
        return Collections.unmodifiableList(discardPile);
    }

    public void playCard(CardType type) {
        if (type == null) {
            throw new IllegalArgumentException("Card type cannot be null");
        }

        if (type == CardType.FAVOR || type == CardType.TRADE) {
            throw new IllegalArgumentException(type + " requires a target player");
        }

        validateGameCanPlayCard();

        Player currentPlayer = getCurrentPlayer();
        Card playedCard = currentPlayer.removeCard(type);
        discardPile.add(playedCard);

        if (type == CardType.ATTACK) {
            pendingTurnsForCurrentPlayer += 2;
            moveToNextActivePlayer();
        }
        else if (type == CardType.SKIP) {
            endTurn();
        }
        else if (type == CardType.SHUFFLE) {
            deck.shuffle();
        }
        else if (type == CardType.DRAW_FROM_BOTTOM) {
            Card drawnCard = deck.drawBottom();
            handleDrawnCard(drawnCard);
        }
    }

    public void playCard(CardType type, Player targetPlayer) {
        if (type == null) {
            throw new IllegalArgumentException("Card type cannot be null");
        }

        if (type != CardType.FAVOR && type != CardType.TRADE) {
            playCard(type);
            return;
        }

        validateGameCanPlayCard();

        Player currentPlayer = getCurrentPlayer();
        validateTargetPlayer(targetPlayer, currentPlayer);

        if (type == CardType.TRADE) {
            validateTradeCards(currentPlayer, targetPlayer);
        }

        Card playedCard = currentPlayer.removeCard(type);
        discardPile.add(playedCard);

        if (type == CardType.FAVOR) {
            Card transferredCard = targetPlayer.removeCard(targetPlayer.getHand().get(0).getType());
            currentPlayer.addCard(transferredCard);
        } else if (type == CardType.TRADE) {
            Card currentPlayerCard = currentPlayer.removeCard(
                    currentPlayer.getHand().get(0).getType());
            Card targetPlayerCard = targetPlayer.removeCard(
                    targetPlayer.getHand().get(0).getType());
            currentPlayer.addCard(targetPlayerCard);
            targetPlayer.addCard(currentPlayerCard);
        }
    }

    public Card playMark(Player targetPlayer) {
        validateGameCanPlayCard();

        Player currentPlayer = getCurrentPlayer();
        validateTargetPlayer(targetPlayer, currentPlayer);

        if (!currentPlayer.hasCard(CardType.MARK)) {
            throw new IllegalStateException("Player does not have card of type " + CardType.MARK);
        }

        Card playedCard = currentPlayer.removeCard(CardType.MARK);
        discardPile.add(playedCard);

        return targetPlayer.getHand().get(0);
    }

    public void playCatPairCombo(CardType catType, Player targetPlayer) {
        if (catType == null) {
            throw new IllegalArgumentException("Cat card type cannot be null");
        }

        if (!isCatCard(catType)) {
            throw new IllegalArgumentException("Card type must be a Cat Card");
        }

        validateGameCanPlayCard();

        Player currentPlayer = getCurrentPlayer();
        validateTargetPlayer(targetPlayer, currentPlayer);

        if (currentPlayer.countCardsOfType(catType) < 2) {
            throw new IllegalStateException("Current player needs two matching Cat Cards");
        }

        Card firstCat = currentPlayer.removeCard(catType);
        Card secondCat = currentPlayer.removeCard(catType);
        discardPile.add(firstCat);
        discardPile.add(secondCat);

        Card transferredCard = targetPlayer.removeCard(targetPlayer.getHand().get(0).getType());
        currentPlayer.addCard(transferredCard);
    }

    public void playCatThreeCombo(CardType catType, Player targetPlayer, CardType requestedType) {
        if (catType == null) {
            throw new IllegalArgumentException("Cat card type cannot be null");
        }

        if (!isCatCard(catType)) {
            throw new IllegalArgumentException("Card type must be a Cat Card");
        }

        validateGameCanPlayCard();

        Player currentPlayer = getCurrentPlayer();
        validateTargetPlayerSelection(targetPlayer, currentPlayer);

        if (requestedType == null) {
            throw new IllegalArgumentException("Requested card type cannot be null");
        }

        if (currentPlayer.countCardsOfType(catType) < 3) {
            throw new IllegalStateException("Current player needs three matching Cat Cards");
        }

        Card firstCat = currentPlayer.removeCard(catType);
        Card secondCat = currentPlayer.removeCard(catType);
        Card thirdCat = currentPlayer.removeCard(catType);
        discardPile.add(firstCat);
        discardPile.add(secondCat);
        discardPile.add(thirdCat);

        if (targetPlayer.hasCard(requestedType)) {
            Card transferredCard = targetPlayer.removeCard(requestedType);
            currentPlayer.addCard(transferredCard);
        }
    }

    public List<Card> playSeeTheFuture() {
        validateGameCanPlayCard();
        Player currentPlayer = getCurrentPlayer();
        Card playedCard = currentPlayer.removeCard(CardType.SEE_THE_FUTURE);
        discardPile.add(playedCard);
        return deck.peek(3);
    }

    public List<Card> playPeekSwap() {
        validateGameCanPlayCard();
        if (deck.size() < 2) {
            throw new IllegalStateException("Cannot play Peek Swap when deck has fewer than 2 cards");
        }
        Player currentPlayer = getCurrentPlayer();
        Card playedCard = currentPlayer.removeCard(CardType.PEEK_SWAP);
        discardPile.add(playedCard);

        canSwapPeekedCards = true;
        return deck.peek(2);
    }

    public void swapPeekedCards() {
        if (!canSwapPeekedCards) {
            throw new IllegalStateException("No Peek Swap action is currently active");
        }
        deck.swapTopTwo();
        canSwapPeekedCards = false;
    }

    public void declinePeekSwap() {
        if (!canSwapPeekedCards) {
            throw new IllegalStateException("No Peek Swap action is currently active");
        }
        canSwapPeekedCards = false;
    }

    private void validateGameCanPlayCard() {
        if (!setupComplete) {
            throw new IllegalStateException("Game setup has not been completed");
        }

        if (getActivePlayerCount() <= 1) {
            throw new IllegalStateException("Game is over");
        }
    }

    private void validateTargetPlayer(Player targetPlayer, Player currentPlayer) {
        validateTargetPlayerSelection(targetPlayer, currentPlayer);

        if (targetPlayer.getHand().isEmpty()) {
            throw new IllegalStateException("Target player has no cards");
        }
    }

    private void validateTargetPlayerSelection(Player targetPlayer, Player currentPlayer) {
        if (targetPlayer == null) {
            throw new IllegalArgumentException("Target player cannot be null");
        }

        if (!players.contains(targetPlayer)) {
            throw new IllegalArgumentException("Target player must be in the game");
        }

        if (targetPlayer == currentPlayer) {
            throw new IllegalArgumentException("Target player must be different");
        }
    }

    private boolean isCatCard(CardType type) {
        return type == CardType.TACO_CAT
                || type == CardType.BEARD_CAT
                || type == CardType.RAINBOW_RALPHING_CAT
                || type == CardType.HAIRY_POTATO_CAT;
    }

    private void validateTradeCards(Player currentPlayer, Player targetPlayer) {
        if (!currentPlayer.hasCard(CardType.TRADE)) {
            throw new IllegalStateException("Player does not have card of type " + CardType.TRADE);
        }

        if (currentPlayer.getHand().size() < 2) {
            throw new IllegalStateException("Current player has no card to trade");
        }

        if (targetPlayer.getHand().isEmpty()) {
            throw new IllegalStateException("Target player has no cards");
        }
    }

    private void finishCurrentDrawTurn() {
        if (pendingTurnsForCurrentPlayer > 1) {
            pendingTurnsForCurrentPlayer--;
            return;
        }

        pendingTurnsForCurrentPlayer = 0;
        moveToNextActivePlayer();
    }

    private void moveToNextActivePlayer() {
        do {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        } while (!players.get(currentPlayerIndex).isActive());
    }
}
