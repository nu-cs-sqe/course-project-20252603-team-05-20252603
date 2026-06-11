# Console Game BVA

## Console Internationalization

| Test Case | State of the System | Expected Output | Implemented? |
|-----------|---------------------|-----------------|--------------|
| C1 | English locale is requested | English `messages.properties` resource bundle loads successfully | :y: |
| C2 | Spanish locale is requested | Spanish `messages_es.properties` resource bundle loads successfully | :y: |
| C3 | English resource bundle is loaded | Required console message keys exist | :y: |
| C4 | Spanish resource bundle is loaded | Required console message keys exist | :y: |
| C5 | Spanish locale is selected for `player.count.prompt` | Spanish prompt text is returned | :y: |
| C6 | Unsupported language choice is selected | Console messages fall back safely to English | :y: |
| C7 | Blank language choice is selected | Console messages fall back safely to English | :y: |
| C8 | Console game starts | User is asked to choose a language before player count | :y: |
| C9 | Message contains an inserted value, such as player number | Message is formatted through the resource bundle using the selected locale | :y: |
