package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DISPLAY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DISPLAY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DISPLAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DISPLAY_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISPLAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISPLAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateDisplayCommand;
import seedu.address.model.person.DisplayPic;

/**
 * Test scope: similar to {@code EditCommandParserTest}.
 * @see EditCommandParserTest
 */
//@@author Alaru
public class UpdateDisplayCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateDisplayCommand.MESSAGE_USAGE);

    private UpdateDisplayCommandParser parser = new UpdateDisplayCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + DISPLAY_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + DISPLAY_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid (non existent) image
        assertParseFailure(parser, "1" + INVALID_DISPLAY_DESC, DisplayPic.MESSAGE_DISPLAY_PIC_NONEXISTENT_CONSTRAINTS);
        // invalid (not image) file
        assertParseFailure(parser, "1" + INVALID_DISPLAY_TYPE_DESC, DisplayPic.MESSAGE_DISPLAY_PIC_NOT_IMAGE);
    }

    @Test
    public void parse_validValue_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + DISPLAY_DESC_AMY;
        DisplayPic display = new DisplayPic(VALID_DISPLAY_AMY);
        UpdateDisplayCommand expectedCommand = new UpdateDisplayCommand(targetIndex, display);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedField_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + DISPLAY_DESC_AMY + DISPLAY_DESC_BOB;
        DisplayPic display = new DisplayPic(VALID_DISPLAY_BOB);
        UpdateDisplayCommand expectedCommand = new UpdateDisplayCommand(targetIndex, display);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
