package seedu.address.logic.commands.exceptions;

/**
 * Represents an error which occurs during execution of a Desktop operation.
 */
//@@author Alaru
public class UnsupportDesktopException extends CommandException {
    public UnsupportDesktopException(String message) {
        super(message);
    }
}
