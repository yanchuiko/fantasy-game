package org.fantasygame.model.entities.items.food;

import org.fantasygame.model.entities.content.items.food.Pizza;
import org.fantasygame.model.entities.core.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PizzaTest {

    private Pizza pizza;
    private Player player;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize Pizza and Player
        pizza = new Pizza();
        player = new Player(4, 1, 100);
    }

    /**
     * Tests that using the pizza increases the player's power points by 30.
     */
    @Test
    void testUseIncreasesPowerPoints() {
        // Act: Use the pizza on the player
        pizza.use(player);

        // Assert: Verify the player's power points increased by 30
        Assertions.assertEquals(130, player.getPowerPoints(), "Player's power points should increase by 30");
    }

    /**
     * Tests that using the pizza multiple times increases the player's power points
     * cumulatively by 30 for each use.
     */
    @Test
    void testUseMultipleTimes() {
        // Act: Use the pizza three times
        pizza.use(player);
        pizza.use(player);
        pizza.use(player);

        // Assert: Verify the player's power points increased by 90 after three uses
        Assertions.assertEquals(190, player.getPowerPoints(), "Player's power points should increase by 90 after three uses");
    }
}
