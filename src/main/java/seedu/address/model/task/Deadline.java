package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Task's deadline in the address book.
 * Guarantees: immutable;
 */
public class Deadline {


    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
            "Deadline should be a valid date in the format dd-mm-yyyy. Tasks cannot be scheduled in the past."
                    + "And can only be scheduled at most 6 months in advance. (Based on months: tasks cannot be"
                    + " scheduled on 1st August 2018 if the current date is 31st January 2018).";
    public final String dateString;
    public final LocalDate value;
    public final int diff;
    public final int day;
    public final int month;
    public final int year;

    /**
     * Constructs a {@code Deadline}.
     *
     * @param deadline A valid deadline.
     */
    public Deadline(String deadline) {
        requireNonNull(deadline);
        dateString = deadline;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate deadlineDate = LocalDate.parse(deadline, formatter);
        this.value = deadlineDate;
        LocalDate now = LocalDate.now();
        this.diff = calculateDifference(deadlineDate, now);
        this.day = deadlineDate.getDayOfMonth();
        this.month = deadlineDate.getMonthValue();
        this.year = deadlineDate.getYear();
    }

    /**
     * Returns true if a given string is a valid deadline.
     */
    public static boolean isValidDeadline(String test) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate deadlineDate = LocalDate.parse(test, formatter);
            LocalDate now = LocalDate.now();
            if (deadlineDate.getYear() < now.getYear()) {
                throw new IllegalArgumentException(MESSAGE_DEADLINE_CONSTRAINTS);
            }
            if (deadlineDate.getMonthValue() < now.getMonthValue() && deadlineDate.getYear() == now.getYear()) {
                throw new IllegalArgumentException(MESSAGE_DEADLINE_CONSTRAINTS);
            }
            if (deadlineDate.getMonthValue() == now.getMonthValue()
                    && deadlineDate.getYear() == now.getYear() && deadlineDate.getDayOfMonth() < now.getDayOfMonth()) {
                throw new IllegalArgumentException(MESSAGE_DEADLINE_CONSTRAINTS);
            }
            if (!isWithinSixMonths(deadlineDate, now)) {
                throw new IllegalArgumentException(MESSAGE_DEADLINE_CONSTRAINTS);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * checks and see if the deadline is within 6 months of the current date.
     * @return
     */
    public static boolean isWithinSixMonths(LocalDate deadlineDate, LocalDate now) {
        int difference;
        if (deadlineDate.getYear() == now.getYear()) {
            difference = deadlineDate.getMonthValue() - now.getMonthValue();
        } else if (deadlineDate.getYear() - now.getYear() == 1) {
            difference = 12 - now.getMonthValue() + deadlineDate.getMonthValue();
        } else {
            difference = 100;
        }
        return difference <= 6;
    }

    /**
     * Calculates the value of the difference in months between the deadline and the current date.
     * @return
     */
    private int calculateDifference(LocalDate deadlineDate, LocalDate now) {
        int diff;
        if (deadlineDate.getYear() == now.getYear()) {
            diff = deadlineDate.getMonthValue() - now.getMonthValue();
        } else {
            diff = 12 - now.getMonthValue() + deadlineDate.getMonthValue();
        }
        return diff;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.value.equals(((Deadline) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

