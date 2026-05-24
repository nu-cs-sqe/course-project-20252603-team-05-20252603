package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    public void constructor_oneCharacterName_createsPlayer() {
        Player player = new Player("A");

        assertEquals("A", player.getName());
        assertTrue(player.getHand().isEmpty());
    }

    @Test
    public void constructor_normalName_createsPlayer() {
        Player player = new Player("Anthony");

        assertEquals("Anthony", player.getName());
        assertTrue(player.getHand().isEmpty());
    }

    @Test
    public void getHand_newPlayer_returnsEmptyHand() {
        Player player = new Player("Anthony");

        assertTrue(player.getHand().isEmpty());
    }

    @Test
    public void getName_oneCharacterName_returnsName() {
        Player player = new Player("A");

        assertEquals("A", player.getName());
    }

    @Test
    public void getName_normalName_returnsName() {
        Player player = new Player("Anthony");

        assertEquals("Anthony", player.getName());
    }

}
