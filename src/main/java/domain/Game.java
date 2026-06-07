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

        do {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        } while (!players.get(currentPlayerIndex).isActive());
    }

    public void drawCard() {
        if (!setupComplete) {
            throw new IllegalStateException("Game setup has not been completed");
        }

        if (getActivePlayerCount() <= 1) {
            throw new IllegalStateException("Game is over");
        }

        Player currentPlayer = getCurrentPlayer();
        Card drawnCard = deck.draw();

        if (drawnCard.getType() == CardType.EXPLODING_KITTEN) {
            if (currentPlayer.hasCard(CardType.DEFUSE)) {
                discardPile.add(currentPlayer.removeCard(CardType.DEFUSE));
                endTurn();
            } else if (currentPlayer.hasCard(CardType.SHIELD)) {
                discardPile.add(currentPlayer.removeCard(CardType.SHIELD));
                endTurn();
            } else {
                currentPlayer.eliminate();

                if (getActivePlayerCount() > 1) {
                    endTurn();
                }
            }

            return;
        }

        currentPlayer.addCard(drawnCard);
        endTurn();
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

        if (!setupComplete) {
            throw new IllegalStateException("Game setup has not been completed");
        }

        if (getActivePlayerCount() <= 1) {
            throw new IllegalStateException("Game is over");
        }

        Player currentPlayer = getCurrentPlayer();
        Card playedCard = currentPlayer.removeCard(type);
        discardPile.add(playedCard);

        switch (type) {
            case SKIP:
                endTurn();
                break;
            default:
                break;
        }
    }
}
