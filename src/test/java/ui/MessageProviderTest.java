package ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.Test;

public class MessageProviderTest {
    private static final List<String> REQUIRED_KEYS = List.of(
            "language.prompt",
            "language.english",
            "language.spanish",
            "player.count.prompt",
            "player.count.invalid.range",
            "player.count.invalid.number",
            "player.name.prompt",
            "player.default.name",
            "game.over",
            "winner",
            "current.player",
            "hand",
            "choose.action",
            "action.play.card",
            "action.cat.pair",
            "action.cat.three",
            "action.draw",
            "invalid.choice",
            "error.prefix",
            "card.index.prompt",
            "invalid.card.index",
            "revealed.card",
            "top.three.cards",
            "top.two.cards",
            "cat.card.index.prompt",
            "combo.cat.pair.played",
            "combo.cat.three.played",
            "target.choose",
            "card.type.choose",
            "peek.swap.prompt",
            "cards.swapped",
            "cards.unchanged",
            "alter.future.reordered",
            "new.order.prompt",
            "new.order.card.prompt",
            "invalid.index",
            "invalid.target.index",
            "invalid.card.type.index",
            "duplicate.card.choice",
            "valid.number.error",
            "indexed.item"
    );

    @Test
    public void englishResourceBundleLoads() {
        MessageProvider messages = MessageProvider.forLocale(Locale.ENGLISH);

        assertDoesNotThrow(() -> messages.get("language.prompt"));
    }

    @Test
    public void spanishResourceBundleLoads() {
        MessageProvider messages = MessageProvider.forLocale(Locale.forLanguageTag("es"));

        assertDoesNotThrow(() -> messages.get("language.prompt"));
    }

    @Test
    public void englishResourceBundleContainsRequiredKeys() {
        MessageProvider messages = MessageProvider.forLocale(Locale.ENGLISH);

        for (String key : REQUIRED_KEYS) {
            assertDoesNotThrow(() -> messages.get(key));
        }
    }

    @Test
    public void spanishResourceBundleContainsRequiredKeys() {
        MessageProvider messages = MessageProvider.forLocale(Locale.forLanguageTag("es"));

        for (String key : REQUIRED_KEYS) {
            assertDoesNotThrow(() -> messages.get(key));
        }
    }

    @Test
    public void spanishLocaleReturnsSpanishTextForPlayerCountPrompt() {
        MessageProvider messages = MessageProvider.forLocale(Locale.forLanguageTag("es"));

        assertEquals("¿Cuántos jugadores?", messages.get("player.count.prompt"));
    }

    @Test
    public void unsupportedLanguageChoiceFallsBackToEnglish() {
        MessageProvider messages = MessageProvider.fromLanguageChoice("fr");

        assertEquals("How many players?", messages.get("player.count.prompt"));
    }

    @Test
    public void blankLanguageChoiceFallsBackToEnglish() {
        MessageProvider messages = MessageProvider.fromLanguageChoice(" ");

        assertEquals("How many players?", messages.get("player.count.prompt"));
    }

    @Test
    public void messageWithInsertedValueUsesMessageFormat() {
        MessageProvider messages = MessageProvider.forLocale(Locale.ENGLISH);

        assertEquals("Enter name for Player 2:", messages.format("player.name.prompt", 2));
    }
}
