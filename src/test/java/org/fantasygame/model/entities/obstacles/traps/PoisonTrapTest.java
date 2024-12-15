package org.fantasygame.model.entities.obstacles.traps;

import org.fantasygame.model.entities.content.obstacles.traps.PoisonTrap;
import org.fantasygame.model.entities.core.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PoisonTrapTest {

    private PoisonTrap poisonTrap;
    private Player mockPlayer;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize PoisonTrap and mock Player
        poisonTrap = new PoisonTrap();
        mockPlayer = mock(Player.class);

        // Arrange: Set initial power points for the player
        when(mockPlayer.getPowerPoints()).thenReturn(100);
    }

    /**
     * Tests that triggering the PoisonTrap reduces the player's power points,
     * neutralizes the trap, and returns the correct message.
     */
    @Test
    void testTriggerReducesPowerPoints() {
        // Act: Trigger the PoisonTrap
        String result = poisonTrap.trigger(mockPlayer);

        // Assert: Verify the player's power points are reduced by 30
        verify(mockPlayer).setPowerPoints(70);

        // Assert: Verify that the trap is neutralized
        assert poisonTrap.isNeutralized() : "PoisonTrap should be neutralized after triggering.";

        // Assert: Verify the message returned by the trigger
        assert result.equals("You triggered a Poison Trap and lost 30 Power Points!") :
                "Trigger should return the correct message.";
    }
}
