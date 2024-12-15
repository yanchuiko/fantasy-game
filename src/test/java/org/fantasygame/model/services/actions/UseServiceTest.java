package org.fantasygame.model.services.actions;

import org.fantasygame.model.entities.content.items.Item;
import org.fantasygame.model.entities.content.items.spells.Spell;
import org.fantasygame.model.entities.core.Inventory;
import org.fantasygame.model.entities.core.Player;
import org.fantasygame.model.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UseServiceTest {

    private UseService useService;
    private Player mockPlayer;
    private Inventory mockInventory;
    private RoomService mockRoomService;

    @BeforeEach
    void setUp() {
        // Arrange: Mock Player, Inventory, and RoomService
        mockPlayer = mock(Player.class);
        mockInventory = mock(Inventory.class);
        mockRoomService = mock(RoomService.class);

        // Arrange: Link the mock inventory to the player
        when(mockPlayer.getInventory()).thenReturn(mockInventory);

        // Arrange: Initialize UseService with mocked dependencies
        useService = new UseService(mockPlayer, mockRoomService);
    }

    /**
     * Tests that using a valid spell calls its use method, removes it from the inventory,
     * and returns the correct feedback message.
     */
    @Test
    void testUseValidSpell() {
        // Arrange: Prepare a valid spell
        String spell = "Teleportation Spell";
        Spell mockSpell = mock(Spell.class);

        // Arrange: Mock spell behavior
        when(mockInventory.getItem(spell)).thenReturn(mockSpell);
        when(mockSpell.getName()).thenReturn(spell);

        // Act: Attempt to use the spell
        String result = useService.use(spell);

        // Assert: Verify interactions and feedback
        verify(mockSpell, times(1)).use(mockPlayer); // Ensure the spell's use method is called
        verify(mockInventory, times(1)).removeItem(spell); // Ensure the spell is removed from inventory
        assertEquals("You used the Teleportation Spell!", result, "Feedback message should match the spell usage.");
    }

    /**
     * Tests that attempting to use a non-spell item throws an exception,
     * does not invoke its use method, and does not remove it from the inventory.
     */
    @Test
    void testUseInvalidItem() {
        // Arrange: Prepare a non-spell item
        String invalidItem = "Spanner";
        Item mockItem = mock(Item.class);

        // Arrange: Mock inventory behavior
        when(mockInventory.getItem(invalidItem)).thenReturn(mockItem);

        // Act: Attempt to use the item and catch the exception
        RuntimeException exception = null;
        try {
            useService.use(invalidItem);
        } catch (RuntimeException e) {
            exception = e;
        }

        // Assert: Verify exception and interactions
        assertNotNull(exception, "Should throw an error for invalid item usage.");
        assertNotNull(exception.getMessage(), "Error message should be provided.");
        verify(mockItem, never()).use(mockPlayer);
        verify(mockInventory, never()).removeItem(invalidItem);
    }
}
