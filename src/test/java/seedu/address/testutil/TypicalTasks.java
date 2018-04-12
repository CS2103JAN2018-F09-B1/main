package seedu.address.testutil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.task.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
//@@author Wu Di
public class TypicalTasks {

    private static LocalDate now = LocalDate.now();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static String tutorialDeadline = now.plusDays(5).format(formatter);
    private static String sleepDeadline = now.plusDays(6).format(formatter);
    private static String lunchDeadline = now.plusDays(10).format(formatter);
    private static String reportDeadline = now.plusDays(8).format(formatter);
    private static String biddingDeadline = now.plusMonths(1).format(formatter);

    private static final Task TUTORIAL = new TaskBuilder().withTitle("Prepare Tut")
            .withDesc("Prepare tutorial contents for friday Tutorial")
            .withDeadline(tutorialDeadline).withPriority("1").build();
    private static final Task SLEEP = new TaskBuilder().withTitle("Sleep Early")
            .withDesc("I need to sleep early before midnight today")
            .withDeadline(sleepDeadline).withPriority("2").build();
    private static final Task LUNCH = new TaskBuilder().withTitle("Group Lunch")
            .withDesc("Have lunch with the TA group")
            .withDeadline(lunchDeadline).withPriority("3").build();
    private static final Task REPORT = new TaskBuilder().withTitle("Sem Report")
            .withDesc("Prepare for end of semester report")
            .withDeadline(reportDeadline).withPriority("2").build();
    private static final Task BIDDING = new TaskBuilder().withTitle("Bid Modules")
            .withDesc("Prepare for bidding modules for the coming semester")
            .withDeadline(biddingDeadline).withPriority("2").build();

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Task task : getTypicalTasks()) {
            ab.addTask(task);
        }
        return ab;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(TUTORIAL, SLEEP, LUNCH, REPORT, BIDDING));
    }
}
