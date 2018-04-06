package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    private final ObservableList<Task>[][] calendarList = new ObservableList[7][32];

    private Date dateNow = new Date();

    private LocalDate now = dateNow.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    private int monthNow = now.getMonthValue();

    public UniqueTaskList() {
        super();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 32; j++) {
                calendarList[i][j] = FXCollections.observableArrayList();
            }
        }
    }
    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(Task toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     */
    public void add(Task toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
        int diff = toAdd.getDeadline().diff;
        calendarList[diff][toAdd.getDeadlineDay()].add(toAdd);
        Collections.sort(calendarList[diff][toAdd.getDeadlineDay()]);
    }

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     *
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    public void setTask(Task target, Task editedTask)
            throws TaskNotFoundException {
        requireNonNull(editedTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }
        internalList.set(index, editedTask);
        int indexCalendar = calendarList[target.getDeadline().diff][target.getDeadlineDay()].indexOf(target);
        if (indexCalendar == -1) {
            throw new TaskNotFoundException();
        }
        calendarList[target.getDeadline().diff][target.getDeadlineDay()].set(indexCalendar, editedTask);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(Task toRemove) throws TaskNotFoundException {
        requireNonNull(toRemove);
        final boolean personFoundAndDeleted = internalList.remove(toRemove)
            && calendarList[toRemove.getDeadline().diff][toRemove.getDeadlineDay()].remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return personFoundAndDeleted;
    }

    public void setTasks(UniqueTaskList replacement) {
        this.internalList.setAll(replacement.internalList);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 32; j++) {
                calendarList[i][j].setAll(replacement.calendarList[i][j]);
            }
        }
    }

    public void setTasks(List<Task> tasks) {
        requireAllNonNull(tasks);
        final UniqueTaskList replacement = new UniqueTaskList();
        for (final Task task : tasks) {
            replacement.add(task);
        }
        setTasks(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    /**
     * Returns the calendarList array for tasks
     * @return
     */
    public ObservableList<Task>[][] asCalendarList() {
        return calendarList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}

