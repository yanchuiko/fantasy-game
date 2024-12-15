package org.fantasygame.model.entities.obstacles.traps;

import org.fantasygame.model.entities.content.obstacles.traps.NeedleTrap;
import org.fantasygame.model.entities.core.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class NeedleTrapTest {

    private NeedleTrap needleTrap;
    private Player mockPlayer;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize NeedleTrap and mock Player
        needleTrap = new NeedleTrap();
        mockPlayer = mock(Player.class);

        // Arrange: Set initial power points for the player
        when(mockPlayer.getPowerPoints()).thenReturn(100);
    }

    /**
     * Tests that triggering the NeedleTrap reduces the player's power points,
     * neutralizes the trap, and returns the correct message.
     */
    @Test
    void testTriggerReducesPowerPoints() {
        // Act: Trigger the NeedleTrap
        String result = needleTrap.trigger(mockPlayer);

        // Assert: Verify the player's power points are reduced by 20
        verify(mockPlayer).setPowerPoints(80);

        // Assert: Verify that the trap is neutralized
        assert needleTrap.isNeutralized() : "NeedleTrap should be neutralized after triggering.";

        // Assert: Verify the message returned by the trigger
        assert result.equals("You triggered a Needle Trap and lost 20 Power Points!") :
                "Trigger should return the correct message.";
    }
}
