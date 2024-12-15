package org.fantasygame.model.entities.items.boxes;

import org.fantasygame.presenter.engine.ErrorHandler;
import org.fantasygame.model.entities.content.items.boxes.EnchantedBox;
import org.fantasygame.model.entities.content.items.spells.FreezeSpell;
import org.fantasygame.model.entities.content.items.spells.TeleportationSpell;
import org.fantasygame.model.entities.core.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Random;

import static org.mockito.Mockito.*;

class EnchantedBoxTest {

    private EnchantedBox enchantedBox;
    private Player player;
    private Random mockRandom;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize EnchantedBox with a mocked Random
        mockRandom = mock(Random.class);
        enchantedBox = new EnchantedBox(mockRandom);
        player = new Player(4, 1, 100);
    }

    /**
     * Tests that using the EnchantedBox adds a Freeze Spell to the player's inventory
     * when the random generator selects the Freeze Spell.
     */
    @Test
    void testUseAddsFreezeSpell() {
        // Arrange: Mock Random to generate FreezeSpell
        when(mockRandom.nextInt(2)).thenReturn(0);

        // Act: Use the Enchanted Box
        enchantedBox.use(player);

        // Assert: Verify Freeze Spell is in the inventory
        boolean containsFreezeSpell = player.getInventory().contains("Freeze Spell");
        assert containsFreezeSpell : "Player's inventory should contain a Freeze Spell";
    }

    /**
     * Tests that using the EnchantedBox adds a Teleportation Spell to the player's inventory
     * when the random generator selects the Teleportation Spell.
     */
    @Test
    void testUseAddsTeleportationSpell() {
        // Arrange: Mock Random to generate TeleportationSpell
        when(mockRandom.nextInt(2)).thenReturn(1);

        // Act: Use the Enchanted Box
        enchantedBox.use(player);

        // Assert: Verify Teleportation Spell is in the inventory
        boolean containsTeleportationSpell = player.getInventory().contains("Teleportation Spell");
        assert containsTeleportationSpell : "Player's inventory should contain a Teleportation Spell";
    }

    /**
     * Tests that using the EnchantedBox triggers an error when the player's inventory
     * reaches the spell limit, ensuring no additional spells can be added.
     */
    @Test
    void testUseSpellLimit() {
        // Arrange: Mock Random to always generate FreezeSpell
        when(mockRandom.nextInt(2)).thenReturn(0);

        // Arrange: Fill the player's inventory to the spell limit
        player.getInventory().addItem(new FreezeSpell());
        player.getInventory().addItem(new TeleportationSpell());

        // Act & Assert: Mock static ErrorHandler and verify throwError(300) is called
        try (MockedStatic<ErrorHandler> mockedErrorHandler = mockStatic(ErrorHandler.class)) {
            // Act: Use the Enchanted Box
            enchantedBox.use(player);

            // Assert: Verify that ErrorHandler.throwError(300) is called
            mockedErrorHandler.verify(() -> ErrorHandler.throwError(300));
        }
    }
}
