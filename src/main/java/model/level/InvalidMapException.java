package model.level;

/**
 * Exception thrown when a map is invalid/can't be loaded.
 */
public class InvalidMapException extends Exception {

    public InvalidMapException(String message) {
        super(message);
    }
}
