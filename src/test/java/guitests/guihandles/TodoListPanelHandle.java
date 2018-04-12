package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.task.Task;
import seedu.address.ui.TodoCard;

/**
 * Provides a handle to a TodoCard in the TodoList panel.
 */
//@@author Wu Di
public class TodoListPanelHandle extends NodeHandle<ListView<TodoCard>> {
    public static final String TODO_LIST_VIEW_ID = "#todoListView";

    private Optional<TodoCard> lastRememberedSelectedTodoCard;

    public TodoListPanelHandle(ListView<TodoCard> todoListPanelNode) {
        super(todoListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code TodoCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public TodoCardHandle getHandleToSelectedCard() {
        List<TodoCard> taskList = getRootNode().getSelectionModel().getSelectedItems();

        if (taskList.size() != 1) {
            throw new AssertionError("Person list size expected 1.");
        }

        return new TodoCardHandle(taskList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<TodoCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the task.
     */
    public void navigateToCard(Task task) {
        List<TodoCard> cards = getRootNode().getItems();
        Optional<TodoCard> matchingCard = cards.stream().filter(card -> card.task.equals(task)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Person does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the Todocard handle of a task associated with the {@code index} in the list.
     */
    public TodoCardHandle getTodoCardHandle(int index) {
        return getTodoCardHandle(getRootNode().getItems().get(index).task);
    }

    /**
     * Returns the {@code TodoCardHandle} of the specified {@code task} in the list.
     */
    public TodoCardHandle getTodoCardHandle(Task task) {
        Optional<TodoCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.task.equals(task))
                .map(card -> new TodoCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Task does not exist."));
    }

    /**
     * Selects the {@code TodoCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code PersonCard} in the list.
     */
    public void rememberSelectedPersonCard() {
        List<TodoCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedTodoCard = Optional.empty();
        } else {
            lastRememberedSelectedTodoCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PersonCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonCard()} call.
     */
    public boolean isSelectedPersonCardChanged() {
        List<TodoCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedTodoCard.isPresent();
        } else {
            return !lastRememberedSelectedTodoCard.isPresent()
                    || !lastRememberedSelectedTodoCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
