package seedu.address.logic.commands;

import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Switches between the tabs of Person List and TodoList
 */
//@@author Wu Di
public class SwitchTabCommand extends Command {

    public static final String COMMAND_WORD = "switchTab";
    public static final String COMMAND_ALIAS = "swt";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches between tabs of Person List and Todo List. ";
    public static final String MESSAGE_SUCCESS = "Tab switched.";

    private static final int PERSON_TAB = 0;
    private static final int TASK_TAB = 1;

    private TabPane tabPane;

    public SwitchTabCommand(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    @Override
    public CommandResult execute() {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        int selectedTab = selectionModel.getSelectedIndex();
        selectionModel.select(selectAnotherTab(selectedTab));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Alternates the tab index
     * @param currentTab index
     * @return alternated tab index
     */
    private int selectAnotherTab(int currentTab) {
        if (currentTab == PERSON_TAB) {
            return TASK_TAB;
        }
        return PERSON_TAB;
    }
}
