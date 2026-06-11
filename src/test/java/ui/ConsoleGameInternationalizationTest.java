package ui;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class ConsoleGameInternationalizationTest {
    @Test
    public void consoleGameAsksForLanguageBeforePlayerCount() {
        ByteArrayInputStream input = new ByteArrayInputStream(
                System.lineSeparator().getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleGame consoleGame = new ConsoleGame(
                input,
                new PrintStream(output, true, StandardCharsets.UTF_8),
                new Random(0));

        assertThrows(NoSuchElementException.class, consoleGame::run);

        String consoleOutput = output.toString(StandardCharsets.UTF_8);
        int languagePromptIndex = consoleOutput.indexOf("Choose language");
        int playerCountPromptIndex = consoleOutput.indexOf("How many players?");

        assertTrue(languagePromptIndex >= 0);
        assertTrue(playerCountPromptIndex > languagePromptIndex);
    }
}
