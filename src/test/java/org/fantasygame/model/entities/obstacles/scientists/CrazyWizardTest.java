package org.fantasygame.model.entities.obstacles.scientists;

import org.fantasygame.model.entities.content.obstacles.scientists.CrazyWizard;
import org.fantasygame.model.entities.core.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class CrazyWizardTest {

    private CrazyWizard crazyWizard;
    private Player mockPlayer;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize CrazyWizard and mock Player
        crazyWizard = new CrazyWizard();
        mockPlayer = mock(Player.class);

        // Arrange: Set initial power points for the player
        when(mockPlayer.getPowerPoints()).thenReturn(100);
    }

    /**
     * Tests that triggering the CrazyWizard reduces the player's power points,
     * neutralizes the CrazyWizard, and returns the correct message.
     */
    @Test
    void testTriggerReducesPowerPoints() {
        // Act: Trigger the CrazyWizard
        String result = crazyWizard.trigger(mockPlayer);

        // Assert: Verify the player's power points are reduced by 50
        verify(mockPlayer).setPowerPoints(50);

        // Assert: Verify that the CrazyWizard is neutralized
        assert crazyWizard.isNeutralized() : "CrazyWizard should be neutralized after triggering.";

        // Assert: Verify the message returned by the trigger
        assert result.equals("The Crazy Wizard lectures about arcane magic, draining 50 Power Points!") :
                "Trigger should return the correct message.";
    }
}
