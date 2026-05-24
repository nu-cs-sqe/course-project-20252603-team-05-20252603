## Boundary Value Analysis

### Method: `Player(String name)`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|---|
| P1 | Name is `null` | Throws `IllegalArgumentException` | :x: |
| P2 | Name is empty string `""` | Throws `IllegalArgumentException` | :x: |
| P3 | Name is whitespace only `"   "` | Throws `IllegalArgumentException` | :x: |
| P4 | Name is one character `"A"` | Player is created successfully | :x: |
| P5 | Name is normal string `"Anthony"` | Player is created successfully | :x: |

### Method: `addCard(Card card)`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|---|
| P6 | Card is `null` | Throws `IllegalArgumentException` | :x: |
| P7 | Player has 0 cards, add 1 valid card | Hand size becomes 1 and hand contains the card | :x: |
| P8 | Player has 1 card, add another valid card | Hand size becomes 2 and hand contains both cards | :x: |

### Method: `getHand()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|---|
| P9 | Player has no cards | Returns an empty hand | :x: |
| P10 | Player has 1 card | Returns a hand containing exactly that card | :x: |
| P11 | Player has multiple cards | Returns all cards in the player's hand | :x: |
| P12 | External code tries to modify the returned hand | Player's internal hand is not modified | :x: |

### Method: `getName()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|---|
| P13 | Player name is one character `"A"` | Returns `"A"` | :x: |
| P14 | Player name is normal string `"Anthony"` | Returns `"Anthony"` | :x: |