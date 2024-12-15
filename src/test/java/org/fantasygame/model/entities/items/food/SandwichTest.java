package org.fantasygame.model.entities.items.food;

import org.fantasygame.model.entities.content.items.food.Sandwich;
import org.fantasygame.model.entities.core.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SandwichTest {

    private Sandwich sandwich;
    private Player player;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize Sandwich and Player
        sandwich = new Sandwich();
        player = new Player(4, 1, 100);
    }

    /**
     * Tests that using the sandwich increases the player's power points by 15.
     */
    @Test
    void testUseIncreasesPowerPoints() {
        // Act: Use the sandwich on the player
        sandwich.use(player);

        // Assert: Verify the player's power points increased by 15
        Assertions.assertEquals(115, player.getPowerPoints(), "Player's power points should increase by 15");
    }

    /**
     * Tests that using the sandwich multiple times increases the player's power points
     * cumulatively by 15 for each use.
     */
    @Test
    void testUseMultipleTimes() {
        // Act: Use the sandwich three times
        sandwich.use(player);
        sandwich.use(player);
        sandwich.use(player);

        // Assert: Verify the player's power points increased by 45 after three uses
        Assertions.assertEquals(145, player.getPowerPoints(), "Player's power points should increase by 45 after three uses");
    }
}
