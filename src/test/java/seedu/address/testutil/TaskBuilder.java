package seedu.address.testutil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import seedu.address.model.task.Deadline;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDescription;
import seedu.address.model.task.Title;

//@@author Wu Di
/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_TITLE = "Dance";
    public static final String DEFAULT_DESC = "Dance till I drop";
    public static final String DEFAULT_PRIORITY = "3";

    private static LocalDate now = LocalDate.now();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final String DEFAULT_DEADLINE = now.format(formatter);

    private Title title;
    private TaskDescription desc;
    private Deadline deadline;
    private Priority priority;

    public TaskBuilder() {
        title = new Title(DEFAULT_TITLE);
        desc = new TaskDescription(DEFAULT_DESC);
        deadline = new Deadline(DEFAULT_DEADLINE);
        priority = new Priority(DEFAULT_PRIORITY);
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}
     */
    public TaskBuilder(Task taskToCopy) {
        title = taskToCopy.getTitle();
        desc = taskToCopy.getTaskDesc();
        deadline = taskToCopy.getDeadline();
        priority = taskToCopy.getPriority();
    }

    /**
     * Sets the {@code Title} of the {@code Task} that we are building
     */
    public TaskBuilder withTitle (String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code TaskDescription} of the {@code Task} that we are building.
     */
    public TaskBuilder withDesc(String desc) {
        this.desc = new TaskDescription(desc);
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Task} that we are building.
     */
    public TaskBuilder withDeadline(String deadline) {
        this.deadline = new Deadline(deadline);
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Task} that we are building.
     */
    public TaskBuilder withPriority(String priority) {
        this.priority = new Priority(priority);
        return this;
    }

    public Task build() {
        return new Task(title, desc, deadline, priority);
    }
}
