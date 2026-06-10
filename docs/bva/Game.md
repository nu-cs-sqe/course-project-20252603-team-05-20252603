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
|-----------|---|---|--------------|
| G57 | Card type is `null` | Throws `IllegalArgumentException` | :y: |
| G58 | Game has not started | Throws `IllegalStateException` | :y: |
| G59 | Game is already over | Throws `IllegalStateException` | :y: |
| G60 | Current player does not have the requested card type | Throws `IllegalStateException` | :y: |
| G61 | Current player has one card matching the requested type | Removes that card from the current player's hand | :y: |
| G62 | Current player has one card matching the requested type | Adds that card to the discard pile | :y: |
| G63 | Current player has multiple cards matching the requested type | Removes only one matching card | :y: |
| G64 | Current player plays a card | Current player does not change | :y: |

#### Cat Cards
| Test Case | State of the System | Expected Output | Implemented? |
|-----------|---|---|--------------|
| G65 | Current player plays `TACO_CAT` | Removes `TACO_CAT` from hand and adds it to discard pile | :y: |
| G66 | Current player plays `BEARD_CAT` | Removes `BEARD_CAT` from hand and adds it to discard pile | :y: |
| G67 | Current player plays `RAINBOW_RALPHING_CAT` | Removes `RAINBOW_RALPHING_CAT` from hand and adds it to discard pile | :y: |
| G68 | Current player plays `HAIRY_POTATO_CAT` | Removes `HAIRY_POTATO_CAT` from hand and adds it to discard pile | :y: |
| G69 | Current player plays a Cat Card | Current player does not change | :y: |
| G70 | Current player plays a Cat Card | Deck size does not change | :y: |
| G71 | Current player plays a Cat Card | No player is eliminated | :y: |

#### Attack
| Test Case | State of the System | Expected Output | Implemented? |
|-----------|---|---|--------------|
| G72 | Current player plays `ATTACK` | Removes `ATTACK` from hand and adds it to discard pile | :y: |
| G73 | Current player plays `ATTACK` with 2 active players | Current player changes to the next player | :y: |
| G74 | Attacked player draws the first card of the attack | Current player remains the attacked player | :y: |
| G75 | Attacked player draws the second card of the attack | Current player advances to the next active player | :y: |
| G76 | Current player plays `ATTACK` and next player in order is eliminated | Current player changes to the next active player | :y: |
| G77 | Current player plays `ATTACK` | Deck size does not change | :y: |
| G78 | Current player plays `ATTACK` | No player is eliminated | :y: |
| G79 | Attacked player plays `ATTACK` before drawing | Next player must take 4 turns | :y: |
| G80 | Attacked player plays `ATTACK` after one attacked draw | Next player must take 3 turns | :y: |

#### Exploding Kitten / Defuse
| Test Case | State of the System | Expected Output | Implemented? |
|-----------|---|---|--------------|
| G81 | Current player draws an Exploding Kitten with no protection while 3 players are active | Current player is eliminated | :y: |
| G82 | Current player draws an Exploding Kitten with no protection while 3 players are active | Game continues with the next active player | :y: |
| G83 | Current player draws an Exploding Kitten with no protection while exactly 2 players are active | Current player is eliminated and the other player wins | :y: |
| G84 | Current player draws an Exploding Kitten with no protection | Exploding Kitten is not added to the player's hand | :y: |
| G85 | Current player has 0 Defuse cards and draws an Exploding Kitten | Player is eliminated | :y: |
| G86 | Current player has exactly 1 Defuse card and draws an Exploding Kitten | Exactly 1 Defuse is removed and the player remains active | :y: |
| G87 | Current player has 2 Defuse cards and draws an Exploding Kitten | Exactly 1 Defuse is removed and 1 Defuse remains | :y: |
| G88 | A Defuse is used against an Exploding Kitten | Used Defuse is added to the discard pile | :y: |
| G89 | A Defuse is used against an Exploding Kitten | Current player's turn ends and advances to the next active player | :y: |

