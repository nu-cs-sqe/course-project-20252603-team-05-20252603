# Game Setup Phase Design

## Goal

The goal of the Game Setup Phase is to initialize a new Exploding Kittens game so that the game is fully ready for the first player's turn. This includes creating players, preparing the deck, dealing starting hands, adding the correct number of Exploding Kitten cards, and setting the first turn.

## Main Classes

### Game

The `Game` class controls the overall game state.

Fields:
- `List<Player> players`
- `Deck deck`
- `int currentPlayerIndex`
- `boolean gameStarted`

Methods:
- `startGame(int numPlayers)`
- `setupGame()`
- `getCurrentPlayer()`
- `nextTurn()`
- `isGameStarted()`

### Player

The `Player` class stores information about each player and their hand.

Fields:
- `String name`
- `List<Card> hand`

Methods:
- `addCard(Card card)`
- `getHand()`
- `getName()`

### Deck

The `Deck` class stores the draw pile and handles deck-related behavior.

Fields:
- `List<Card> cards`

Methods:
- `shuffle()`
- `drawCard()`
- `removeExplodingKittens()`
- `addExplodingKittens(int amount)`
- `dealRandomCards(Player player, int amount)`
- `size()`
- `countCardType(CardType type)`

### Card

The `Card` class represents a single card in the game.

Fields:
- `CardType type`

Methods:
- `getType()`

### CardType

The `CardType` enum represents the different types of cards in the game.

Possible values:
- `EXPLODING_KITTEN`
- `DEFUSE`
- `ATTACK`
- `SKIP`
- `FAVOR`
- `SHUFFLE`
- `SEE_THE_FUTURE`
- `NOPE`

## Setup Algorithm

1. Check that the number of players is between 2 and 5.
2. Create the player objects.
3. Create the starting deck.
4. Remove all Exploding Kitten cards from the deck.
5. Shuffle the deck.
6. Deal 7 random cards to each player.
7. Give each player 1 Defuse card.
8. Add back one fewer Exploding Kitten card than the number of players.
9. Shuffle the deck again.
10. Set `currentPlayerIndex` to 0.
11. Mark the game as started.

## Postconditions

After setup is complete:

- The game has 2 to 5 players.
- Each player has exactly 8 cards.
- Each player has exactly 1 Defuse card.
- The deck contains `number of players - 1` Exploding Kitten cards.
- The deck has been shuffled.
- The current player is set.
- The game is ready for the first turn.

## Boundary Value Analysis

The main boundary for setup is the number of players.

Test cases:

| Number of Players | Expected Result |
|---|---|
| 1 | Invalid |
| 2 | Valid |
| 5 | Valid |
| 6 | Invalid |

The values 2 and 5 should work because they are the minimum and maximum allowed player counts. The values 1 and 6 should fail because they are just outside the valid range.

## Unit Test Plan

We plan to write tests for the following setup behavior:

1. The game rejects fewer than 2 players.
2. The game rejects more than 5 players.
3. The game starts successfully with 2 players.
4. The game starts successfully with 5 players.
5. Each player receives exactly 8 cards.
6. Each player receives exactly 1 Defuse card.
7. The deck contains one fewer Exploding Kitten than the number of players.
8. The game is marked as started after setup.
9. The first player is set correctly.