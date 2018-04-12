package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a TodoCard in the TodoList panel.
 */
//@@author Wu Di
public class TodoCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String TITLE_FIELD_ID = "#title";
    private static final String DESC_FIELD_ID = "#description";
    private static final String DEADLINE_FIELD_ID = "#deadline";
    private static final String PRIORITY_FIELD_ID = "#priority";

    private final Label idLabel;
    private final Label titleLabel;
    private final Label descriptionLabel;
    private final Label deadlineLabel;
    private final Label priorityLabel;

    public TodoCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.titleLabel = getChildNode(TITLE_FIELD_ID);
        this.descriptionLabel = getChildNode(DESC_FIELD_ID);
        this.deadlineLabel = getChildNode(DEADLINE_FIELD_ID);
        this.priorityLabel = getChildNode(PRIORITY_FIELD_ID);

    }

    public String getId() {
        return idLabel.getText();
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    public String getDeadline() {
        return deadlineLabel.getText();
    }

    public String getPriority() {
        return priorityLabel.getText();
    }

}
