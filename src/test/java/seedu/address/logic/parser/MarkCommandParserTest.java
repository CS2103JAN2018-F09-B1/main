package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ALPHABET_MARK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_OVER_MARK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PARTICIPATION_MARK;
import static seedu.address.logic.commands.CommandTestUtil.PARTICIPATION_DESC_MARK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INT_PART_MARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.MarkCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
//@@author Alaru
public class MarkCommandParserTest {

    private MarkCommandParser parser = new MarkCommandParser();

    @Test
    public void parse_validArgs_returnsMarkCommand() {
        assertParseSuccess(parser, "1"
                + PARTICIPATION_DESC_MARK, new MarkCommand(INDEX_FIRST_PERSON, VALID_INT_PART_MARK));
    }

    @Test
    public void parse_invalidMarksArg_throwsParseException() {
        assertParseFailure(parser, "1" + INVALID_PARTICIPATION_MARK,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidMissingMarksArgs_throwsParseException() {
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_alphabetMarksArgs_throwsParseException() {
        assertParseFailure(parser, "1" + INVALID_ALPHABET_MARK_DESC, MarkCommand.MESSAGE_INVALID_PARAMETER_VALUE);
    }

    @Test
    public void parse_overLimitMarksArgs_throwsParseException() {
        assertParseFailure(parser, "1" + INVALID_OVER_MARK_DESC, MarkCommand.MESSAGE_INVALID_PARAMETER_VALUE);
    }
}
