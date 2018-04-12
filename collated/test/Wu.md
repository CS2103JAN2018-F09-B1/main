# Wu
###### \java\seedu\address\logic\commands\AddTaskCommandTest.java
``` java
public class AddTaskCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTask_throwNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddTaskCommand(null);
    }

    @Test
    public void execute_taskAcceptedByModel_addTaskSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();

        CommandResult commandResult = getAddTaskCommandForTask(validTask, modelStub).execute();

        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void equals() {
        Task meeting = new TaskBuilder().withTitle("Meeting").build();
        Task assignment = new TaskBuilder().withTitle("Assignment").build();
        AddTaskCommand addMeetingCommand = new AddTaskCommand(meeting);
        AddTaskCommand addAssignmentCommand = new AddTaskCommand(assignment);

        // same object -> returns true
        assertTrue(addMeetingCommand.equals(addMeetingCommand));

        // same values -> returns true
        AddTaskCommand addMeetingCommandCopy = new AddTaskCommand(meeting);
        assertTrue(addMeetingCommand.equals(addMeetingCommandCopy));

        // different types -> returns false
        assertFalse(addMeetingCommand.equals(1));

        // null -> returns false
        assertFalse(addMeetingCommand.equals(null));

        // different person -> returns false
        assertFalse(addMeetingCommand.equals(addAssignmentCommand));
    }

    private AddTaskCommand getAddTaskCommandForTask(Task task, Model model) {
        AddTaskCommand command = new AddTaskCommand(task);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void addTask(Task task) {
        }

        @Override
        public void deleteTask(Task target) throws TaskNotFoundException {
        }

        @Override
        public void updateTask(Task target, Task editedTask) throws TaskNotFoundException {
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(Person target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void sortPersons() {
            fail("This method should not be called");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            return null;
        }

        @Override
        public ObservableList<Task>[][] getCalendarTaskLists() {
            return new ObservableList[0][];
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {

        }

        @Override
        public void addDeleteItem(String filepath) {
        }

        @Override
        public List<String> getItemList() {
            return null;
        }

        @Override
        public void clearDeleteItems() {

        }
    }

    /**
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<Task> tasksAdded = new ArrayList<>();

        @Override
        public void addTask(Task task) {
            requireNonNull(task);
            tasksAdded.add(task);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
```
###### \java\seedu\address\logic\commands\CommandTaskTestUtil.java
``` java
public class CommandTaskTestUtil {

    public static final String VALID_TITLE_EXAM = "Test Preparation";
    public static final String VALID_TITLE_MARK = "Mark Test";
    public static final String VALID_TASK_DESC_EXAM = "Giving Practical Exam tips and reviewing past year test";
    public static final String VALID_TASK_DESC_MARK = "Grade test papers for CS1020 tutorial classes";
    public static final String VALID_DEADLINE_EXAM = "01-06-2018";
    public static final String VALID_DEADLINE_MARK = "04-06-2018";
    public static final String VALID_PRIORITY_EXAM = "1";
    public static final String VALID_PRIORITY_MARK = "2";

    public static final String TITLE_DESC_EXAM = " " + PREFIX_TITLE + VALID_TITLE_EXAM;
    public static final String TITLE_DESC_MARK = " " + PREFIX_TITLE + VALID_TITLE_MARK;
    public static final String TASK_DESC_DESC_EXAM = " " + PREFIX_TASK_DESC + VALID_TASK_DESC_EXAM;
    public static final String TASK_DESC_DESC_MARK = " " + PREFIX_TASK_DESC + VALID_TASK_DESC_MARK;
    public static final String DEADLINE_DESC_EXAM = " " + PREFIX_DEADLINE + VALID_DEADLINE_EXAM;
    public static final String DEADLINE_DESC_MARK = " " + PREFIX_DEADLINE + VALID_DEADLINE_MARK;
    public static final String PRIORITY_DESC_EXAM = " " + PREFIX_PRIORITY + VALID_PRIORITY_EXAM;
    public static final String PRIORITY_DESC_MARK = " " + PREFIX_PRIORITY + VALID_PRIORITY_MARK;

    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + " "; // '&' not allowed in title
    public static final String INVALID_TASK_DESC_DESC = " " + PREFIX_TASK_DESC
            + " "; // empty string not allowed in task description
    public static final String INVALID_DEADLINE_DESC = " " + PREFIX_DEADLINE + "30-13-2018"; // invalid date
    public static final String INVALID_PRIORITY_DESC = " " + PREFIX_PRIORITY + "0"; // priority from 1 to 3

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditTaskCommand.EditTaskDescriptor DESC_EXAM;
    public static final EditTaskCommand.EditTaskDescriptor DESC_MARK;
    static {
        DESC_EXAM = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_EXAM).withDesc(VALID_TASK_DESC_EXAM)
                .withDeadline(VALID_DEADLINE_EXAM).withPriority(VALID_PRIORITY_EXAM).build();
        DESC_MARK = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_MARK).withDesc(VALID_TASK_DESC_MARK)
                .withDeadline(VALID_DEADLINE_MARK).withPriority(VALID_PRIORITY_MARK).build();
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteTaskPerson(Model model) {
        Task firstTask = model.getFilteredTaskList().get(0);
        try {
            model.deleteTask(firstTask);
        } catch (TaskNotFoundException tnfe) {
            throw new AssertionError("Task in filtered list must exist in model.", tnfe);
        }
    }

    /**
     * Returns an {@code UndoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static UndoCommand prepareUndoCommand(Model model, UndoRedoStack undoRedoStack) {
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return undoCommand;
    }

    /**
     * Returns a {@code RedoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static RedoCommand prepareRedoCommand(Model model, UndoRedoStack undoRedoStack) {
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return redoCommand;
    }
}
```
###### \java\seedu\address\logic\commands\DeleteCommandTest.java
``` java
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first person deleted
        deleteCommand.execute();
        undoRedoStack.push(deleteCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        expectedModel.deletePerson(personToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteCommand not pushed into undoRedoStack
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Person} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // delete -> deletes second person in unfiltered person list / first person in filtered person list
        deleteCommand.execute();
        undoRedoStack.push(deleteCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.deletePerson(personToDelete);
        assertNotEquals(personToDelete, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> deletes same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        DeleteCommand deleteFirstCommand = prepareCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = prepareCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = prepareCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteCommand prepareCommand(Index index) {
        DeleteCommand deleteCommand = new DeleteCommand(index);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
```
###### \java\seedu\address\logic\commands\DeleteTaskCommandTest.java
``` java
public class DeleteTaskCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTask(taskToDelete);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteTaskCommand deleteTaskCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTask(taskToDelete);
        showNoTask(expectedModel);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = prepareCommand(INDEX_FIRST_TASK);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first task deleted
        deleteTaskCommand.execute();
        undoRedoStack.push(deleteTaskCommand);

        // undo -> reverts addressbook back to previous state and to show all tasks
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first task deleted again
        expectedModel.deleteTask(taskToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteTaskCommand deleteTaskCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteCommand not pushed into undoRedoStack
        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        DeleteTaskCommand deleteFirstCommand = prepareCommand(INDEX_FIRST_TASK);
        DeleteTaskCommand deleteSecondCommand = prepareCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTaskCommand deleteFirstCommandCopy = prepareCommand(INDEX_FIRST_TASK);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteTaskCommand} with the parameter {@code index}.
     */
    private DeleteTaskCommand prepareCommand(Index index) {
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(index);
        deleteTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTaskCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no task.
     */
    private void showNoTask(Model model) {
        model.updateFilteredTaskList(p -> false);

        assertTrue(model.getFilteredTaskList().isEmpty());
    }
}
```
###### \java\seedu\address\logic\commands\EditTaskCommandTest.java
``` java
public class EditTaskCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Task editedTask = new TaskBuilder().build();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = prepareCommand(INDEX_FIRST_TASK, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTask(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastTask = Index.fromOneBased(model.getFilteredTaskList().size());
        Task lastTask = model.getFilteredTaskList().get(indexLastTask.getZeroBased());

        TaskBuilder taskInList = new TaskBuilder(lastTask);
        Task editedTask = taskInList.withTitle(VALID_TITLE_EXAM).withDesc(VALID_TASK_DESC_EXAM)
                .withDeadline(VALID_DEADLINE_EXAM).build();

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_EXAM)
                .withDesc(VALID_TASK_DESC_EXAM).withDeadline(VALID_DEADLINE_EXAM).build();
        EditTaskCommand editTaskCommand = prepareCommand(indexLastTask, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTask(lastTask, editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditTaskCommand editTaskCommand = prepareCommand(INDEX_FIRST_TASK, new EditTaskDescriptor());
        Task editedTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_MARK).build();
        EditTaskCommand editTaskCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Task editedTask = new TaskBuilder().build();
        Task taskToEdit = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = prepareCommand(INDEX_FIRST_TASK, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // edit -> first task edited
        editTaskCommand.execute();
        undoRedoStack.push(editTaskCommand);

        // undo -> reverts addressbook back to previous state and filtered task list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first task edited again
        expectedModel.updateTask(taskToEdit, editedTask);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_MARK).build();
        EditTaskCommand editTaskCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> editCommand not pushed into undoRedoStack
        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        final EditTaskCommand standardCommand = prepareCommand(INDEX_FIRST_TASK, DESC_MARK);

        // same values -> returns true
        EditTaskDescriptor copyDescriptor = new EditTaskDescriptor(DESC_MARK);
        EditTaskCommand commandWithSameValues = prepareCommand(INDEX_FIRST_TASK, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preprocessUndoableCommand();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_SECOND_TASK, DESC_EXAM)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_FIRST_TASK, DESC_EXAM)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditTaskCommand prepareCommand(Index index, EditTaskDescriptor descriptor) {
        EditTaskCommand editTaskCommand = new EditTaskCommand(index, descriptor);
        editTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editTaskCommand;
    }
}
```
###### \java\seedu\address\logic\commands\ImportCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ImportCommand}.
 */
