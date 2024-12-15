package org.fantasygame.model.entities.items.potions;

import org.fantasygame.model.entities.content.items.potions.SleepPotion;
import org.fantasygame.model.entities.content.items.tools.AlarmClock;
import org.fantasygame.model.entities.core.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SleepPotionTest {

    private SleepPotion sleepPotion;
    private Player player;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize SleepPotion and Player
        sleepPotion = new SleepPotion();
        player = new Player(4, 1, 100);
    }

    /**
     * Tests that using the sleep potion does not affect the player if they have an Alarm Clock.
     */
    @Test
    void testUseWithAlarmClock() {
        // Arrange: Add an Alarm Clock to the player's inventory
        AlarmClock alarmClock = new AlarmClock();
        Assertions.assertTrue(player.getInventory().addItem(alarmClock), "Alarm Clock should be added to the inventory");

        // Act: Use the Sleep Potion
        sleepPotion.use(player);

        // Assert: Verify the player does not fall asleep and power points remain unchanged
        Assertions.assertFalse(player.isAsleep(), "Player should not be asleep with an Alarm Clock");
        Assertions.assertEquals(100, player.getPowerPoints(), "Player's power points should not decrease with an Alarm Clock");
    }

    /**
     * Tests that using the sleep potion affects the player if they do not have an Alarm Clock.
     */
    @Test
    void testUseWithoutAlarmClock() {
        // Arrange: Ensure the player does not have an Alarm Clock
        Assertions.assertFalse(player.getInventory().contains("Alarm Clock"), "Player should not have an Alarm Clock");

        // Act: Use the Sleep Potion
        sleepPotion.use(player);

        // Assert: Verify the player falls asleep and power points decrease by 30
        Assertions.assertTrue(player.isAsleep(), "Player should be asleep without an Alarm Clock");
        Assertions.assertEquals(70, player.getPowerPoints(), "Player's power points should decrease by 30 without an Alarm Clock");
    }
}
