Please either include the complete game rules within this document or provide a direct link to them.

Base Game Ruleset:
# Exploding Kittens Rules and Game Specification

Based on the Grab & Game Edition rules.

## Game Information
**Players:** 2–4 players
**Deck Size:** 42 cards total
**Win Condition:** Be the last surviving player. All other players eventually explode and are eliminated.

---

# Card Distribution

| Card Type | Amount |
|-----------|--------|
| Exploding Kitten | 3 |
| Defuse | 5 |
| Attack | 3 |
| Skip | 3 |
| Favor | 4 |
| Shuffle | 4 |
| See the Future | 4 |
| Nope | 4 |
| Taco Cat | 4 |
| Beard Cat | 4 |
| Rainbow Ralphing Cat | 4 |
| Hairy Potato Cat | 4 |

Total cards: **46** 

---

# Game Setup

## Initial Deck Construction
1. Remove all **Exploding Kitten** cards from the deck.
2. Remove all **Defuse** cards from the deck.
3. Deal **1 Defuse** card to each player.
4. Deal **5 additional cards** to each player.
5. Each player now starts with **6 cards total**:
    - 5 regular cards
    - 1 Defuse card
6. Return remaining Defuse cards back into the Draw Pile.
7. Insert Exploding Kittens back into the deck:
    - Number inserted = **players − 1**
    - Extra Exploding Kittens are removed from the game.
8. Shuffle the deck.
9. Place the Draw Pile face down.
10. Create an empty Discard Pile.
11. Select a starting player.

### Example
- 4 players → insert 3 Exploding Kittens
- 3 players → insert 2 Exploding Kittens

This guarantees only one player survives.

---

# Turn Structure

Players take turns clockwise.

During a turn:

## Step 1: Play Phase

Player may:

- Play **zero cards**
- Play **one card**
- Play **multiple cards**

Played cards are placed face-up onto the Discard Pile.
Player may continue playing cards until they choose to stop.

## Step 2: Draw Phase

Player draws the top card from the Draw Pile.
Drawing ends the player's turn unless modified by card effects.

---

# Elimination Rules

## Drawing an Exploding Kitten
If player draws an Exploding Kitten:

### Has Defuse
1. Play Defuse.
2. Discard Defuse.
3. Reinsert Exploding Kitten anywhere into Draw Pile secretly.
4. Player survives.
5. Turn ends.

### No Defuse
1. Player explodes.
2. Player is eliminated.
3. Exploding Kitten remains visible.
4. Remaining cards are discarded face down.

---

# Card Effects

## Attack (3)
Effect:
- End current turn immediately.
- Do not draw.
- Next player takes **2 turns**.

Attack cards stack.

Example:
If attacked player uses another Attack:

Next player receives:
remaining turns + 2 additional turns.

---

## Skip (3)

Effect:
- End current turn immediately.
- No draw occurs.

Against Attack:
One Skip removes only one pending turn.

Example:
Attack (2 turns)
Play Skip → 1 turn remains
Play another Skip → all turns cleared.

---

## Favor (4)

Effect:
- Choose another player.
- That player gives one card from their hand to the player who played Favor.

---

## Shuffle (4)

Effect:
Shuffle the Draw Pile randomly.

---

## See the Future (4)

Effect:
Privately inspect top **2 cards** of Draw Pile.

Cards return unchanged and in same order.

---

## Nope (4)

Effect:
Cancel another player's action.

Restrictions:
Cannot cancel:
- Exploding Kitten
- Defuse

May be played:
- At any time
- Even outside player's turn

Canceled cards still move to discard pile.

---

# Cat Cards

Types:
- Taco Cat
- Beard Cat
- Rainbow Ralphing Cat
- Hairy Potato Cat

Each has 4 copies.

Cat cards have no individual effect.

---

# Special Combos

## Two of a Kind

Requirement:
Play two matching cards.

Examples:
- Two Attacks
- Two Shuffles
- Two Taco Cats

Effect:
Steal one random card from another player.

Ignore original card effects.

---

## Three of a Kind

Requirement:
Play three matching cards.

Effect:
Name a card.

If target player has card:
- Take it

Otherwise:
- Receive nothing

Ignore original card effects.

---

# Additional Rules

- No minimum hand size.
- No maximum hand size.
- Running out of cards causes no special action.
- Players continue normally and draw again next turn.

---

# Game End Condition

Game ends when only one player remains alive.
That player wins. 


Additional Cards:

## Trade

Trade is Anthony's invented card.

Effect:
- Choose another player.
- Discard the Trade card.
- The player who played Trade swaps one card with the chosen player.
- The discarded Trade card cannot be swapped.

## Mark

Mark is an implemented additional card.

Effect:
- Choose another player.
- Discard the Mark card.
- Reveal one card from the chosen player's hand.
- The revealed card stays in the chosen player's hand.

Additional Win Condition:
