package org.fantasygame.presenter.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorHandlerTest {

    /**
     * Tests retrieving the error message for a valid error code.
     * Ensures the correct error message is returned for known codes.
     */
    @Test
    void testGetErrorMessageValidCode() {
        // Arrange: Define the error code and expected message
        int errorCode = 100;
        String expectedMessage = "Invalid command";

        // Act: Retrieve the error message from ErrorHandler
        String actualMessage = ErrorHandler.getErrorMessage(errorCode);

        // Assert: Verify the returned message matches the expected message
        assertEquals(expectedMessage, actualMessage, "Error message for code 100 should match.");
    }

    /**
     * Tests retrieving the error message for an invalid error code.
     * Ensures the default error message is returned for unknown codes.
     */
    @Test
    void testGetErrorMessageInvalidCode() {
        // Arrange: Define an unknown error code and the default expected message
        int errorCode = 1000;
        String expectedMessage = "Unknown error occurred.";

        // Act: Retrieve the error message from ErrorHandler
        String actualMessage = ErrorHandler.getErrorMessage(errorCode);

        // Assert: Verify the returned message matches the default error message
        assertEquals(expectedMessage, actualMessage, "Unknown codes should return a default error message.");
    }
}
