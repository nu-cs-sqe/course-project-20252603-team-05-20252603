# User Story: Single Player Turn

As a player of Exploding Kittens, I want to take my turn by optionally playing cards and then drawing from the deck so that the game can progress to the next player according to the rules.

## Acceptance Criteria

✅ The current player is identified at the start of the turn.

✅ The player may choose to play zero or more playable cards from their hand.

✅ When a player plays a card, that card is removed from their hand.

✅ Played cards are placed into the discard pile.

✅ The player must draw one card to end their turn unless a played card changes that rule.

✅ If the player draws a non-Exploding Kitten card, it is added to their hand.

✅ If the player draws an Exploding Kitten card and has a Defuse card, the Defuse card is used and the player stays in the game.

✅ If the player draws an Exploding Kitten card and has no Defuse card, the player is eliminated.

✅ After the turn ends, the game advances to the next player who is still active.

## Card Functionality Included in This Feature

During a player’s turn, the system supports the basic effects of playable cards.

- **Skip**: Ends the current player’s turn without drawing.
- **Attack**: Ends the current player’s turn without drawing and forces the next player to take two turns.
- **Shuffle**: Shuffles the deck.
- **See the Future**: Shows the top three cards of the deck without removing them.
- **Nope**: Cancels the effect of another playable card.
- **Defuse**: Automatically prevents elimination when the player draws an Exploding Kitten.
- **Exploding Kitten**: Eliminates the player unless they have a Defuse card.
- **Cat Cards**: Have no effect by themselves in the bare minimum version.

---

# Use Case 1: Take a Normal Turn

**Actor:** Player

## Preconditions

- The game setup phase is complete.
- The current player has a hand of cards.
- The deck is not empty.

## Main Flow

1. System identifies the current player.
2. System shows the player their hand.
3. Player chooses whether to play a card.
4. If the player plays a card, the system removes it from their hand.
5. System places the played card into the discard pile.
6. Player may continue playing cards or choose to stop.
7. Player draws one card from the deck.
8. If the drawn card is not an Exploding Kitten, the system adds it to the player’s hand.
9. System ends the current player’s turn.
10. System advances to the next active player.

## Alternate Flows

### 3.a Player chooses not to play any cards

- System skips to Step 7.

### 4.a Player plays a card with an effect

- If the player plays a **Skip**, the system ends their turn without drawing.
- If the player plays an **Attack**, the system ends their turn without drawing and gives the next player two turns.
- If the player plays a **Shuffle**, the system shuffles the deck.
- If the player plays a **See the Future**, the system shows the top three cards of the deck.
- If the player plays a **Nope**, the system cancels the previous playable card effect.
- If the player plays a **Cat Card**, the system does nothing in the bare minimum version.

### 7.a Player draws an Exploding Kitten and has a Defuse card

- System removes the Defuse card from the player’s hand.
- System keeps the player active.
- System places the Defuse card into the discard pile.
- System ends the player’s turn.

### 7.b Player draws an Exploding Kitten and does not have a Defuse card

- System eliminates the player.
- System ends the player’s turn.
- System advances to the next active player.

## Postconditions

- The player’s hand is updated.
- The deck contains one fewer card unless modified by card effects.
- The discard pile contains any played cards.
- The turn advances to the next active player.