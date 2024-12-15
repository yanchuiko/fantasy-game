package org.fantasygame.model.entities.items.tools;

import org.fantasygame.model.entities.content.items.tools.AlarmClock;
import org.fantasygame.model.entities.core.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlarmClockTest {

    private AlarmClock alarmClock;
    private Player player;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize AlarmClock and Player, setting the player as asleep
        alarmClock = new AlarmClock();
        player = new Player(0, 0, 100);
        player.setAsleep(true);
    }

    /**
     * Tests that using the Alarm Clock awakens the player.
     */
    @Test
    void testUseAwakesPlayer() {
        // Act: Use the Alarm Clock on the player
        alarmClock.use(player);

        // Assert: Verify that the player is no longer asleep
        Assertions.assertFalse(player.isAsleep(), "Player should be awake after using the Alarm Clock");
    }
}
