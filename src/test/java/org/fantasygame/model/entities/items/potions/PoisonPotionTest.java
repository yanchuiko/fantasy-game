package org.fantasygame.model.entities.items.potions;

import org.fantasygame.model.entities.content.items.potions.PoisonPotion;
import org.fantasygame.model.entities.core.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PoisonPotionTest {

    private PoisonPotion poisonPotion;
    private Player player;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize PoisonPotion and Player
        poisonPotion = new PoisonPotion();
        player = new Player(4, 1, 100);
    }

    /**
     * Tests that using the poison potion decreases the player's power points by 20.
     */
    @Test
    void testUseDecreasesPowerPoints() {
        // Act: Use the poison potion on the player
        poisonPotion.use(player);

        // Assert: Verify the player's power points decreased by 20
        Assertions.assertEquals(80, player.getPowerPoints(), "Player's power points should decrease by 20");
    }

    /**
     * Tests that using the poison potion multiple times decreases the player's power points
     * cumulatively by 20 for each use.
     */
    @Test
    void testUseMultipleTimes() {
        // Act: Use the poison potion three times
        poisonPotion.use(player);
        poisonPotion.use(player);
        poisonPotion.use(player);

        // Assert: Verify the player's power points decreased by 60 after three uses
        Assertions.assertEquals(40, player.getPowerPoints(), "Player's power points should decrease by 60 after three uses");
    }
}