#### Skip
| Test Case | State of the System | Expected Output | Implemented? |
|-----------|---|---|--------------|
| G90 | Current player has 0 Skip cards and attempts to play Skip | Throws `IllegalStateException` | :y: |
| G91 | Current player has exactly 1 Skip card and plays it | Exactly 1 Skip is removed and added to the discard pile | :y: |
| G92 | Current player has 2 Skip cards and plays one | Exactly 1 Skip is removed and 1 Skip remains | :y: |
| G93 | Current player plays Skip | Turn ends without drawing a card | :y: |
| G94 | Current player plays Skip while the next player is active | Current player advances to the next player | :y: |
| G95 | Current player plays Skip while the next player is eliminated | Turn advances to the next active player | :y: |
| G96 | Last player in the list plays Skip | Turn order wraps around to the first active player | :y: |

#### Favor
| Test Case | State of the System | Expected Output | Implemented? |
|-----------|---|---|--------------|
| G97 | Current player plays `FAVOR` without a target player | Throws `IllegalArgumentException` | :y: |
| G98 | Current player plays untargeted `FAVOR` | `FAVOR` remains in current player's hand | :y: |
| G99 | Current player plays untargeted `FAVOR` | `FAVOR` is not added to discard pile | :y: |
| G100 | Current player plays `FAVOR` with `null` target player | Throws `IllegalArgumentException` | :y: |
| G101 | Current player plays `FAVOR` with a target player not in the game | Throws `IllegalArgumentException` | :y: |
| G102 | Current player plays `FAVOR` targeting themself | Throws `IllegalArgumentException` | :y: |
| G103 | Current player plays `FAVOR` targeting a player with no cards | Throws `IllegalStateException` | :y: |
| G104 | Current player does not have `FAVOR` | Throws `IllegalStateException` | :y: |
| G105 | Invalid targeted `FAVOR` play | `FAVOR` remains in current player's hand and is not discarded | :y: |
| G106 | Current player plays valid `FAVOR` | Removes `FAVOR` from current player's hand and adds it to discard pile | :y: |
| G107 | Current player plays valid `FAVOR` | Target player loses one card and current player receives that card | :y: |
| G108 | Target player has multiple cards | Transfers the first card from the target player's hand | :y: |
| G109 | Current player plays valid `FAVOR` | Current player does not change | :y: |

#### Trade
| Test Case | State of the System | Expected Output | Implemented? |
|-----------|---|---|--------------|
| G110 | Current player plays `TRADE` without a target player | Throws `IllegalArgumentException` | :y: |
| G111 | Current player plays untargeted `TRADE` | `TRADE` remains in current player's hand | :y: |
| G112 | Current player plays untargeted `TRADE` | `TRADE` is not added to discard pile | :y: |
| G113 | Current player plays `TRADE` with `null` target player | Throws `IllegalArgumentException` | :y: |
| G114 | Current player plays `TRADE` with a target player not in the game | Throws `IllegalArgumentException` | :y: |
| G115 | Current player plays `TRADE` targeting themself | Throws `IllegalArgumentException` | :y: |
| G116 | Current player does not have `TRADE` | Throws `IllegalStateException` | :y: |
| G117 | Current player has `TRADE` but no remaining card to swap after playing it | Throws `IllegalStateException` | :y: |
| G118 | Current player plays `TRADE` targeting a player with no cards | Throws `IllegalStateException` | :y: |
| G119 | Invalid targeted `TRADE` play | `TRADE` remains in current player's hand and is not discarded | :y: |
| G120 | Current player plays valid `TRADE` | Removes `TRADE` from current player's hand and adds it to discard pile | :y: |
| G121 | Current player plays valid `TRADE` | Current player and target player each exchange one card | :y: |
| G122 | Both players have multiple cards during valid `TRADE` | Swaps current player's first remaining card with target player's first card | :y: |
| G123 | Current player plays valid `TRADE` | Discarded `TRADE` card is not swapped | :y: |
| G124 | Current player plays valid `TRADE` | Current player does not change | :y: |
| G125 | Current player plays valid `TRADE` | Deck size does not change | :y: |
| G126 | Current player plays valid `TRADE` | No player is eliminated | :y: |

