package domain;

import java.util.List;

public class Game {
    public Game(List<Player> players, Deck deck) {
        if (players == null) {
            throw new IllegalArgumentException("Players list cannot be null");
        }

        if (deck == null) {
            throw new IllegalArgumentException("Deck cannot be null");
        }
    }
}