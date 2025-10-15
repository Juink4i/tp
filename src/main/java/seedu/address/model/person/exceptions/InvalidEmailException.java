package seedu.address.model.person.exceptions;

/**
 * Signals that the operation input email is invalid.
 */
public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super("Invalid input Email");
    }
}
