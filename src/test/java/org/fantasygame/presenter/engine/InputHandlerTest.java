package org.fantasygame.presenter.engine;

import org.fantasygame.view.GameView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InputHandlerTest {

    /**
     * Verifies that the method correctly retrieves user input from the `GameView`.
     */
    @Test
    void testGetInput() {
        // Arrange: Mock the GameView and set up its behavior
        GameView mockGameView = mock(GameView.class);
        when(mockGameView.getUserInput()).thenReturn("test input"); // Simulate user input
        InputHandler inputHandler = new InputHandler(mockGameView);

        // Act: Retrieve the input using InputHandler
        String input = inputHandler.getInput();

        // Assert: Verify the input and interactions with GameView
        assertEquals("test input", input, "InputHandler should return the input provided by GameView.");
        verify(mockGameView, times(1)).getUserInput(); // Ensure getUserInput is called exactly once
    }
}
