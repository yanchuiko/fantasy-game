package org.fantasygame.model.entities.items.boxes;

import org.fantasygame.model.entities.content.items.boxes.PandoraBox;
import org.fantasygame.model.entities.content.items.potions.*;
import org.fantasygame.model.entities.core.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.mockito.Mockito.*;

class PandoraBoxTest {

    private PandoraBox pandoraBox;
    private Player player;
    private Random mockRandom;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize Random and PandoraBox
        mockRandom = mock(Random.class);
        pandoraBox = new PandoraBox(mockRandom);

        // Arrange: Initialize Player
        player = new Player(4, 1, 100);
    }

    /**
     * Verifies that a HealthPotion is generated when using the PandoraBox.
     */
    @Test
    void testUseGeneratesHealthPotion() {
        // Arrange: Mock Random to return 0 (HealthPotion)
        when(mockRandom.nextInt(5)).thenReturn(0);

        // Act: Use the PandoraBox
        pandoraBox.use(player);

        // Assert: Verify the inventory contains a HealthPotion
        assert player.getInventory().contains("potion") : "Inventory should contain a potion";
        assert isPotionOfType(player, HealthPotion.class) : "Potion should be of type HealthPotion";
    }

    /**
     * Verifies that a PoisonPotion is generated when using the PandoraBox.
     */
    @Test
    void testUseGeneratesPoisonPotion() {
        // Arrange: Mock Random to return 1 (PoisonPotion)
        when(mockRandom.nextInt(5)).thenReturn(1);

        // Act: Use the PandoraBox
        pandoraBox.use(player);

        // Assert: Verify the inventory contains a PoisonPotion
        assert player.getInventory().contains("potion") : "Inventory should contain a potion";
        assert isPotionOfType(player, PoisonPotion.class) : "Potion should be of type PoisonPotion";
    }

    /**
     * Verifies that a LuckyPotion is generated when using the PandoraBox.
     */
    @Test
    void testUseGeneratesLuckyPotion() {
        // Arrange: Mock Random to return 2 (LuckyPotion)
        when(mockRandom.nextInt(5)).thenReturn(2);

        // Act: Use the PandoraBox
        pandoraBox.use(player);

        // Assert: Verify the inventory contains a LuckyPotion
        assert player.getInventory().contains("potion") : "Inventory should contain a potion";
        assert isPotionOfType(player, LuckyPotion.class) : "Potion should be of type LuckyPotion";
    }

    /**
     * Verifies that a SleepPotion is generated when using the PandoraBox.
     */
    @Test
    void testUseGeneratesSleepPotion() {
        // Arrange: Mock Random to return 3 (SleepPotion)
        when(mockRandom.nextInt(5)).thenReturn(3);

        // Act: Use the PandoraBox
        pandoraBox.use(player);

        // Assert: Verify the inventory contains a SleepPotion
        assert player.getInventory().contains("potion") : "Inventory should contain a potion";
        assert isPotionOfType(player, SleepPotion.class) : "Potion should be of type SleepPotion";
    }

    /**
     * Verifies that an XRayPotion is generated when using the PandoraBox.
     */
    @Test
    void testUseGeneratesXRayPotion() {
        // Arrange: Mock Random to return 4 (XRayPotion)
        when(mockRandom.nextInt(5)).thenReturn(4);

        // Act: Use the PandoraBox
        pandoraBox.use(player);

        // Assert: Verify the inventory contains an XRayPotion
        assert player.getInventory().contains("potion") : "Inventory should contain a potion";
        assert isPotionOfType(player, XRayPotion.class) : "Potion should be of type XRayPotion";
    }

    /**
     * Helper method to verify the type of potion in the player's inventory.
     */
    private boolean isPotionOfType(Player player, Class<? extends Potion> potionType) {
        Potion potion = player.getInventory().getPotionInstance();
        return potionType.isInstance(potion);
    }
}
