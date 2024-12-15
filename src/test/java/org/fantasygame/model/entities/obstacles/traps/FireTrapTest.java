package org.fantasygame.model.entities.obstacles.traps;

import org.fantasygame.model.entities.content.obstacles.traps.FireTrap;
import org.fantasygame.model.entities.core.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class FireTrapTest {

    private FireTrap fireTrap;
    private Player mockPlayer;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize FireTrap and mock Player
        fireTrap = new FireTrap();
        mockPlayer = mock(Player.class);

        // Arrange: Set initial power points for the player
        when(mockPlayer.getPowerPoints()).thenReturn(100);
    }

    /**
     * Tests that triggering the FireTrap reduces the player's power points,
     * neutralizes the trap, and returns the correct message.
     */
    @Test
    void testTriggerReducesPowerPoints() {
        // Act: Trigger the FireTrap
        String result = fireTrap.trigger(mockPlayer);

        // Assert: Verify the player's power points are reduced by 25
        verify(mockPlayer).setPowerPoints(75);

        // Assert: Verify that the trap is neutralized
        assert fireTrap.isNeutralized() : "FireTrap should be neutralized after triggering.";

        // Assert: Verify the message returned by the trigger
        assert result.equals("You triggered a Fire Trap and lost 25 Power Points!") :
                "Trigger should return the correct message.";
    }
}