#### Mark
| Test Case | State of the System | Expected Output | Implemented? |
|-----------|---|---|--------------|
| G127 | Current player plays `MARK` with `null` target player | Throws `IllegalArgumentException` | :y: |
| G128 | Current player plays `MARK` with a target player not in the game | Throws `IllegalArgumentException` | :y: |
| G129 | Current player plays `MARK` targeting themself | Throws `IllegalArgumentException` | :y: |
| G130 | Current player does not have `MARK` | Throws `IllegalStateException` | :y: |
| G131 | Current player plays `MARK` targeting a player with no cards | Throws `IllegalStateException` | :y: |
| G132 | Invalid `MARK` play | `MARK` remains in current player's hand and is not discarded | :y: |
| G133 | Current player plays valid `MARK` | Removes `MARK` from current player's hand | :y: |
| G134 | Current player plays valid `MARK` | Adds `MARK` to the discard pile | :y: |
| G135 | Current player plays valid `MARK` | Reveals the first card from the target player's hand | :y: |
| G136 | Current player plays valid `MARK` | Revealed card remains in the target player's hand | :y: |
| G137 | Current player plays valid `MARK` | Current player does not change | :y: |
| G138 | Current player plays valid `MARK` | Deck size does not change | :y: |
| G139 | Current player plays valid `MARK` | No player is eliminated | :y: |

#### Cat Pair Combo
| Test Case | State of the System | Expected Output | Implemented? |
|-----------|---|---|--------------|
| G140 | Cat pair combo has `null` card type | Throws `IllegalArgumentException` | :y: |
| G141 | Cat pair combo card type is not a Cat Card | Throws `IllegalArgumentException` | :y: |
| G142 | Cat pair combo target player is `null` | Throws `IllegalArgumentException` | :y: |
| G143 | Cat pair combo target player is not in the game | Throws `IllegalArgumentException` | :y: |
| G144 | Cat pair combo targets the current player | Throws `IllegalArgumentException` | :y: |
| G145 | Current player has fewer than two matching Cat Cards | Throws `IllegalStateException` | :y: |
| G146 | Cat pair combo target player has no cards | Throws `IllegalStateException` | :y: |
| G147 | Invalid Cat pair combo attempt | Matching Cat Cards remain in current player's hand and are not discarded | :y: |
| G148 | Current player plays a valid Cat pair combo | Removes exactly two matching Cat Cards from current player's hand | :y: |
| G149 | Current player plays a valid Cat pair combo | Adds both played Cat Cards to the discard pile | :y: |
| G150 | Current player plays two matching `TACO_CAT` cards | Cat pair combo succeeds | :y: |
| G151 | Current player plays two matching `BEARD_CAT` cards | Cat pair combo succeeds | :y: |
| G152 | Current player plays two matching `RAINBOW_RALPHING_CAT` cards | Cat pair combo succeeds | :y: |
| G153 | Current player plays two matching `HAIRY_POTATO_CAT` cards | Cat pair combo succeeds | :y: |
| G154 | Current player plays a valid Cat pair combo | Transfers the first card from target player's hand | :y: |
| G155 | Current player plays a valid Cat pair combo | Target player loses the transferred card | :y: |
| G156 | Current player plays a valid Cat pair combo | Current player gains the transferred card | :y: |
| G157 | Current player plays a valid Cat pair combo | Current player does not change | :y: |
| G158 | Current player plays a valid Cat pair combo | Deck size does not change | :y: |
| G159 | Current player plays a valid Cat pair combo | No player is eliminated | :y: |

#### Shield
| Test Case | State of the System | Expected Output | Implemented? |
|-----------|---|---|--------------|
| G160 | Current player has 0 Shield cards and no Defuse, then draws an Exploding Kitten | Player is eliminated | :y: |
| G161 | Current player has exactly 1 Shield and no Defuse, then draws an Exploding Kitten | Exactly 1 Shield is removed and the player remains active | :y: |
| G162 | Current player has 2 Shields and no Defuse, then draws an Exploding Kitten | Exactly 1 Shield is removed and 1 Shield remains | :y: |
| G163 | A Shield is used against an Exploding Kitten | Used Shield is added to the discard pile | :y: |
| G164 | A Shield is used against an Exploding Kitten | Current player's turn ends and advances to the next active player | :y: |
| G165 | Current player has both a Defuse and a Shield when drawing an Exploding Kitten | Defuse is consumed first and Shield remains in the player's hand | :y: |

