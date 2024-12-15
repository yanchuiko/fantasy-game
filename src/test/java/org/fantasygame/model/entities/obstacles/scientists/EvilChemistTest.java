package org.fantasygame.model.entities.obstacles.scientists;

import org.fantasygame.model.entities.content.obstacles.scientists.EvilChemist;
import org.fantasygame.model.entities.core.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class EvilChemistTest {

    private EvilChemist evilChemist;
    private Player mockPlayer;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize EvilChemist and mock Player
        evilChemist = new EvilChemist();
        mockPlayer = mock(Player.class);

        // Arrange: Set initial power points for the player
        when(mockPlayer.getPowerPoints()).thenReturn(100);
    }

    /**
     * Tests that triggering the EvilChemist reduces the player's power points,
     * neutralizes the EvilChemist, and returns the correct message.
     */
    @Test
    void testTriggerReducesPowerPoints() {
        // Act: Trigger the EvilChemist
        String result = evilChemist.trigger(mockPlayer);

        // Assert: Verify the player's power points are reduced by 40
        verify(mockPlayer).setPowerPoints(60);

        // Assert: Verify that the EvilChemist is neutralized
        assert evilChemist.isNeutralized() : "EvilChemist should be neutralized after triggering.";

        // Assert: Verify the message returned by the trigger
        assert result.equals("The Evil Chemist tested his new potion on you, draining 40 Power Points!") :
                "Trigger should return the correct message.";
    }
}
