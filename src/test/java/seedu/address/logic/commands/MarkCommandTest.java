package seedu.address.logic.commands;


import static seedu.address.logic.commands.CommandTestUtil.VALID_INT_PART_MARK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARTICIPATION_MARK;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * MarkCommand.
 * With reference to @code UpdateDisplayCommandTest.java
 */
//@@author Alaru
public class MarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_correctFieldSpecifiedUnfilteredList_success() throws Exception {
        //Rest of the fields must be the same as the typicaladdressbook
        Person updateMarkPerson = new PersonBuilder().withEmail("alice@example.com")
                .withMatriculationNumber("A1234567X").withParticipation(VALID_PARTICIPATION_MARK).build();
        MarkCommand markCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_INT_PART_MARK);

        String expectedMessage = String.format(MarkCommand.MESSAGE_SUCCESS,
                updateMarkPerson.getName().toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updateMarkPerson);

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }


    /**
     * Returns an {@code MarkCommand} with parameters {@code index} and {@code marks}
     */
    private MarkCommand prepareCommand(Index index, Integer marks) {
        MarkCommand markCommand = new MarkCommand(index, marks);
        markCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return markCommand;
    }
}
