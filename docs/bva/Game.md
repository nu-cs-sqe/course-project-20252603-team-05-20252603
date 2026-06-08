## Boundary Value Analysis

### Method: `Game(List<Player> players, Deck deck)`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| G1 | Players list is `null` | Throws `IllegalArgumentException` | :y: |
| G2 | Deck is `null` | Throws `IllegalArgumentException` | :y: |
| G3 | Players list has 0 players | Throws `IllegalArgumentException` | :y: |
| G4 | Players list has 1 player | Throws `IllegalArgumentException` | :y: |
| G5 | Players list has minimum valid count: 2 players | Game is created successfully | :y: |
| G6 | Players list has normal valid count: 3 players | Game is created successfully | :y: |
| G7 | Players list has normal valid count: 4 players | Game is created successfully | :y: |
| G8 | Players list has maximum valid count: 5 players | Game is created successfully | :y: |
| G9 | Players list has 6 players | Throws `IllegalArgumentException` | :y: |
| G10 | Players list contains a `null` player | Throws `IllegalArgumentException` | :y: |

### Method: `setupGame()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| G11 | Game has valid players and valid deck | Game setup completes successfully | :y: |
| G12 | Game has 2 players | Adds 1 Exploding Kitten to the deck | :y: |
| G13 | Game has 3 players | Adds 2 Exploding Kittens to the deck | :y: |
| G14 | Game has 4 players | Adds 3 Exploding Kittens to the deck | :y: |
| G15 | Game has 5 players | Adds 4 Exploding Kittens to the deck | :y: |
| G16 | Game setup is completed | Each player has exactly 1 Defuse card | :y: |
| G17 | Game setup is completed | Each player receives the correct number of starting cards | :y: |
| G18 | Game setup is completed | Deck is shuffled after Exploding Kittens are inserted | :y: |
| G19 | `setupGame()` is called twice | Throws `IllegalStateException` or prevents duplicate setup | :y: |

### Method: `getCurrentPlayer()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| G20 | Game has not started or setup has not completed | Returns `null` or throws `IllegalStateException` | :y: |
| G21 | Game has started with 2 players | Returns one of the active players | :y: |
| G22 | Game has started with 4 players | Returns the player whose turn it currently is | :y: |
| G23 | Current player has been eliminated | Returns the next active player | :y: |
| G24 | Game is over | Returns `null` or throws `IllegalStateException` | :y: |

### Method: `endTurn()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| G25 | Game has not started | Throws `IllegalStateException` | :y: |
| G26 | Game has 2 active players | Current player changes to the other player | :y: |
| G27 | Game has 4 active players and current player is not last | Current player advances to the next player | :y: |
| G28 | Current player is the last player in the list | Turn order wraps around to the first active player | :y: |
| G29 | Next player in turn order is eliminated | Turn skips eliminated player and advances to the next active player | :y: |
| G30 | Game is already over | Throws `IllegalStateException` | :y: |

### Method: `drawCard()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| G31 | Game has not started | Throws `IllegalStateException` | :y: |
| G32 | Deck is empty | Throws `IllegalStateException` | :y: |
| G33 | Current player draws a normal card | Card is added to current player's hand | :y: |
| G34 | Current player draws a normal card | Turn ends after drawing | :y: |
| G35 | Current player draws an Exploding Kitten and has a Defuse | Defuse is used and player remains active | :y: |
| G36 | Current player draws an Exploding Kitten and has no Defuse | Player is eliminated | :y: |
| G37 | Current player explodes while 3 or more players are alive | Game continues | :y: |
| G38 | Current player explodes while exactly 2 players are alive | Game ends | :y: |
| G39 | Game is already over | Throws `IllegalStateException` | :y: |

### Method: `isGameOver()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| G40 | Game has not started | Returns `false` | :y: |
| G41 | Game has 5 active players | Returns `false` | :y: |
| G42 | Game has 3 active players | Returns `false` | :y: |
| G43 | Game has 2 active players | Returns `false` | :y: |
| G44 | Game has exactly 1 active player | Returns `true` | :y: |
| G45 | Game has 0 active players | Returns `true` or throws error for invalid state | :y: |

### Method: `getWinner()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| G46 | Game has not started | Returns `null` or throws `IllegalStateException` | :y: |
| G47 | Game has more than 1 active player | Returns `null` | :y: |
| G48 | Game has exactly 1 active player | Returns the remaining active player | :y: |
| G49 | Game has 0 active players | Returns `null` or throws error for invalid state | :y: |

