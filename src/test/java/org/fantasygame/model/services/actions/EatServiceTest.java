package org.fantasygame.model.services.actions;

import org.fantasygame.model.entities.content.items.Item;
import org.fantasygame.model.entities.content.items.food.Food;
import org.fantasygame.model.entities.core.Inventory;
import org.fantasygame.model.entities.core.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EatServiceTest {

    private EatService eatService;
    private Player mockPlayer;
    private Inventory mockInventory;

    @BeforeEach
    void setUp() {
        // Arrange: Mock Player and Inventory
        mockPlayer = mock(Player.class);
        mockInventory = mock(Inventory.class);

        // Arrange: Link the mock inventory to the player
        when(mockPlayer.getInventory()).thenReturn(mockInventory);

        // Arrange: Initialize EatService with the mocked player
        eatService = new EatService(mockPlayer);
    }

    /**
     * Tests that eating valid food removes it from the player's inventory,
     * invokes its use method, and returns the correct feedback message.
     */
    @Test
    void testEatValidFood() {
        // Arrange: Prepare a valid food item
        String food = "Cake";
        Food mockFood = mock(Food.class);

        // Arrange: Mock food behavior
        when(mockFood.getName()).thenReturn(food);
        when(mockFood.getPowerPoints()).thenReturn(10);

        // Arrange: Mock inventory behavior
        when(mockInventory.getItem(food)).thenReturn(mockFood);

        // Act: Attempt to eat the food
        String result = eatService.eat(food);

        // Assert: Verify food's use method is called
        verify(mockFood, times(1)).use(mockPlayer);

        // Assert: Verify food is removed from inventory
        verify(mockInventory, times(1)).removeItem(food);

        // Assert: Verify the feedback message is correct
        assertEquals("You ate Cake and gained 10 Power Points!", result, "Feedback message should be correct.");
    }

    /**
     * Tests that attempting to eat a non-food item throws an exception,
     * does not invoke its use method, and does not remove it from the inventory.
     */
    @Test
    void testEatInvalidItem() {
        // Arrange: Prepare an invalid item
        String invalidItem = "Test";
        Item mockItem = mock(Item.class);

        // Arrange: Mock inventory behavior for non-food item
        when(mockInventory.getItem(invalidItem)).thenReturn(mockItem);

        // Act: Attempt to eat a non-food item
        boolean exceptionThrown = false;
        try {
            eatService.eat(invalidItem);
        } catch (RuntimeException e) {
            exceptionThrown = true; // Mark that the exception was thrown
        }

        // Assert: Verify the exception was thrown
        assertTrue(exceptionThrown, "Should throw RuntimeException for non-food item.");

        // Assert: Verify the item's use method is not called
        verify(mockItem, never()).use(any());

        // Assert: Verify the item is not removed from the inventory
        verify(mockInventory, never()).removeItem(invalidItem);
    }

}
