package org.fantasygame.model.entities.items.food;

import org.fantasygame.model.entities.content.items.food.Cake;
import org.fantasygame.model.entities.core.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CakeTest {

    private Cake cake;
    private Player player;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize Cake and Player
        cake = new Cake();
        player = new Player(4, 1, 100);
    }

    /**
     * Tests that using the cake increases the player's power points by 10.
     */
    @Test
    void testUseIncreasesPowerPoints() {
        // Act: Use the cake on the player
        cake.use(player);

        // Assert: Verify the player's power points increased by 10
        Assertions.assertEquals(110, player.getPowerPoints(), "Player's power points should increase by 10");
    }

    /**
     * Tests that using the cake multiple times increases the player's power points
     * cumulatively by 10 for each use.
     */
    @Test
    void testUseMultipleTimes() {
        // Act: Use the cake three times
        cake.use(player);
        cake.use(player);
        cake.use(player);

        // Assert: Verify the player's power points increased by 30 after three uses
        Assertions.assertEquals(130, player.getPowerPoints(), "Player's power points should increase by 30 after three uses");
    }
}
