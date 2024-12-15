package org.fantasygame.model.entities.items.food;

import org.fantasygame.model.entities.content.items.food.Salad;
import org.fantasygame.model.entities.core.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SaladTest {

    private Salad salad;
    private Player player;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize Salad and Player
        salad = new Salad();
        player = new Player(4, 1, 100);
    }

    /**
     * Tests that using the salad increases the player's power points by 5.
     */
    @Test
    void testUseIncreasesPowerPoints() {
        // Act: Use the salad on the player
        salad.use(player);

        // Assert: Verify the player's power points increased by 5
        Assertions.assertEquals(105, player.getPowerPoints(), "Player's power points should increase by 5");
    }

    /**
     * Tests that using the salad multiple times increases the player's power points
     * cumulatively by 5 for each use.
     */
    @Test
    void testUseMultipleTimes() {
        // Act: Use the salad three times
        salad.use(player);
        salad.use(player);
        salad.use(player);

        // Assert: Verify the player's power points increased by 15 after three uses
        Assertions.assertEquals(115, player.getPowerPoints(), "Player's power points should increase by 15 after three uses");
    }
}
