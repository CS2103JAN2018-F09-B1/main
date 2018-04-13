package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.task.TaskList;

public class TaskListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        TaskList taskList = new TaskList();
        thrown.expect(UnsupportedOperationException.class);
        taskList.asObservableList().remove(0);
    }
}

