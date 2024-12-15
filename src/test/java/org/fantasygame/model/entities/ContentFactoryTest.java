package org.fantasygame.model.entities;

import org.fantasygame.model.entities.content.ContentFactory;
import org.fantasygame.model.entities.content.obstacles.Obstacle;
import org.fantasygame.model.entities.content.items.Item;
import org.junit.jupiter.api.Test;

import java.util.List;

class ContentFactoryTest {

    /**
     * Tests that the content generated for Level 1 is valid, containing at most one obstacle,
     * and that all elements are either Items or Obstacles.
     */
    @Test
    void testGenerateRoomContentLevel1() {
        // Arrange: Create a ContentFactory instance
        ContentFactory factory = new ContentFactory();

        // Act: Generate room content for Level 1
        List<Object> content = factory.generateRoomContent("Level 1");

        // Assert: Verify the content is not null
        assert content != null : "Content should not be null";

        // Assert: Verify there is at most one obstacle
        int obstacleCount = 0;
        for (Object obj : content) {
            if (obj instanceof Obstacle) {
                obstacleCount++;
            }
        }
        assert obstacleCount <= 1 : "There should be at most one obstacle in the room";

        // Assert: Verify all content is either an Item or an Obstacle
        for (Object obj : content) {
            assert obj instanceof Item || obj instanceof Obstacle :
                    "Content should be either an Item or an Obstacle";
        }
    }

    /**
     * Tests that the content generated for Level 2 is valid, containing at most one obstacle,
     * and that all elements are either Items or Obstacles.
     */
    @Test
    void testGenerateRoomContentLevel2() {
        // Arrange: Create a ContentFactory instance
        ContentFactory factory = new ContentFactory();

        // Act: Generate room content for Level 2
        List<Object> content = factory.generateRoomContent("Level 2");

        // Assert: Verify the content is not null
        assert content != null : "Content should not be null";

        // Assert: Verify there is at most one obstacle
        int obstacleCount = 0;
        for (Object obj : content) {
            if (obj instanceof Obstacle) {
                obstacleCount++;
            }
        }
        assert obstacleCount <= 1 : "There should be at most one obstacle in the room";

        // Assert: Verify all content is either an Item or an Obstacle
        for (Object obj : content) {
            assert obj instanceof Item || obj instanceof Obstacle :
                    "Content should be either an Item or an Obstacle";
        }
    }

    /**
     * Tests that the content generated for Level 3 is valid, containing at most one obstacle,
     * and that all elements are either Items or Obstacles.
     */
    @Test
    void testGenerateRoomContentLevel3() {
        // Arrange: Create a ContentFactory instance
        ContentFactory factory = new ContentFactory();

        // Act: Generate room content for Level 3
        List<Object> content = factory.generateRoomContent("Level 3");

        // Assert: Verify the content is not null
        assert content != null : "Content should not be null";

        // Assert: Verify there is at most one obstacle
        int obstacleCount = 0;
        for (Object obj : content) {
            if (obj instanceof Obstacle) {
                obstacleCount++;
            }
        }
        assert obstacleCount <= 1 : "There should be at most one obstacle in the room";

        // Assert: Verify all content is either an Item or an Obstacle
        for (Object obj : content) {
            assert obj instanceof Item || obj instanceof Obstacle :
                    "Content should be either an Item or an Obstacle";
        }
    }
}