### Method: `getPlayers()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| G50 | Game has 2 players | Returns list of 2 players | :y: |
| G51 | Game has 5 players | Returns list of 5 players | :y: |
| G52 | External code tries to modify returned players list | Game's internal player list is not modified | :y: |

### Method: `getDeck()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| G53 | Game has a valid deck | Returns the game deck | :y: |
| G54 | Game setup has completed | Returns deck with updated card count after dealing and inserting Exploding Kittens | :y: |

### Method: `getDiscardPile()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| G55 | Game has just been created | Returns an empty discard pile | :y: |
| G56 | External code tries to modify returned discard pile | Game's internal discard pile is not modified | :y: |

### Method: `playCard(CardType type)`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| G57 | Card type is `null` | Throws `IllegalArgumentException` | :y: |
| G58 | Game has not started | Throws `IllegalStateException` | :y: |
| G59 | Game is already over | Throws `IllegalStateException` | :y: |
| G60 | Current player does not have the requested card type | Throws `IllegalStateException` | :y: |
| G61 | Current player has one card matching the requested type | Removes that card from the current player's hand | :y: |
| G62 | Current player has one card matching the requested type | Adds that card to the discard pile | :y: |
| G63 | Current player has multiple cards matching the requested type | Removes only one matching card | :y: |
| G64 | Current player plays a card | Current player does not change | :y: |
| G65 | Current player plays `TACO_CAT` | Removes `TACO_CAT` from hand and adds it to discard pile | :y: |
| G66 | Current player plays `BEARD_CAT` | Removes `BEARD_CAT` from hand and adds it to discard pile | :y: |
| G67 | Current player plays `RAINBOW_RALPHING_CAT` | Removes `RAINBOW_RALPHING_CAT` from hand and adds it to discard pile | :y: |
| G68 | Current player plays `HAIRY_POTATO_CAT` | Removes `HAIRY_POTATO_CAT` from hand and adds it to discard pile | :y: |
| G69 | Current player plays a Cat Card | Current player does not change | :y: |
| G70 | Current player plays a Cat Card | Deck size does not change | :y: |
| G71 | Current player plays a Cat Card | No player is eliminated | :y: |
| G72 | Current player plays `ATTACK` | Removes `ATTACK` from hand and adds it to discard pile | :y: |
| G73 | Current player plays `ATTACK` with 2 active players | Current player changes to the next player | :y: |
| G74 | Attacked player draws the first card of the attack | Current player remains the attacked player | :y: |
| G75 | Attacked player draws the second card of the attack | Current player advances to the next active player | :y: |
| G76 | Current player plays `ATTACK` and next player in order is eliminated | Current player changes to the next active player | :y: |
| G77 | Current player plays `ATTACK` | Deck size does not change | :y: |
| G78 | Current player plays `ATTACK` | No player is eliminated | :y: |
| G79 | Attacked player plays `ATTACK` before drawing | Next player must take 4 turns | :y: |
| G80 | Attacked player plays `ATTACK` after one attacked draw | Next player must take 3 turns | :y: |
| G81 | Current player draws an Exploding Kitten with no protection while 3 players are active | Current player is eliminated | :y: |
| G82 | Current player draws an Exploding Kitten with no protection while 3 players are active | Game continues with the next active player | :y: |
| G83 | Current player draws an Exploding Kitten with no protection while exactly 2 players are active | Current player is eliminated and the other player wins | :y: |
| G84 | Current player draws an Exploding Kitten with no protection | Exploding Kitten is not added to the player's hand | :y: |
| G85 | Current player has 0 Defuse cards and draws an Exploding Kitten | Player is eliminated | :y: |
| G86 | Current player has exactly 1 Defuse card and draws an Exploding Kitten | Exactly 1 Defuse is removed and the player remains active | :y: |
| G87 | Current player has 2 Defuse cards and draws an Exploding Kitten | Exactly 1 Defuse is removed and 1 Defuse remains | :y: |
| G88 | A Defuse is used against an Exploding Kitten | Used Defuse is added to the discard pile | :n: |
| G89 | A Defuse is used against an Exploding Kitten | Current player's turn ends and advances to the next active player | :y: |
