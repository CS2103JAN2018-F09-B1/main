package seedu.address.model.person.exceptions;

/**
 * Signals that the operation uses illegal mark values for Participation
 */
//@@author Alaru
public class IllegalMarksException extends IllegalArgumentException {
    public IllegalMarksException() {
        super("Mark values are not allowed");
    }
}