#### Cat Three Combo
| Test Case | State of the System | Expected Output | Implemented? |
|-----------|---|---|--------------|
| G166 | Cat three combo has `null` card type | Throws `IllegalArgumentException` | :y: |
| G167 | Cat three combo card type is not a Cat Card | Throws `IllegalArgumentException` | :y: |
| G168 | Cat three combo target player is `null` | Throws `IllegalArgumentException` | :y: |
| G169 | Cat three combo target player is not in the game | Throws `IllegalArgumentException` | :y: |
| G170 | Cat three combo targets the current player | Throws `IllegalArgumentException` | :y: |
| G171 | Cat three combo requested card type is `null` | Throws `IllegalArgumentException` | :y: |
| G172 | Current player has fewer than three matching Cat Cards | Throws `IllegalStateException` | :y: |
| G173 | Invalid Cat three combo attempt | Matching Cat Cards remain in current player's hand and are not discarded | :y: |
| G174 | Current player plays a valid Cat three combo | Removes exactly three matching Cat Cards from current player's hand | :y: |
| G175 | Current player plays a valid Cat three combo | Adds all three played Cat Cards to the discard pile | :y: |
| G176 | Current player plays three matching `TACO_CAT` cards | Cat three combo succeeds | :y: |
| G177 | Current player plays three matching `BEARD_CAT` cards | Cat three combo succeeds | :y: |
| G178 | Current player plays three matching `RAINBOW_RALPHING_CAT` cards | Cat three combo succeeds | :y: |
| G179 | Current player plays three matching `HAIRY_POTATO_CAT` cards | Cat three combo succeeds | :y: |
| G180 | Target player has requested card type during valid Cat three combo | Transfers one matching requested card | :y: |
| G181 | Target player has requested card type during valid Cat three combo | Target player loses the transferred requested card | :y: |
| G182 | Target player has requested card type during valid Cat three combo | Current player gains the transferred requested card | :y: |
| G183 | Target player does not have requested card type during valid Cat three combo | Combo succeeds with no card transferred | :y: |
| G184 | Current player plays a valid Cat three combo | Current player does not change | :y: |
| G185 | Current player plays a valid Cat three combo | Deck size does not change | :y: |
| G186 | Current player plays a valid Cat three combo | No player is eliminated | :y: |

#### Shuffle
| Test Case | State of the System | Expected Output | Implemented? |
|-----------|---|---|--------------|
| G187 | Current player has 0 Shuffle cards and attempts to play Shuffle | Throws `IllegalStateException` | :y: |
| G188 | Current player has exactly 1 Shuffle card and plays it | Exactly 1 Shuffle is removed from current player's hand | :y: |
| G189 | Current player has exactly 1 Shuffle card and plays it | Shuffle card is added to the discard pile | :y: |
| G190 | Current player has 2 Shuffle cards and plays one | Exactly 1 Shuffle is removed and 1 Shuffle remains | :y: |
| G191 | Current player plays Shuffle | Deck size does not change | :y: |
| G192 | Current player plays Shuffle | Card amounts in the deck do not change | :y: |
| G193 | Current player plays Shuffle | Current player does not change | :y: |
| G194 | Current player plays Shuffle | No player is eliminated | :y: |

#### See the Future
| Test Case | State of the System | Expected Output | Implemented? |
|-----------|---|---|-----|
| G195 | Current player has 0 See the Future cards and attempts to play See the Future | Throws `IllegalStateException` | :y: |
| G196 | Current player has exactly 1 See the Future card and plays it | Exactly 1 See the Future is removed from current player's hand | :y: |
| G197 | Current player has exactly 1 See the Future card and plays it | See the Future card is added to the discard pile | :y: |
| G198 | Current player has 2 See the Future cards and plays one | Exactly 1 See the Future is removed and 1 See the Future remains | :y: |
| G199 | Current player plays See the Future | Returns the top 3 cards of the deck in correct order | :y: |
| G200 | Current player plays See the Future | Does not remove any cards from the deck | :y: |
| G201 | Current player plays See the Future | Current player does not change | :y: |
| G202 | Current player plays See the Future | No player is eliminated | :n: |