package org.fantasygame.model.entities.items.food;

import org.fantasygame.model.entities.content.items.food.Soup;
import org.fantasygame.model.entities.core.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SoupTest {

    private Soup soup;
    private Player player;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize Soup and Player
        soup = new Soup();
        player = new Player(4, 1, 100);
    }

    /**
     * Tests that using the soup increases the player's power points by 20.
     */
    @Test
    void testUseIncreasesPowerPoints() {
        // Act: Use the soup on the player
        soup.use(player);

        // Assert: Verify the player's power points increased by 20
        Assertions.assertEquals(120, player.getPowerPoints(), "Player's power points should increase by 20");
    }

    /**
     * Tests that using the soup multiple times increases the player's power points
     * cumulatively by 20 for each use.
     */
    @Test
    void testUseMultipleTimes() {
        // Act: Use the soup three times
        soup.use(player);
        soup.use(player);
        soup.use(player);

        // Assert: Verify the player's power points increased by 60 after three uses
        Assertions.assertEquals(160, player.getPowerPoints(), "Player's power points should increase by 60 after three uses");
    }
}
