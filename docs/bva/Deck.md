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

- **TC3: size_emptyDeck** (:todo:)
  - **State of the system**: Deck contains 0 cards
  - **Expected output**: 0

- **TC4: size_oneCardDeck** (:todo:)
  - **State of the system**: Deck contains exactly 1 card
  - **Expected output**: 1

- **TC5: size_twoCardDeck** (:todo:)
  - **State of the system**: Deck contains 2 cards
  - **Expected output**: The number of cards currently in the deck (2)


### Method under test: `amtCardType(CardType type)`
Input State: Deck  
Input Value: CardType  
Output Value: Integer

- **TC6: amtCardType_notPresent_returnsZero** (:todo:)
  - **State of the system**: Deck contains no cards of the requested CardType
  - **Input Value**: EXPLODING_KITTEN
  - **Expected output**: 0

- **TC7: amtCardType_onePresent_returnsOne** (:todo:)
  - **State of the system**: Deck contains exactly 1 card of the requested CardType (insert an Exploding Kitten)
  - **Input Value**: EXPLODING_KITTEN
  - **Expected output**: 1

- **TC8: amtCardType_multiplePresent_returnsCount** (:todo:)
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

- **TC11: draw_fromFullCardDeck** (:todo:)
  - **State of the system**: Deck contains full deck after construction (34)
  - **Expected output**: One card is returned and `size()` returns 33


### Method under test: `insertBottom(Card card)`
Input State: Deck  
Input Value: Card  
Output Value: Deck

- **TC12: insertBottom_intoEmptyDeck** (:todo:)
  - **State of the system**: Deck contains 0 cards
  - **Input Value**: DEFUSE card
  - **Expected output**: `size()` returns 1 and `amtCardType(DEFUSE)` returns 1

- **TC13: insertBottom_intoNonEmptyDeck** (:todo:)
  - **State of the system**: Deck contains multiple cards
  - **Input Value**: EXPLODING_KITTEN card
  - **Expected output**: `size()` increases by 1 and `amtCardType(EXPLODING_KITTEN)` returns 1

- **TC14: insertBottom_duplicateCardType** (:todo:)
  - **State of the system**: Deck already contains 1 DEFUSE card
  - **Input Value**: DEFUSE card
  - **Expected output**: `amtCardType(DEFUSE)` returns 2


### Method under test: `shuffle()`
Input State: Deck  
Input Value: None  
Output Value: Deck

- **TC15: shuffle_emptyDeck** (:todo:)
  - **State of the system**: Deck contains 0 cards
  - **Expected output**: `size()` still returns 0, no exception

- **TC16: shuffle_oneCardDeck** (:todo:)
  - **State of the system**: Deck contains exactly 1 card
  - **Expected output**: `size()` still returns 1, no exception

- **TC17: shuffle_multipleCardDeck** (:todo:)
  - **State of the system**: Deck contains multiple cards
  - **Expected output**: `size()` is unchanged and card amounts are unchanged