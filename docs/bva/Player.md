## Boundary Value Analysis

### Method: `Player(String name)`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| P1 | Name is `null` | Throws `IllegalArgumentException` | :y:          |
| P2 | Name is empty string `""` | Throws `IllegalArgumentException` | :y:          |
| P3 | Name is whitespace only `"   "` | Throws `IllegalArgumentException` | :y:          |
| P4 | Name is one character `"A"` | Player is created successfully | :y:          |
| P5 | Name is normal string `"Anthony"` | Player is created successfully | :y:          |

### Method: `addCard(Card card)`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| P6 | Card is `null` | Throws `IllegalArgumentException` | :y:          |
| P7 | Player has 0 cards, add 1 valid card | Hand size becomes 1 and hand contains the card | :y:          |
| P8 | Player has 1 card, add another valid card | Hand size becomes 2 and hand contains both cards | :y:          |

### Method: `getHand()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| P9 | Player has no cards | Returns an empty hand | :y:          |
| P10 | Player has 1 card | Returns a hand containing exactly that card | :y:          |
| P11 | Player has multiple cards | Returns all cards in the player's hand | :y:          |
| P12 | External code tries to modify the returned hand | Player's internal hand is not modified | :y:          |

### Method: `getName()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| P13 | Player name is one character `"A"` | Returns `"A"` | :y:          |
| P14 | Player name is normal string `"Anthony"` | Returns `"Anthony"` | :y:          |

### Method: `isActive()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| P15 | Player has just been created | Returns `true` | :n:          |
| P16 | Player has been eliminated | Returns `false` | :n:          |

### Method: `eliminate()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| P17 | Player is active | Player becomes inactive | :n:          |
| P18 | Player is already inactive | Player remains inactive | :n:          |

### Method: `hasCard(CardType type)`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| P19 | Player has no cards | Returns `false` | :n:          |
| P20 | Player has cards, but none match the requested type | Returns `false` | :n:          |
| P21 | Player has one card matching the requested type | Returns `true` | :n:          |
| P22 | Requested type is `null` | Returns `false` | :n:          |

### Method: `removeCard(CardType type)`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| P23 | Player has no cards | Throws `IllegalStateException` | :n:          |
| P24 | Player has cards, but none match the requested type | Throws `IllegalStateException` | :n:          |
| P25 | Player has one card matching the requested type | Removes and returns that card | :n:          |
| P26 | Player has multiple cards matching the requested type | Removes and returns one matching card | :n:          |
| P27 | Requested type is `null` | Throws `IllegalStateException` | :n:          |

### Method: `countCardsOfType(CardType type)`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| P28 | Requested type is `null` | Throws `IllegalArgumentException` | :n:          |
| P29 | Player has no cards | Returns `0` | :n:          |
| P30 | Player has cards, but none match the requested type | Returns `0` | :n:          |
| P31 | Player has one card matching the requested type | Returns `1` | :n:          |
| P32 | Player has multiple cards matching the requested type | Returns the number of matching cards | :n:          |
