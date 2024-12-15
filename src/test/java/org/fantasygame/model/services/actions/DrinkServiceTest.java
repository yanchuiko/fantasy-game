package org.fantasygame.model.services.actions;

import org.fantasygame.model.entities.content.items.potions.Potion;
import org.fantasygame.model.entities.core.Inventory;
import org.fantasygame.model.entities.core.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DrinkServiceTest {

    private DrinkService drinkService;
    private Player mockPlayer;
    private Inventory mockInventory;

    @BeforeEach
    void setUp() {
        // Arrange: Mock Player and Inventory
        mockPlayer = mock(Player.class);
        mockInventory = mock(Inventory.class);

        // Arrange: Link the mock inventory to the player
        when(mockPlayer.getInventory()).thenReturn(mockInventory);

        // Arrange: Initialize DrinkService with the mocked player
        drinkService = new DrinkService(mockPlayer);
    }

    /**
     * Tests that drinking a valid potion invokes its use method,
     * removes it from the inventory, and returns the expected message.
     */
    @Test
    void testDrinkValidPotion() {
        // Arrange: Prepare a valid potion
        String potion = "Health Potion";
        Potion mockPotion = mock(Potion.class);

        // Arrange: Mock potion behavior
        when(mockInventory.getPotionInstance()).thenReturn(mockPotion);
        when(mockPotion.use(mockPlayer)).thenReturn("You drank the potion");
        when(mockInventory.contains(potion)).thenReturn(true);

        // Act: Attempt to drink the potion
        String result = drinkService.drink(potion);

        // Assert: Verify potion's use method is called
        verify(mockPotion, times(1)).use(mockPlayer);

        // Assert: Verify the potion is removed from inventory
        verify(mockInventory, times(1)).removePotion(mockPotion);

        // Assert: Verify the feedback message matches the potion's effect
        assertEquals("You drank the potion", result, "Feedback message should match the potion's effect.");
    }

    /**
     * Tests that attempting to drink a potion not present in the inventory
     * throws an exception and no potion is used or removed.
     */
    @Test
    void testDrinkInvalidPotion() {
        // Arrange: Prepare a potion not present in the inventory
        String potion = "Test";

        // Arrange: Simulate the absence of the potion in the inventory
        when(mockInventory.getPotionInstance()).thenReturn(null);

        // Act: Attempt to drink the potion and handle exception
        RuntimeException exception = null;
        try {
            drinkService.drink(potion);
        } catch (RuntimeException e) {
            exception = e;
        }

        // Assert: Verify the exception is thrown
        assertNotNull(exception, "Should throw an error for invalid potion.");
        assertNotNull(exception.getMessage(), "Error message should be present");

        // Assert: Verify no potion's use method is called
        verify(mockInventory, times(1)).getPotionInstance();
        verify(mockInventory, never()).removePotion(any(Potion.class));
    }
}
