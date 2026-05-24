# BVA Analysis for Card

### Method under test: `Card(CardType type)`
- **TC1: constructorAcceptsFirstCardType** ( :white_check_mark: )
  - **State of the system**: a new `Card` is created with the first enum value, `CardType.EXPLODING_KITTEN`
  - **Expected output**: a `Card` object is created successfully

- **TC2: constructorAcceptsMiddleCardType** ( :white_check_mark: )
  - **State of the system**: a new `Card` is created with a middle enum value, `CardType.DEFUSE`
  - **Expected output**: a `Card` object is created successfully

- **TC3: constructorAcceptsLastCardType** ( :white_check_mark: )
  - **State of the system**: a new `Card` is created with the last enum value, `CardType.NOPE`
  - **Expected output**: a `Card` object is created successfully

- **TC4: constructorRejectsNullCardType** ( :white_check_mark: )
  - **State of the system**: a new `Card` is created with `null` instead of a `CardType`
  - **Expected output**: throws `NullPointerException`

### Method under test: `getType()`
- **TC5: getTypeReturnsFirstCardType** ( :white_check_mark: )
  - **State of the system**: card was created with the first enum value, `CardType.EXPLODING_KITTEN`
  - **Expected output**: returns `CardType.EXPLODING_KITTEN`

- **TC6: getTypeReturnsMiddleCardType** ( :white_check_mark: )
  - **State of the system**: card was created with a middle enum value, `CardType.DEFUSE`
  - **Expected output**: returns `CardType.DEFUSE`

- **TC7: getTypeReturnsLastCardType** ( :white_check_mark: )
  - **State of the system**: card was created with the last enum value, `CardType.NOPE`
  - **Expected output**: returns `CardType.NOPE`
