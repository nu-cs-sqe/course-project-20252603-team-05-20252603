## Boundary Value Analysis

### Method: `Game(List<Player> players, Deck deck)`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| G1 | Players list is `null` | Throws `IllegalArgumentException` | :y: |
| G2 | Deck is `null` | Throws `IllegalArgumentException` | :x: |
| G3 | Players list has 0 players | Throws `IllegalArgumentException` | :x: |
| G4 | Players list has 1 player | Throws `IllegalArgumentException` | :x: |
| G5 | Players list has minimum valid count: 2 players | Game is created successfully | :x: |
| G6 | Players list has normal valid count: 3 players | Game is created successfully | :x: |
| G7 | Players list has normal valid count: 4 players | Game is created successfully | :x: |
| G8 | Players list has maximum valid count: 5 players | Game is created successfully | :x: |
| G9 | Players list has 6 players | Throws `IllegalArgumentException` | :x: |
| G10 | Players list contains a `null` player | Throws `IllegalArgumentException` | :x: |

### Method: `setupGame()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| G11 | Game has valid players and valid deck | Game setup completes successfully | :x: |
| G12 | Game has 2 players | Adds 1 Exploding Kitten to the deck | :x: |
| G13 | Game has 3 players | Adds 2 Exploding Kittens to the deck | :x: |
| G14 | Game has 4 players | Adds 3 Exploding Kittens to the deck | :x: |
| G15 | Game has 5 players | Adds 4 Exploding Kittens to the deck | :x: |
| G16 | Game setup is completed | Each player has exactly 1 Defuse card | :x: |
| G17 | Game setup is completed | Each player receives the correct number of starting cards | :x: |
| G18 | Game setup is completed | Deck is shuffled after Exploding Kittens are inserted | :x: |
| G19 | `setupGame()` is called twice | Throws `IllegalStateException` or prevents duplicate setup | :x: |

### Method: `getCurrentPlayer()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| G20 | Game has not started or setup has not completed | Returns `null` or throws `IllegalStateException` | :x: |
| G21 | Game has started with 2 players | Returns one of the active players | :x: |
| G22 | Game has started with 4 players | Returns the player whose turn it currently is | :x: |
| G23 | Current player has been eliminated | Returns the next active player | :x: |
| G24 | Game is over | Returns `null` or throws `IllegalStateException` | :x: |

### Method: `endTurn()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| G25 | Game has not started | Throws `IllegalStateException` | :x: |
| G26 | Game has 2 active players | Current player changes to the other player | :x: |
| G27 | Game has 4 active players and current player is not last | Current player advances to the next player | :x: |
| G28 | Current player is the last player in the list | Turn order wraps around to the first active player | :x: |
| G29 | Next player in turn order is eliminated | Turn skips eliminated player and advances to the next active player | :x: |
| G30 | Game is already over | Throws `IllegalStateException` | :x: |

### Method: `drawCard()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| G31 | Game has not started | Throws `IllegalStateException` | :x: |
| G32 | Deck is empty | Throws `IllegalStateException` | :x: |
| G33 | Current player draws a normal card | Card is added to current player's hand | :x: |
| G34 | Current player draws a normal card | Turn ends after drawing | :x: |
| G35 | Current player draws an Exploding Kitten and has a Defuse | Defuse is used and player remains active | :x: |
| G36 | Current player draws an Exploding Kitten and has no Defuse | Player is eliminated | :x: |
| G37 | Current player explodes while 3 or more players are alive | Game continues | :x: |
| G38 | Current player explodes while exactly 2 players are alive | Game ends | :x: |
| G39 | Game is already over | Throws `IllegalStateException` | :x: |

### Method: `isGameOver()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| G40 | Game has not started | Returns `false` | :x: |
| G41 | Game has 5 active players | Returns `false` | :x: |
| G42 | Game has 3 active players | Returns `false` | :x: |
| G43 | Game has 2 active players | Returns `false` | :x: |
| G44 | Game has exactly 1 active player | Returns `true` | :x: |
| G45 | Game has 0 active players | Returns `true` or throws error for invalid state | :x: |

### Method: `getWinner()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| G46 | Game has not started | Returns `null` or throws `IllegalStateException` | :x: |
| G47 | Game has more than 1 active player | Returns `null` | :x: |
| G48 | Game has exactly 1 active player | Returns the remaining active player | :x: |
| G49 | Game has 0 active players | Returns `null` or throws error for invalid state | :x: |

### Method: `getPlayers()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| G50 | Game has 2 players | Returns list of 2 players | :x: |
| G51 | Game has 5 players | Returns list of 5 players | :x: |
| G52 | External code tries to modify returned players list | Game's internal player list is not modified | :x: |

### Method: `getDeck()`

| Test Case | State of the System | Expected Output | Implemented? |
|---|---|---|--------------|
| G53 | Game has a valid deck | Returns the game deck | :x: |
| G54 | Game setup has completed | Returns deck with updated card count after dealing and inserting Exploding Kittens | :x: |