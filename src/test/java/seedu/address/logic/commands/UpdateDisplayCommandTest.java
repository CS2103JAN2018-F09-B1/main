package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEFAULT_DISPLAY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.DisplayPic;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * UpdateDisplayCommand.
 * With reference to @code EditCommandTest.java
 */
//@@author Alaru
public class UpdateDisplayCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_correctFieldSpecifiedUnfilteredList_success() throws Exception {
        //Rest of the fields must be the same as the typicaladdressbook
        Person editedDisplayPerson = new PersonBuilder().withEmail("alice@example.com")
                .withMatriculationNumber("A1234567X").build();
        DisplayPic editedDisplay = new DisplayPic(VALID_DEFAULT_DISPLAY);
        UpdateDisplayCommand updateDisplayCommand = prepareCommand(INDEX_FIRST_PERSON, editedDisplay);

        String expectedMessage = String.format(UpdateDisplayCommand.MESSAGE_SUCCESS,
                editedDisplayPerson.getName().toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedDisplayPerson);

        assertCommandSuccess(updateDisplayCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedPerson = new PersonBuilder(personInFilteredList).withDisplayPic(VALID_DEFAULT_DISPLAY).build();
        UpdateDisplayCommand updateDisplayCommand = prepareCommand(INDEX_FIRST_PERSON,
                new DisplayPic(VALID_DEFAULT_DISPLAY));

        String expectedMessage = String.format(UpdateDisplayCommand.MESSAGE_SUCCESS, updatedPerson.getName()
                .toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(updateDisplayCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DisplayPic displayPic = new DisplayPic(VALID_DEFAULT_DISPLAY);
        UpdateDisplayCommand updateDisplayCommand = prepareCommand(outOfBoundIndex, displayPic);

        assertCommandFailure(updateDisplayCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UpdateDisplayCommand updateDisplayCommand = prepareCommand(outOfBoundIndex,
                new DisplayPic(VALID_DEFAULT_DISPLAY));

        assertCommandFailure(updateDisplayCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person personToUpdate = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DisplayPic displayPic = new DisplayPic(VALID_DEFAULT_DISPLAY);
        Person updatedPerson = new PersonBuilder(personToUpdate).withDisplayPic(VALID_DEFAULT_DISPLAY).build();
        UpdateDisplayCommand updateDisplayCommand = prepareCommand(INDEX_FIRST_PERSON, displayPic);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // edit -> first person edited
        updateDisplayCommand.execute();
        undoRedoStack.push(updateDisplayCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person edited again
        expectedModel.updatePerson(personToUpdate, updatedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DisplayPic displayPic = new DisplayPic(VALID_DEFAULT_DISPLAY);
        UpdateDisplayCommand updateDisplayCommand = prepareCommand(outOfBoundIndex, displayPic);

        // execution failed -> editCommand not pushed into undoRedoStack
        assertCommandFailure(updateDisplayCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * Returns an {@code UpdateDisplayCommand} with parameters {@code index} and {@code editedDisplay}
     */
    private UpdateDisplayCommand prepareCommand(Index index, DisplayPic editedDisplay) {
        UpdateDisplayCommand updateDisplayCommand = new UpdateDisplayCommand(index, editedDisplay);
        updateDisplayCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return updateDisplayCommand;
    }
}
