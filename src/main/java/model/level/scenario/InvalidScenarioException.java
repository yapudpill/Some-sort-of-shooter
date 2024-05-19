package model.level.scenario;

/**
 * Exception thrown when a scenario is invalid/can't be loaded.
 */
public class InvalidScenarioException extends Exception {
    public InvalidScenarioException(String message) {
        super(message);
    }
}
