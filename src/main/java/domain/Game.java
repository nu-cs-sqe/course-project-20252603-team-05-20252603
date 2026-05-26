package domain;

import java.util.List;

public class Game {
    private static final int STARTING_RANDOM_CARDS = 5;

    private final List<Player> players;
    private final Deck deck;

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

        int playersChecked = 0;

        while (playersChecked < players.size()) {
            Player currentPlayer = players.get(currentPlayerIndex);

            if (currentPlayer.isActive()) {
                return currentPlayer;
            }

            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            playersChecked++;
        }

        return null;
    }
}