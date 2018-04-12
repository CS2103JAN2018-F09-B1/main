package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISPLAY_PIC;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DisplayPic;
import seedu.address.model.person.Email;
import seedu.address.model.person.MatriculationNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Participation;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.storage.DisplayPicStorage;

/**
 * Updates a student's display picture in Your TA.
 */
//@@author Alaru
public class UpdateDisplayCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "updateDP";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the display picture for a student "
            + "Parameters: [INDEX] (must be a positive integer) "
            + PREFIX_DISPLAY_PIC + "[PATH TO IMAGE]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_DISPLAY_PIC + "C:\\Users\\Name\\Desktop\\John.png";

    public static final String MESSAGE_SUCCESS = "Display Picture successfully updated for %1$s!";

    private final Index targetIndex;
    //private final EditCommand.EditPersonDescriptor editPersonDescriptor;
    private final DisplayPic dp;

    private Person personToUpdate;
    private Person updatedPerson;

    /**
     * Creates an MarkCommand to add the participation marks of {@code marks}
     */
    public UpdateDisplayCommand(Index index, DisplayPic dp) {
        requireNonNull(index);
        requireNonNull(dp);
        this.targetIndex = index;
        this.dp = dp;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.updatePerson(personToUpdate, updatedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("There cannot be a duplicate when just updating the display pic");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SUCCESS, personToUpdate.getName().toString()));

    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToUpdate = lastShownList.get(targetIndex.getZeroBased());
        updatedPerson = createUpdatedPerson(personToUpdate, dp);

    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToUpdate}
     * edited with the new Display Pic.
     */
    private Person createUpdatedPerson(Person personToUpdate, DisplayPic dp)
            throws CommandException {
        assert personToUpdate != null;

        Name updatedName = personToUpdate.getName();
        MatriculationNumber updatedMatricNumber = personToUpdate.getMatricNumber();
        Phone updatedPhone = personToUpdate.getPhone();
        Email updatedEmail = personToUpdate.getEmail();
        Address updatedAddress = personToUpdate.getAddress();
        DisplayPic updatedDisplay = DisplayPicStorage.toSaveDisplay(dp,
                personToUpdate.getDisplayPic(), personToUpdate.getDetails());
        Participation updatedPart = personToUpdate.getParticipation();
        Set<Tag> updatedTags = personToUpdate.getTags();

        return new Person(updatedName, updatedMatricNumber, updatedPhone, updatedEmail, updatedAddress, updatedDisplay,
                updatedPart, updatedTags);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UpdateDisplayCommand // instanceof handles nulls
                && targetIndex.equals(((UpdateDisplayCommand) other).targetIndex)
                && dp.equals(((UpdateDisplayCommand) other).dp));
    }
}
