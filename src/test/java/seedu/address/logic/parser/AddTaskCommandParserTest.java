package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTaskTestUtil.DEADLINE_DESC_EXAM;
import static seedu.address.logic.commands.CommandTaskTestUtil.DEADLINE_DESC_MARK;
import static seedu.address.logic.commands.CommandTaskTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTaskTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTaskTestUtil.INVALID_TASK_DESC_DESC;
import static seedu.address.logic.commands.CommandTaskTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTaskTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTaskTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTaskTestUtil.PRIORITY_DESC_EXAM;
import static seedu.address.logic.commands.CommandTaskTestUtil.PRIORITY_DESC_MARK;
import static seedu.address.logic.commands.CommandTaskTestUtil.TASK_DESC_DESC_EXAM;
import static seedu.address.logic.commands.CommandTaskTestUtil.TASK_DESC_DESC_MARK;
import static seedu.address.logic.commands.CommandTaskTestUtil.TITLE_DESC_EXAM;
import static seedu.address.logic.commands.CommandTaskTestUtil.TITLE_DESC_MARK;
import static seedu.address.logic.commands.CommandTaskTestUtil.VALID_DEADLINE_EXAM;
import static seedu.address.logic.commands.CommandTaskTestUtil.VALID_PRIORITY_EXAM;
import static seedu.address.logic.commands.CommandTaskTestUtil.VALID_TASK_DESC_EXAM;
import static seedu.address.logic.commands.CommandTaskTestUtil.VALID_TITLE_EXAM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDescription;
import seedu.address.model.task.Title;
import seedu.address.testutil.TaskBuilder;

//@@author Wu Di
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
