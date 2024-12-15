package org.fantasygame.model.entities.items.potions;

import org.fantasygame.model.entities.content.items.potions.HealthPotion;
import org.fantasygame.model.entities.core.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HealthPotionTest {

    private HealthPotion healthPotion;
    private Player player;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize HealthPotion and Player
        healthPotion = new HealthPotion();
        player = new Player(4, 1, 100);
    }

    /**
     * Tests that using the health potion increases the player's power points by 20.
     */
    @Test
    void testUseIncreasesPowerPoints() {
        // Act: Use the health potion on the player
        healthPotion.use(player);

        // Assert: Verify the player's power points increased by 20
        Assertions.assertEquals(120, player.getPowerPoints(), "Player's power points should increase by 20");
    }

    /**
     * Tests that using the health potion multiple times increases the player's power points
     * cumulatively by 20 for each use.
     */
    @Test
    void testUseMultipleTimes() {
        // Act: Use the health potion three times
        healthPotion.use(player);
        healthPotion.use(player);
        healthPotion.use(player);

        // Assert: Verify the player's power points increased by 60 after three uses
        Assertions.assertEquals(160, player.getPowerPoints(), "Player's power points should increase by 60 after three uses");
    }
}
