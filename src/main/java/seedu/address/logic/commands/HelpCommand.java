package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String COMMAND_SIGN = "?";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "1. add/a/+: Add person to addressbook.\n"
            + "Example: add n/NAME m/MATRIC NUMBER p/PHONE e/EMAIL [t/TAG]...\n"
            + "2. clear/c: Clear the addressbook.\n"
            + "Example: clear\n"
            + "3. delete/d/-: Delete a person from addressbook using their index.\n"
            + "Example: delete INDEX\n"
            + "4. edit/e: Edit the details of an existing person using their index.\n"
            + "Example: edit INDEX [n/NAME] [m/MATRIC NUMBER] [p/PHONE] [e/EMAIL] [t/TAG]...\n"
            + "5. exit: Exit the addressbook.\n"
            + "Example: exit\n"
            + "6. find/f: Find all persons with names containing any keyword(s) and lists them.\n"
            + "Example: find KEYWORD [KEYWORD]...\n"
            + "7. history/h: List all commands made by the user from the latest to earliest.\n"
            + "Example: history\n"
            + "8. list/ls: List all persons in the addressbook with index.\n"
            + "Example: ls\n"
            + "9. redo/r: Redo the previously undone command.\n"
            + "Example: redo\n"
            + "10. select/s: Select an existing person using their index.\n"
            + "Example: select INDEX\n"
            + "11. undo/u: Undo the previous UNDOABLE command.\n"
            + "Example: undo\n";


    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }
}
