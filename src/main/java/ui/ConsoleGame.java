package ui;

import domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ConsoleGame {
    private final Scanner scanner = new Scanner(System.in);
    private Game game;

    public void run() {
        int playerCount = askForPlayerCount();
        List<Player> players = createPlayers(playerCount);

        game = new Game(players, new Deck(new Random()));
        game.setupGame();

        while (!game.isGameOver()) {
            takeTurn();
        }

        System.out.println("\nGame over!");
        System.out.println("Winner: " + game.getWinner().getName());
    }

    private int askForPlayerCount() {
        while (true) {
            System.out.print("How many players? ");

            try {
                int count = Integer.parseInt(scanner.nextLine());

                if (count >= 2 && count <= 5) {
                    return count;
                }

                System.out.println("Please enter a number from 2 to 5.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private List<Player> createPlayers(int playerCount) {
        List<Player> players = new ArrayList<>();

        for (int i = 1; i <= playerCount; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String name = scanner.nextLine();

            if (name.isBlank()) {
                name = "Player " + i;
            }

            players.add(new Player(name));
        }

        return players;
    }

    private void takeTurn() {
        Player currentPlayer = game.getCurrentPlayer();

        System.out.println("\n==============================");
        System.out.println("Current player: " + currentPlayer.getName());
        System.out.println("==============================");

        boolean turnStillActive = true;

        while (turnStillActive && !game.isGameOver()
                && game.getCurrentPlayer() == currentPlayer) {

            printHand(currentPlayer);

            System.out.println("\nChoose an action:");
            System.out.println("1. Play one card");
            System.out.println("2. Play Cat Pair Combo");
            System.out.println("3. Play Cat Three Combo");
            System.out.println("4. Draw card");

            String choice = scanner.nextLine();

            try {
                if (choice.equals("1")) {
                    playOneCard();
                } else if (choice.equals("2")) {
                    playCatPairCombo();
                } else if (choice.equals("3")) {
                    playCatThreeCombo();
                } else if (choice.equals("4")) {
                    game.drawCard();
                    turnStillActive = false;
                } else {
                    System.out.println("Invalid choice.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void printHand(Player player) {
        System.out.println("\nHand:");

        List<Card> hand = player.getHand();

        for (int i = 0; i < hand.size(); i++) {
            System.out.println(i + ". " + hand.get(i).getType());
        }
    }

    private void playOneCard() {
        Player currentPlayer = game.getCurrentPlayer();

        System.out.print("Enter card index: ");
        int index = readInt();

        if (index < 0 || index >= currentPlayer.getHand().size()) {
            System.out.println("Invalid card index.");
            return;
        }

        Card selectedCard = currentPlayer.getHand().get(index);
        CardType type = selectedCard.getType();

        if (type == CardType.FAVOR
                || type == CardType.TRADE
                || type == CardType.STEAL) {

            Player target = chooseTargetPlayer();
            game.playCard(type, target);
        } else if (type == CardType.MARK) {
            Player target = chooseTargetPlayer();
            Card revealed = game.playMark(target);
            System.out.println("Revealed card: " + revealed.getType());
        } else if (type == CardType.SEE_THE_FUTURE) {
            List<Card> cards = game.playSeeTheFuture();
            printCards("Top 3 cards:", cards);
        } else if (type == CardType.PEEK_SWAP) {
            List<Card> cards = game.playPeekSwap();
            printCards("Top 2 cards:", cards);

            System.out.print("Swap these two cards? y/n: ");
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("y")) {
                game.swapPeekedCards();
                System.out.println("Cards swapped.");
            } else {
                game.declinePeekSwap();
                System.out.println("Cards left unchanged.");
            }
        } else if (type == CardType.ALTER_THE_FUTURE) {
            List<Card> cards = game.playAlterTheFuture();
            printCards("Top 3 cards:", cards);

            List<Card> reorderedCards = askForNewOrder(cards);
            game.reorderAlteredFuture(reorderedCards);

            System.out.println("Top cards reordered.");
        } else {
            game.playCard(type);
        }
    }

    private void playCatPairCombo() {
        Player currentPlayer = game.getCurrentPlayer();

        System.out.print("Enter index of Cat Card type to use: ");
        int index = readInt();

        if (index < 0 || index >= currentPlayer.getHand().size()) {
            System.out.println("Invalid card index.");
            return;
        }

        CardType catType = currentPlayer.getHand().get(index).getType();
        Player target = chooseTargetPlayer();

        game.playCatPairCombo(catType, target);
        System.out.println("Cat Pair Combo played.");
    }

    private void playCatThreeCombo() {
        Player currentPlayer = game.getCurrentPlayer();

        System.out.print("Enter index of Cat Card type to use: ");
        int index = readInt();

        if (index < 0 || index >= currentPlayer.getHand().size()) {
            System.out.println("Invalid card index.");
            return;
        }

        CardType catType = currentPlayer.getHand().get(index).getType();
        Player target = chooseTargetPlayer();

        CardType requestedType = chooseCardType();

        game.playCatThreeCombo(catType, target, requestedType);
        System.out.println("Cat Three Combo played.");
    }

    private Player chooseTargetPlayer() {
        List<Player> players = game.getPlayers();
        Player currentPlayer = game.getCurrentPlayer();

        System.out.println("\nChoose target player:");

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);

            if (player != currentPlayer && player.isActive()) {
                System.out.println(i + ". " + player.getName());
            }
        }

        int index = readInt();

        if (index < 0 || index >= players.size()) {
            throw new IllegalArgumentException("Invalid target player index");
        }

        return players.get(index);
    }

    private CardType chooseCardType() {
        CardType[] types = CardType.values();

        System.out.println("\nChoose requested card type:");

        for (int i = 0; i < types.length; i++) {
            System.out.println(i + ". " + types[i]);
        }

        int index = readInt();

        if (index < 0 || index >= types.length) {
            throw new IllegalArgumentException("Invalid card type index");
        }

        return types[index];
    }

    private List<Card> askForNewOrder(List<Card> cards) {
        List<Card> reorderedCards = new ArrayList<>();

        System.out.println("Enter the new order using indexes 0, 1, 2.");

        while (reorderedCards.size() < cards.size()) {
            System.out.print("Card " + reorderedCards.size() + ": ");
            int index = readInt();

            if (index < 0 || index >= cards.size()) {
                System.out.println("Invalid index.");
            } else if (reorderedCards.contains(cards.get(index))) {
                System.out.println("You already chose that card.");
            } else {
                reorderedCards.add(cards.get(index));
            }
        }

        return reorderedCards;
    }

    private void printCards(String title, List<Card> cards) {
        System.out.println("\n" + title);

        for (int i = 0; i < cards.size(); i++) {
            System.out.println(i + ". " + cards.get(i).getType());
        }
    }

    private int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter a valid number");
        }
    }
}