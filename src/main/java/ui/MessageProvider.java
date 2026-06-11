package ui;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageProvider {
    private static final String BUNDLE_NAME = "messages";
    private static final Locale SPANISH = Locale.forLanguageTag("es");

    private final Locale locale;
    private final ResourceBundle bundle;

    private MessageProvider(Locale locale) {
        this.locale = locale;
        this.bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
    }

    public static MessageProvider forLocale(Locale locale) {
        if (locale == null) {
            return new MessageProvider(Locale.ENGLISH);
        }

        return new MessageProvider(locale);
    }

    public static MessageProvider fromLanguageChoice(String choice) {
        if (choice == null) {
            return forLocale(Locale.ENGLISH);
        }

        String normalizedChoice = choice.trim().toLowerCase(Locale.ROOT);

        if (normalizedChoice.equals("2")
                || normalizedChoice.equals("es")
                || normalizedChoice.equals("spanish")
                || normalizedChoice.equals("español")) {
            return forLocale(SPANISH);
        }

        return forLocale(Locale.ENGLISH);
    }

    public String get(String key) {
        return bundle.getString(key);
    }

    public String format(String key, Object... arguments) {
        MessageFormat formatter = new MessageFormat(get(key), locale);
        return formatter.format(arguments);
    }
}
