package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISPLAY_PIC;

import java.util.NoSuchElementException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UpdateDisplayCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DisplayPic;


/**
 * Parses input arguments and creates a new UpdateDisplayCommand object
 */
//@@author Alaru
public class UpdateDisplayCommandParser implements Parser<UpdateDisplayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkCommand
     * and returns an MarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateDisplayCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DISPLAY_PIC);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateDisplayCommand.MESSAGE_USAGE));
        }

        try {
            DisplayPic dp = ParserUtil.parseEditDisplayPic(argMultimap.getValue(PREFIX_DISPLAY_PIC)).get();
            return new UpdateDisplayCommand(index, dp);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        } catch (NoSuchElementException nsee) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateDisplayCommand.MESSAGE_USAGE));
        }


    }

}

