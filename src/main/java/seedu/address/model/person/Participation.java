package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.person.exceptions.IllegalMarksException;

/**
 * Represents a Person's tutorial participation in the address book.
 */
//@@author Alaru
public class Participation {

    public static final String MESSAGE_PARTICIPATION_CONSTRAINTS = "Participation marks cannot be negative or over 100!";
    public static final String UI_DISPLAY_HEADER = "Participation marks: ";

    public final Integer threshold;
    private Integer value;

    /**
     * Constructs a {@code Participation}.
     */
    public Participation() {
        this.value = 0;
        threshold = 50;
    }

    public Participation(String value) {
        requireNonNull(value);
        checkArgument(isValidParticipation(value), MESSAGE_PARTICIPATION_CONSTRAINTS);
        this.value = Integer.parseInt(value);
        threshold = 50;
    }

    public Participation(Integer value) {
        requireNonNull(value);
        checkArgument(isValidParticipation(Integer.toString(value)), MESSAGE_PARTICIPATION_CONSTRAINTS);
        this.value = value;
        threshold = 50;
    }

    public Integer getMarks() {
        return value;
    }

    public boolean overThreshold() {
        return (value >= threshold);
    }

    public static boolean isValidParticipation(String value) {
        requireNonNull(value);
        try {
            return Integer.parseInt(value) <= 100 && Integer.parseInt(value) > -1;
        } catch (NumberFormatException nfe) {
            throw new IllegalMarksException();
        }
    }

    public String toDisplay() {
        return UI_DISPLAY_HEADER + Integer.toString(value);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Participation // instanceof handles nulls
                && this.value.equals(((Participation) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
