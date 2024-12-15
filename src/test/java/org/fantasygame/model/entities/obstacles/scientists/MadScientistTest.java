package org.fantasygame.model.entities.obstacles.scientists;

import org.fantasygame.model.entities.content.obstacles.scientists.MadScientist;
import org.fantasygame.model.entities.core.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class MadScientistTest {

    private MadScientist madScientist;
    private Player mockPlayer;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize MadScientist and mock Player
        madScientist = new MadScientist();
        mockPlayer = mock(Player.class);

        // Arrange: Set initial power points for the player
        when(mockPlayer.getPowerPoints()).thenReturn(100);
    }

    /**
     * Tests that triggering the MadScientist reduces the player's power points,
     * neutralizes the MadScientist, and returns the correct message.
     */
    @Test
    void testTriggerReducesPowerPoints() {
        // Act: Trigger the MadScientist
        String result = madScientist.trigger(mockPlayer);

        // Assert: Verify the player's power points are reduced by 30
        verify(mockPlayer).setPowerPoints(70);

        // Assert: Verify that the MadScientist is neutralized
        assert madScientist.isNeutralized() : "MadScientist should be neutralized after triggering.";

        // Assert: Verify the message returned by the trigger
        assert result.equals("The Mad Scientist demands money for research, costing you 30 Power Points!") :
                "Trigger should return the correct message.";
    }
}