public class ImportCommandTest {

    public static final String VALID_IMPORT_FILE_PATH = "src/data/ValidImport.xml";
    public static final String INVALID_IMPORT_FILE_PATH = "src/data/InValidImport.txt";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
    }

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }

    @Test
    public void fileNotFound() {
        ImportCommand importCommand = pathInCommand(INVALID_FILE);
        assertCommandFailure(importCommand, model, ImportCommand.MESSAGE_INVALID_PATH);
    }

    /**
     * Returns an {@code ImportCommand} with parameters {@code filePath}
     */
    private ImportCommand pathInCommand(String filePath) {
        ImportCommand testCommand = new ImportCommand(filePath);
        testCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return testCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_add_alias() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(AddCommand.COMMAND_ALIAS + " "
                + PersonUtil.getPersonDetails(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_add_sign() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(AddCommand.COMMAND_SIGN + " "
                + PersonUtil.getPersonDetails(person));
        assertEquals(new AddCommand(person), command);
    }

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_clear_alias() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_delete_alias() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_delete_sign() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_SIGN + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_redoCommandAlias_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS + " 1") instanceof RedoCommand);
    }

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_undoCommandAlias_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS + " 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
```
###### \java\seedu\address\logic\parser\AddTaskCommandParserTest.java
``` java
public class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder().withTitle(VALID_TITLE_EXAM)
                .withDesc(VALID_TASK_DESC_EXAM).withDeadline(VALID_DEADLINE_EXAM)
                .withPriority(VALID_PRIORITY_EXAM).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TITLE_DESC_EXAM + TASK_DESC_DESC_EXAM
                + DEADLINE_DESC_EXAM + PRIORITY_DESC_EXAM, new AddTaskCommand(expectedTask));

        // multiple titles - last title accepted
        assertParseSuccess(parser, TITLE_DESC_MARK + TITLE_DESC_EXAM + TASK_DESC_DESC_EXAM + DEADLINE_DESC_EXAM
                + PRIORITY_DESC_EXAM, new AddTaskCommand(expectedTask));

        // multiple taskDescs - last taskDesc accepted
        assertParseSuccess(parser, TITLE_DESC_EXAM + TASK_DESC_DESC_MARK + TASK_DESC_DESC_EXAM
                + DEADLINE_DESC_EXAM + PRIORITY_DESC_EXAM, new AddTaskCommand(expectedTask));

        // multiple deadlines - last deadline accepted
        assertParseSuccess(parser, TITLE_DESC_EXAM + TASK_DESC_DESC_EXAM + DEADLINE_DESC_MARK
                + DEADLINE_DESC_EXAM + PRIORITY_DESC_EXAM, new AddTaskCommand(expectedTask));

        // multiple priorities - last priority accepted
        assertParseSuccess(parser, TITLE_DESC_EXAM + TASK_DESC_DESC_EXAM + DEADLINE_DESC_EXAM
                + PRIORITY_DESC_MARK + PRIORITY_DESC_EXAM, new AddTaskCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser, VALID_TITLE_EXAM + TASK_DESC_DESC_EXAM + DEADLINE_DESC_EXAM
                        + PRIORITY_DESC_EXAM, expectedMessage);

        // missing taskDesc prefix
        assertParseFailure(parser, TITLE_DESC_EXAM + VALID_TASK_DESC_EXAM + DEADLINE_DESC_EXAM
                + PRIORITY_DESC_EXAM, expectedMessage);

        // missing deadline prefix
        assertParseFailure(parser, TITLE_DESC_EXAM + TASK_DESC_DESC_EXAM + VALID_DEADLINE_EXAM
                + PRIORITY_DESC_EXAM, expectedMessage);

        // missing priority prefix
        assertParseFailure(parser, TITLE_DESC_EXAM + TASK_DESC_DESC_EXAM + DEADLINE_DESC_EXAM
                + VALID_PRIORITY_EXAM, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_EXAM + VALID_TASK_DESC_EXAM + VALID_DEADLINE_EXAM
                + VALID_PRIORITY_EXAM, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid title
        assertParseFailure(parser, INVALID_TITLE_DESC + TASK_DESC_DESC_EXAM + DEADLINE_DESC_EXAM
                + PRIORITY_DESC_EXAM, Title.MESSAGE_TITLE_CONSTRAINTS);

        // invalid taskDesc
        assertParseFailure(parser, TITLE_DESC_EXAM + INVALID_TASK_DESC_DESC + DEADLINE_DESC_EXAM
                + PRIORITY_DESC_EXAM, TaskDescription.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // invalid deadline
        assertParseFailure(parser, TITLE_DESC_EXAM + TASK_DESC_DESC_EXAM + INVALID_DEADLINE_DESC
                + PRIORITY_DESC_EXAM, Deadline.MESSAGE_DEADLINE_CONSTRAINTS);

        // invalid priority
        assertParseFailure(parser, TITLE_DESC_EXAM + TASK_DESC_DESC_EXAM + DEADLINE_DESC_EXAM
                + INVALID_PRIORITY_DESC, Priority.MESSAGE_PRIORITY_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TITLE_DESC + TASK_DESC_DESC_EXAM + DEADLINE_DESC_EXAM
                + INVALID_PRIORITY_DESC, Title.MESSAGE_TITLE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TASK_DESC_DESC_EXAM
                        + DEADLINE_DESC_EXAM + INVALID_PRIORITY_DESC,
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\DeleteTaskCommandParserTest.java
``` java
public class DeleteTaskCommandParserTest {
    private DeleteTaskCommandParser parser = new DeleteTaskCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteTaskCommand(INDEX_FIRST_TASK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\ImportCommandParserTest.java
``` java
public class ImportCommandParserTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_noFile_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "   ", expectedMessage);
    }
}
```
###### \java\seedu\address\testutil\EditTaskDescriptorBuilder.java
``` java
public class EditTaskDescriptorBuilder {
    private EditTaskDescriptor descriptor;

    public EditTaskDescriptorBuilder() {
        descriptor = new EditTaskDescriptor();
    }

    public EditTaskDescriptorBuilder(EditTaskDescriptor descriptor) {
        this.descriptor = new EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTaskDescriptor} with fields containing {@code task}'s details
     */
    public EditTaskDescriptorBuilder(Task task) {
        descriptor = new EditTaskDescriptor();
        descriptor.setTitle(task.getTitle());
        descriptor.setDescription(task.getTaskDesc());
        descriptor.setDeadline(task.getDeadline());
        descriptor.setPriority(task.getPriority());
    }

    /**
     * Sets the {@code Title} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code TaskDescription} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDesc(String taskDesc) {
        descriptor.setDescription(new TaskDescription(taskDesc));
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDeadline(String deadline) {
        descriptor.setDeadline(new Deadline(deadline));
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withPriority(String priority) {
        descriptor.setPriority(new Priority(priority));
        return this;
    }

    public EditTaskDescriptor build() {
        return descriptor;
    }
}
```
###### \java\seedu\address\testutil\TaskBuilder.java
``` java
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
```
###### \java\seedu\address\testutil\TypicalTasks.java
``` java
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
```
