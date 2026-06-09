# BVA Analysis for Deck

### Method under test: `Deck()`
Input State: None  
Input Value: None  
Output Value: Initialized Deck
  - 3 Attack, 4 Shuffle, 3 Skip, 4 See the Future, 4 Nope, 16 Cat Cards (34 cards)

- **TC1: constructor_initializesNonEmptyDeck** (:done:)
  - **State of the system**: No deck object exists yet
  - **Expected output**: A new deck is created and `size()` returns 34

- **TC2: constructor_containsCorrectAmountCardTypes** (:done:)
  - **State of the system**: No deck object exists yet
  - **Expected output**: A new deck is created and contains 3 Attack, 4 Shuffle, 3 Skip, 4 See the Future, 4 Nope, 16 Cat Cards, 0 Defuse, 0 Exploding Kittens


### Method under test: `size()`
Input State: Deck  
Input Value: None  
Output Value: Integer

- **TC3: size_emptyDeck** (:done:)
  - **State of the system**: Deck contains 0 cards
  - **Expected output**: 0

- **TC4: size_oneCardDeck** (:done:)
  - **State of the system**: Deck contains exactly 1 card
  - **Expected output**: 1

- **TC5: size_twoCardDeck** (:done:)
  - **State of the system**: Deck contains 2 cards
  - **Expected output**: The number of cards currently in the deck (2)


### Method under test: `amtCardType(CardType type)`
Input State: Deck  
Input Value: CardType  
Output Value: Integer

- **TC6: amtCardType_notPresent_returnsZero** (:done:)
  - **State of the system**: Deck contains no cards of the requested CardType
  - **Input Value**: EXPLODING_KITTEN
  - **Expected output**: 0

- **TC7: amtCardType_onePresent_returnsOne** (:done:)
  - **State of the system**: Deck contains exactly 1 card of the requested CardType (insert an Exploding Kitten)
  - **Input Value**: EXPLODING_KITTEN
  - **Expected output**: 1

- **TC8: amtCardType_multiplePresent_returnsCount** (:done:)
  - **State of the system**: Deck contains multiple cards of the requested CardType 
  - **Input Value**: ATTACK
  - **Expected output**: 3


### Method under test: `draw()`
Input State: Deck  
Input Value: None  
Output Value: Card or IllegalStateException  
Output State: Deck

- **TC9: draw_fromEmptyDeck** (:done:)
  - **State of the system**: Deck contains 0 cards
  - **Expected output**: IllegalStateException

- **TC10: draw_fromOneCardDeck** (:done:)
  - **State of the system**: Deck contains exactly 1 card
  - **Expected output**: One card is returned and `size()` returns 0

- **TC11: draw_fromFullCardDeck** (:done:)
  - **State of the system**: Deck contains full deck after construction (34)
  - **Expected output**: One card is returned and `size()` returns 33
  - 
- **TC19: draw_fromBottom** (:done:)
  - **State of the system**: Deck contains 2 different cards
  - **Expected output**: One card is returned (the one on the bottom) and `size()` returns 1


### Method under test: `insertBottom(Card card)`
Input State: Deck  
Input Value: Card  
Output Value: Deck

- **TC12: insertBottom_intoEmptyDeck** (:done:)
  - **State of the system**: Deck contains 0 cards
  - **Input Value**: DEFUSE card
  - **Expected output**: `size()` returns 1 and `amtCardType(DEFUSE)` returns 1

- **TC13: insertBottom_intoNonEmptyDeck** (:done:)
  - **State of the system**: Deck contains multiple cards
  - **Input Value**: EXPLODING_KITTEN card
  - **Expected output**: `size()` increases by 1 and `amtCardType(EXPLODING_KITTEN)` returns 1

- **TC14: insertBottom_duplicateCardType** (:done:)
  - **State of the system**: Deck already contains 1 DEFUSE card
  - **Input Value**: DEFUSE card
  - **Expected output**: `amtCardType(DEFUSE)` returns 2


### Method under test: `shuffle()`
Input State: Deck  
Input Value: None  
Output Value: Deck

- **TC15: shuffle_emptyDeck** (:done:)
  - **State of the system**: Deck contains 0 cards
  - **Expected output**: `size()` still returns 0, no exception

- **TC16: shuffle_oneCardDeck** (:done:)
  - **State of the system**: Deck contains exactly 1 card
  - **Expected output**: `size()` still returns 1, same card still there, no exception

- **TC17: shuffle_multipleCardDeck_KeepsSameAmtCardTypes** (:done:)
  - **State of the system**: Deck contains multiple cards
  - **Expected output**: `size()` is unchanged and card amounts are unchanged

- **TC18: shuffle_multipleCardDeck_ChangesOrder** (:done:)
  - **State of the system**: Deck contains multiple cards (2)
  - **Expected output**: `size()` is unchanged and card amounts are unchanged, and ordering of cards has changed

### Method under test: `peek(int x)`
Input State: Deck  
Input Value: Integer  
Output Value: List<Card> or IllegalArgumentException  
Output State: Deck is unchanged

- **TC20: peek_zeroCards** (:done:)
  - **State of the system**: Deck contains multiple cards
  - **Input Value**: 0
  - **Expected output**: IllegalArgumentException

- **TC21: peek_negativeCards** (:done:)
  - **State of the system**: Deck contains multiple cards
  - **Input Value**: -1
  - **Expected output**: IllegalArgumentException

- **TC22: peek_oneCard_checksOrder** (:done:)
  - **State of the system**: Deck contains multiple cards with a known top card
  - **Input Value**: 1
  - **Expected output**: Returns the top 1 card in correct order and `size()` is unchanged

- **TC23: peek_twoCards_checksOrder** (:done:)
  - **State of the system**: Deck contains at least 2 cards in known order
  - **Input Value**: 2
  - **Expected output**: Returns the top 2 cards in correct order and `size()` is unchanged

- **TC24: peek_threeCards_checksOrder** (:done:)
  - **State of the system**: Deck contains at least 3 cards in known order
  - **Input Value**: 3
  - **Expected output**: Returns the top 3 cards in correct order and `size()` is unchanged

- **TC25: peek_DeckSize_Duplicates** (:done:)
  - **State of the system**: Deck contains more than 3 cards in known order
  - **Input Value**: 3
  - **Expected output**: Returns all 3 cards and `size()` is unchanged

- **TC26: peek_moreThanDeckSize** (:done:)
  - **State of the system**: Deck contains exactly 2 cards
  - **Input Value**: 3
  - **Expected output**: IllegalArgumentException