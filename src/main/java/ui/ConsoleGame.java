package ui;

import domain.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ConsoleGame {
    private final Scanner scanner;
    private final PrintStream output;
    private final Random random;

    private MessageProvider messages;
    private Game game;

    public ConsoleGame() {
        this(System.in, System.out, new Random());
    }

    ConsoleGame(InputStream input, PrintStream output, Random random) {
        this.scanner = new Scanner(input);
        this.output = output;
        this.random = random;
        this.messages = MessageProvider.forLocale(java.util.Locale.ENGLISH);
    }

    public void run() {
        messages = askForLanguage();
        int playerCount = askForPlayerCount();
        List<Player> players = createPlayers(playerCount);

        game = new Game(players, new Deck(random));
        game.setupGame();

        while (!game.isGameOver()) {
            takeTurn();
        }

        output.println();
        output.println(messages.get("game.over"));
        output.println(messages.format("winner", game.getWinner().getName()));
    }

    private MessageProvider askForLanguage() {
        output.println(messages.get("language.prompt"));
        output.println(messages.get("language.english"));
        output.println(messages.get("language.spanish"));
        return MessageProvider.fromLanguageChoice(scanner.nextLine());
    }

    private int askForPlayerCount() {
        while (true) {
            printPrompt("player.count.prompt");

            try {
                int count = Integer.parseInt(scanner.nextLine());

                if (count >= 2 && count <= 5) {
                    return count;
                }

                output.println(messages.get("player.count.invalid.range"));
            } catch (NumberFormatException e) {
                output.println(messages.get("player.count.invalid.number"));
            }
        }
    }

    private List<Player> createPlayers(int playerCount) {
        List<Player> players = new ArrayList<>();

        for (int i = 1; i <= playerCount; i++) {
            printPrompt("player.name.prompt", i);
            String name = scanner.nextLine();

            if (name.isBlank()) {
                name = messages.format("player.default.name", i);
            }

            players.add(new Player(name));
        }

        return players;
    }

    private void takeTurn() {
        Player currentPlayer = game.getCurrentPlayer();

        output.println();
        output.println("==============================");
        output.println(messages.format("current.player", currentPlayer.getName()));
        output.println("==============================");

        boolean turnStillActive = true;

        while (turnStillActive && !game.isGameOver()
                && game.getCurrentPlayer() == currentPlayer) {

            printHand(currentPlayer);

            output.println();
            output.println(messages.get("choose.action"));
            output.println(messages.get("action.play.card"));
            output.println(messages.get("action.cat.pair"));
            output.println(messages.get("action.cat.three"));
            output.println(messages.get("action.draw"));

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
                    output.println(messages.get("invalid.choice"));
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                output.println(messages.format("error.prefix", e.getMessage()));
            }
        }
    }

    private void printHand(Player player) {
        output.println();
        output.println(messages.get("hand"));

        List<Card> hand = player.getHand();

        for (int i = 0; i < hand.size(); i++) {
            output.println(messages.format("indexed.item", i, hand.get(i).getType()));
        }
    }

    private void playOneCard() {
        Player currentPlayer = game.getCurrentPlayer();

        printPrompt("card.index.prompt");
        int index = readInt();

        if (index < 0 || index >= currentPlayer.getHand().size()) {
            output.println(messages.get("invalid.card.index"));
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
            output.println(messages.format("revealed.card", revealed.getType()));
        } else if (type == CardType.SEE_THE_FUTURE) {
            List<Card> cards = game.playSeeTheFuture();
            printCards(messages.get("top.three.cards"), cards);
        } else if (type == CardType.PEEK_SWAP) {
            List<Card> cards = game.playPeekSwap();
            printCards(messages.get("top.two.cards"), cards);

            printPrompt("peek.swap.prompt");
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase(messages.get("yes.answer"))) {
                game.swapPeekedCards();
                output.println(messages.get("cards.swapped"));
            } else {
                game.declinePeekSwap();
                output.println(messages.get("cards.unchanged"));
            }
        } else if (type == CardType.ALTER_THE_FUTURE) {
            List<Card> cards = game.playAlterTheFuture();
            printCards(messages.get("top.three.cards"), cards);

            List<Card> reorderedCards = askForNewOrder(cards);
            game.reorderAlteredFuture(reorderedCards);

            output.println(messages.get("alter.future.reordered"));
        } else {
            game.playCard(type);
        }
    }

    private void playCatPairCombo() {
        Player currentPlayer = game.getCurrentPlayer();

        printPrompt("cat.card.index.prompt");
        int index = readInt();

        if (index < 0 || index >= currentPlayer.getHand().size()) {
            output.println(messages.get("invalid.card.index"));
            return;
        }

        CardType catType = currentPlayer.getHand().get(index).getType();
        Player target = chooseTargetPlayer();

        game.playCatPairCombo(catType, target);
        output.println(messages.get("combo.cat.pair.played"));
    }

    private void playCatThreeCombo() {
        Player currentPlayer = game.getCurrentPlayer();

        printPrompt("cat.card.index.prompt");
        int index = readInt();

        if (index < 0 || index >= currentPlayer.getHand().size()) {
            output.println(messages.get("invalid.card.index"));
            return;
        }

        CardType catType = currentPlayer.getHand().get(index).getType();
        Player target = chooseTargetPlayer();

        CardType requestedType = chooseCardType();

        game.playCatThreeCombo(catType, target, requestedType);
        output.println(messages.get("combo.cat.three.played"));
    }

    private Player chooseTargetPlayer() {
        List<Player> players = game.getPlayers();
        Player currentPlayer = game.getCurrentPlayer();

        output.println();
        output.println(messages.get("target.choose"));

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);

            if (player != currentPlayer && player.isActive()) {
                output.println(messages.format("indexed.item", i, player.getName()));
            }
        }

        int index = readInt();

        if (index < 0 || index >= players.size()) {
            throw new IllegalArgumentException(messages.get("invalid.target.index"));
        }

        return players.get(index);
    }

    private CardType chooseCardType() {
        CardType[] types = CardType.values();

        output.println();
        output.println(messages.get("card.type.choose"));

        for (int i = 0; i < types.length; i++) {
            output.println(messages.format("indexed.item", i, types[i]));
        }

        int index = readInt();

        if (index < 0 || index >= types.length) {
            throw new IllegalArgumentException(messages.get("invalid.card.type.index"));
        }

        return types[index];
    }

    private List<Card> askForNewOrder(List<Card> cards) {
        List<Card> reorderedCards = new ArrayList<>();

        output.println(messages.get("new.order.prompt"));

        while (reorderedCards.size() < cards.size()) {
            printPrompt("new.order.card.prompt", reorderedCards.size());
            int index = readInt();

            if (index < 0 || index >= cards.size()) {
                output.println(messages.get("invalid.index"));
            } else if (reorderedCards.contains(cards.get(index))) {
                output.println(messages.get("duplicate.card.choice"));
            } else {
                reorderedCards.add(cards.get(index));
            }
        }

        return reorderedCards;
    }

    private void printCards(String title, List<Card> cards) {
        output.println();
        output.println(title);

        for (int i = 0; i < cards.size(); i++) {
            output.println(messages.format("indexed.item", i, cards.get(i).getType()));
        }
    }

    private int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(messages.get("valid.number.error"));
        }
    }

    private void printPrompt(String key, Object... arguments) {
        output.print(messages.format(key, arguments));
        output.print(" ");
    }
}
