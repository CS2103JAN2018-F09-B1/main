# JoonKai1995
###### /resources/view/CalendarTaskCard.fxml
``` fxml
<HBox maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="17.0" prefWidth="126.0" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <Label fx:id="desc" alignment="CENTER_LEFT" styleClass="label-small" prefHeight="10.0" prefWidth="147.0" scaleShape="false" text="Label">
         <font>
            <Font size="11.0" />
         </font></Label>
   </children>
</HBox>
```
###### /resources/view/CalendarNode.fxml
``` fxml
<Pane prefHeight="95.0" prefWidth="147.0" styleClass="pane-with-border" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <VBox maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="90.0" prefWidth="146.0">
         <children>
            <Label fx:id="date" text="Label" styleClass="label-date" >
               <VBox.margin>
                  <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
               </VBox.margin>
               <font>
                  <Font size="10.0" />
               </font>
            </Label>
            <ListView fx:id="tasks" prefHeight="20.0" prefWidth="70.0" styleClass="list-view-calendar" VBox.vgrow="ALWAYS">
               <VBox.margin>
                  <Insets bottom="1.0" left="2.0" right="1.0" top="1.0" />
               </VBox.margin>
            </ListView>
         </children>
      </VBox>
   </children>
</Pane>
```
###### /resources/view/CalendarView.fxml
``` fxml
<VBox id="calendarVBox" fx:id="calendarVBox" maxHeight="1.7976931348623157E308" maxWidth="1.7976931348623157E308" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
   <children>
       <HBox alignment="CENTER" spacing="10">
          <children>
              <Button fx:id="previousMonth" styleClass="button" onAction="#handlePreviousButtonAction" text="&lt;&lt;" HBox.hgrow="SOMETIMES">
        </Button>
              <Label fx:id="calendarTitle" styleClass="calendar_title" text="\$calendarTitle" HBox.hgrow="SOMETIMES" />
              <Button fx:id="nextMonth" styleClass="button" onAction="#handleNextButtonAction" text="&gt;&gt;" HBox.hgrow="SOMETIMES">
        </Button>
          </children>
           <padding>
               <Insets bottom="5" left="10" right="10" top="5" />
           </padding>
           <padding>
               <Insets bottom="5" left="10" right="10" top="5" />
           </padding>
       </HBox>
      <GridPane alignment="CENTER" minHeight="-Infinity" minWidth="-Infinity">
        <columnConstraints>
          <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" minWidth="90.0" prefWidth="90.0" />
          <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" minWidth="90.0" prefWidth="90.0" />
            <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" minWidth="90.0" prefWidth="90.0" />
            <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" minWidth="90.0" prefWidth="90.0" />
            <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" minWidth="90.0" prefWidth="90.0" />
            <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" minWidth="90.0" prefWidth="90.0" />
            <ColumnConstraints halignment="CENTER" hgrow="SOMETIMES" minWidth="90.0" prefWidth="90.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
            <Label alignment="CENTER" contentDisplay="CENTER" styleClass="calendar_text" text="Sunday" textAlignment="CENTER">
               <GridPane.margin>
                  <Insets bottom="20.0" left="20.0" right="20.0" top="20.0" />
               </GridPane.margin>
            </Label>
            <Label text="Monday" styleClass="calendar_text" GridPane.columnIndex="1">
               <GridPane.margin>
                  <Insets bottom="20.0" left="20.0" right="20.0" top="20.0" />
               </GridPane.margin>
            </Label>
            <Label text="Tuesday" styleClass="calendar_text" GridPane.columnIndex="2">
               <GridPane.margin>
                  <Insets bottom="18.0" left="18.0" right="18.0" top="18.0" />
               </GridPane.margin>
            </Label>
            <Label alignment="CENTER" text="Wednesday" styleClass="calendar_text" GridPane.columnIndex="3">
               <GridPane.margin>
                  <Insets bottom="10.0" left="8.0" right="8.0" top="10.0" />
               </GridPane.margin>
            </Label>
            <Label text="Thursday" styleClass="calendar_text" GridPane.columnIndex="4">
               <GridPane.margin>
                  <Insets bottom="15.0" left="15.0" right="15.0" top="15.0" />
               </GridPane.margin>
            </Label>
            <Label text="Friday" styleClass="calendar_text" GridPane.columnIndex="5">
               <GridPane.margin>
                  <Insets bottom="20.0" left="23.0" right="23.0" top="20.0" />
               </GridPane.margin>
            </Label>
            <Label text="Saturday" styleClass="calendar_text" GridPane.columnIndex="6">
               <GridPane.margin>
                  <Insets bottom="15.0" left="15.0" right="15.0" top="15.0" />
               </GridPane.margin>
            </Label>
         </children>
         <VBox.margin>
            <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
         </VBox.margin>
      </GridPane>
      <GridPane fx:id="calendar" alignment="CENTER" styleClass="calendar-grid-pane" gridLinesVisible="true" minHeight="-Infinity" minWidth="-Infinity" VBox.vgrow="SOMETIMES">
        <columnConstraints>
          <ColumnConstraints hgrow="SOMETIMES" minWidth="90.0" prefWidth="90.0" />
          <ColumnConstraints hgrow="SOMETIMES" minWidth="90.0" prefWidth="90.0" />
            <ColumnConstraints hgrow="SOMETIMES" minWidth="90.0" prefWidth="90.0" />
            <ColumnConstraints hgrow="SOMETIMES" minWidth="90.0" prefWidth="90.0" />
            <ColumnConstraints hgrow="SOMETIMES" minWidth="90.0" prefWidth="90.0" />
            <ColumnConstraints hgrow="SOMETIMES" minWidth="90.0" prefWidth="90.0" />
            <ColumnConstraints hgrow="SOMETIMES" minWidth="90.0" prefWidth="90.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints minHeight="70.0" prefHeight="70.0" vgrow="SOMETIMES" />
          <RowConstraints minHeight="70.0" prefHeight="70.0" vgrow="SOMETIMES" />
          <RowConstraints minHeight="70.0" prefHeight="70.0" vgrow="SOMETIMES" />
            <RowConstraints minHeight="70.0" prefHeight="70.0" vgrow="SOMETIMES" />
            <RowConstraints minHeight="70.0" prefHeight="70.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <VBox.margin>
            <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
         </VBox.margin>
      </GridPane>
   </children>
</VBox>
```
###### /java/seedu/address/ui/CalendarView.java
``` java
/**
 * The Calendar of the app.
 */
public class CalendarView extends UiPart<Region> {

    private static final String FXML = "CalendarView.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private ArrayList<AnchorPane> allCalendarDays = new ArrayList<>(35);
    private YearMonth currentYearMonth;
    private ObservableList<Task>[][] tasks;
    private int currentMonth = 0;

    @FXML
    private VBox calendarVBox;
    @FXML
    private GridPane calendar;
    @FXML
    private Button previousMonth;
    @FXML
    private Button nextMonth;
    @FXML
    private Label calendarTitle;

    /**
     * Creates the calendar of the app
     */
    public CalendarView(ObservableList<Task>[][] tasksArray) {
        super(FXML);
        this.tasks = tasksArray;
        YearMonth yearMonth = YearMonth.now();
        currentYearMonth = yearMonth;
        initCalendar(yearMonth);
    }

    /**
     * Create rows and columns with anchor panes for the calendar
     */
    private void initCalendar(YearMonth yearMonth) {

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPane ap = new AnchorPane();
                ap.setPrefSize(300, 300);
                calendar.add(ap, j, i);
                allCalendarDays.add(ap);
            }
        }
        setCalendarDays(yearMonth);
    }

    /**
     * Set the days of the calendar to display the correct date
     * @param yearMonth year and month of the current month
     */
    public void setCalendarDays(YearMonth yearMonth) {
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY")) {
            calendarDate = calendarDate.minusDays(1);
        }
        for (AnchorPane ap : allCalendarDays) {
            if (ap.getChildren().size() != 0) {
                ap.getChildren().clear();
            }
            String txt = String.valueOf(calendarDate.getDayOfMonth());
            try {
                if (calendarDate.getMonthValue() == currentYearMonth.getMonthValue()) {
                    CalendarNode node = new CalendarNode(txt, tasks[currentMonth][calendarDate.getDayOfMonth()]);
                    ap.getChildren().add(node.getRoot());
                } else if (calendarDate.getMonthValue() > currentYearMonth.getMonthValue()) {
                    CalendarNode node = new CalendarNode(txt, tasks[currentMonth + 1][calendarDate.getDayOfMonth()]);
                    ap.getChildren().add(node.getRoot());
                } else {
                    CalendarNode node = new CalendarNode(txt, tasks[currentMonth - 1][calendarDate.getDayOfMonth()]);
                    ap.getChildren().add(node.getRoot());
                }
                calendarDate = calendarDate.plusDays(1);
            } catch (ArrayIndexOutOfBoundsException oob) {
                CalendarNode node = new CalendarNode(txt, FXCollections.observableArrayList());
                ap.getChildren().add(node.getRoot());
                calendarDate = calendarDate.plusDays(1);
            }
        }
        calendarTitle.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }

    /**
     * Refreshes the calendar with new information.
     */
    public void refreshCalendar() {
        initCalendar(currentYearMonth);
    }

    @FXML
    /**
     * Move the month back by one. Repopulate the calendar with the correct dates.
     */
    private void handlePreviousButtonAction() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        currentMonth--;
        setCalendarDays(currentYearMonth);
    }

    @FXML
    /**
     * Move the month forward by one. Repopulate the calendar with the correct dates.
     */
    private void handleNextButtonAction() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        currentMonth++;
        setCalendarDays(currentYearMonth);
    }

    public ArrayList<AnchorPane> getAllCalendarDays() {
        return allCalendarDays;
    }

    public void setAllCalendarDays(ArrayList<AnchorPane> allCalendarDays) {
        this.allCalendarDays = allCalendarDays;
    }
}
```
###### /java/seedu/address/ui/CalendarTaskCard.java
``` java
/**
 * A UI component that displays compressed information of a {@code Task} on the calendar.
 */
public class CalendarTaskCard extends UiPart<Region> {

    private static final String FXML = "CalendarTaskCard.fxml";

    public final Task task;

    @FXML
    private Label desc;

    public CalendarTaskCard(Task task) {
        super(FXML);
        this.task = task;
        if (task.getTitle().toString().length() <= 20) {
            desc.setText(task.getTitle().toString());
        } else {
            String text = task.getTitle().toString().substring(0, 20) + "...";
            desc.setText(text);
        }
        if (task.getPriority().value == 1) {
            desc.getStyleClass().clear();
            desc.getStyleClass().add("label-small-green");
        } else if (task.getPriority().value == 2) {
            desc.getStyleClass().clear();
            desc.getStyleClass().add("label-small-yellow");
        } else {
            desc.getStyleClass().clear();
            desc.getStyleClass().add("label-small-red");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CalendarTaskCard)) {
            return false;
        }

        // state check
        CalendarTaskCard card = (CalendarTaskCard) other;
        return task.equals(card.task);
    }
}
```
###### /java/seedu/address/ui/CalendarNode.java
``` java
/**
 * Create an anchor pane that can store additional data.
 */
public class CalendarNode extends UiPart<Region> {

    private static final String FXML = "CalendarNode.fxml";

    @FXML
    private Label date;

    @FXML
    private ListView<CalendarTaskCard> tasks;

    /**
     * Create a calendar node.
     * @param txt the date of the node
     * @param uniqueTaskList the task list linked to it
     */
    public CalendarNode(String txt, ObservableList<Task> uniqueTaskList) {
        super(FXML);
        date.setText(txt);
        setConnections(uniqueTaskList);
    }

    private void setConnections(ObservableList<Task> uniqueTaskList) {
        ObservableList<CalendarTaskCard> mappedList = EasyBind.map(
                uniqueTaskList, (task) -> new CalendarTaskCard(task));
        tasks.setItems(mappedList);
        tasks.setCellFactory(listView -> new TasksCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code CalendarTaskCard}.
     */
    class TasksCell extends ListCell<CalendarTaskCard> {

        @Override
        protected void updateItem(CalendarTaskCard task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(task.getRoot());
            }
        }
    }

}
```
###### /java/seedu/address/logic/parser/AddTaskCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_TASK_DESC,
                        PREFIX_DEADLINE, PREFIX_PRIORITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_TASK_DESC,
                PREFIX_DEADLINE, PREFIX_PRIORITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        try {
            Title title = ParserUtil.parseTaskTitle(argMultimap.getValue(PREFIX_TITLE)).get();
            TaskDescription taskDescription = ParserUtil
                .parseTaskDescription(argMultimap.getValue(PREFIX_TASK_DESC)).get();
            Deadline deadline =
                    ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE)).get();
            Priority priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY)).get();

            Task task = new Task(title, taskDescription, deadline, priority);

            return new AddTaskCommand(task);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

```
###### /java/seedu/address/logic/commands/AddTaskCommand.java
``` java
/**
 * Adds a task to the To-do list and calendar.
 */
public class AddTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addTask";
    public static final String COMMAND_ALIAS = "at";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the todo list and calendar. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_TASK_DESC + "TASK "
            + PREFIX_DEADLINE + "DEADLINE "
            + PREFIX_PRIORITY + "PRIORITY LEVEL\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Dance "
            + PREFIX_TASK_DESC + "Dance till I drop "
            + PREFIX_DEADLINE + "20-03-2018 "
            + PREFIX_PRIORITY + "1";

    public static final String MESSAGE_SUCCESS = "New Task added: %1$s";

    private final Task toAdd;

    /**
     * Creates an AddTaskCommand to add the specified {@code Task}
     */
    public AddTaskCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        model.addTask(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));


    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && toAdd.equals(((AddTaskCommand) other).toAdd));
    }
}

```
###### /java/seedu/address/storage/XmlAdaptedTask.java
``` java
/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";
    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String taskDescription;
    @XmlElement(required = true)
    private String deadline;
    @XmlElement(required = true)
    private String priority;

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedTask(String title, String taskDescription, String deadline, String priority) {
        this.title = title;
        this.taskDescription = taskDescription;
        this.deadline = deadline;
        this.priority = priority;
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedTask (Task source) {
        title = source.getTitle().toString();
        taskDescription = source.getTaskDesc().value;
        deadline = source.getDeadline().dateString;
        priority = source.getPriority().priority;
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {

        if (this.title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Title.class.getSimpleName()));
        }

        if (!Title.isValidTitle(this.title)) {
            throw new IllegalValueException(Title.MESSAGE_TITLE_CONSTRAINTS);
        }
        final Title title = new Title(this.title);

        if (this.taskDescription == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TaskDescription.class.getSimpleName()));
        }

        if (!TaskDescription.isValidDescription(this.taskDescription)) {
            throw new IllegalValueException(TaskDescription.MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        final TaskDescription taskDesc = new TaskDescription(this.taskDescription);

        if (this.deadline == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Deadline.class.getSimpleName()));
        }

        if (!Deadline.isValidDeadline(this.deadline)) {
            throw new IllegalValueException(Deadline.MESSAGE_DEADLINE_CONSTRAINTS);
        }

        final Deadline deadline = new Deadline(this.deadline);

        if (this.priority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Priority.class.getSimpleName()));
        }
        if (!Priority.isValidPriority(this.priority)) {
            throw new IllegalValueException(Priority.MESSAGE_PRIORITY_CONSTRAINTS);
        }
        final Priority priority = new Priority(this.priority);

        return new Task(title, taskDesc, deadline, priority);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTask)) {
            return false;
        }

        XmlAdaptedTask otherTask = (XmlAdaptedTask) other;
        return Objects.equals(title, otherTask.title)
                && Objects.equals(taskDescription, otherTask.taskDescription)
                && Objects.equals(deadline, otherTask.deadline)
                && Objects.equals(priority, otherTask.priority);
    }
}
```
###### /java/seedu/address/storage/XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<Task> getTaskList() {
        final ObservableList<Task> tasks = this.tasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(tasks);
    }
```
###### /java/seedu/address/storage/XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<Task>[][] getCalendarList() {
        return null;
    }

    @Override
    public ObservableList<Tag> getTagList() {
        final ObservableList<Tag> tags = this.tags.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(tags);
    }

    @Override
    public ObservableList<String> getItemList() {
        final ObservableList<String> items = this.items.stream().map(it -> {
            try {
                return it.toModelType();
            } catch (IllegalValueException e) {
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(items);
    }
}
```
###### /java/seedu/address/model/person/MatriculationNumber.java
``` java
/**
 * Represents a Person's matriculation number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMatricNumber(String)}
 */
public class MatriculationNumber {


    public static final String MESSAGE_MATRIC_NUMBER_CONSTRAINTS =
            "The first character of the matriculation number should be either an 'A' or 'U',"
                + " followed by 7 digits and end with a capital letter.";
    public static final String MATRIC_NUMBER_VALIDATION_REGEX_FIRST = "[AU]{1}";
    public static final String MATRIC_NUMBER_VALIDATION_REGEX_SECOND = "\\d{7}";
    public static final String MATRIC_NUMBER_VALIDATION_REGEX_LAST = "[A-Z]{1}";
    public final String value;

    /**
     * Constructs a {@code MatriculationNumber}.
     *
     * @param matricNumber A valid matriculation number.
     */
    public MatriculationNumber(String matricNumber) {
        requireNonNull(matricNumber);
        checkArgument(isValidMatricNumber(matricNumber), MESSAGE_MATRIC_NUMBER_CONSTRAINTS);
        this.value = matricNumber;
    }

    /**
     * Returns true if a given string is a valid person matriculation number.
     */
    public static boolean isValidMatricNumber(String test) {
        if (test.length() != 9) {
            return false;
        }
        String firstCharacter = test.substring(0, 1);
        String nextCharacters = test.substring(1, test.length() - 1);
        String lastCharacter = test.substring(test.length() - 1, test.length());
        return firstCharacter.matches(MATRIC_NUMBER_VALIDATION_REGEX_FIRST)
                && nextCharacters.matches(MATRIC_NUMBER_VALIDATION_REGEX_SECOND)
                && lastCharacter.matches(MATRIC_NUMBER_VALIDATION_REGEX_LAST);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatriculationNumber // instanceof handles nulls
                && this.value.equals(((MatriculationNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void addTask(Task task) {
        addressBook.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_CURRENT_TASKS);
        indicateAddressBookChanged();
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void addDeleteItem(String filepath) {
        addressBook.addDeleteItem(filepath);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void clearDeleteItems() {
        addressBook.clearItems();
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

```
###### /java/seedu/address/model/task/Task.java
``` java
/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task implements Comparable<Task> {

    private final Title title;
    private final TaskDescription taskDesc;
    private final Deadline deadline;
    private final Priority priority;

    /**
     * Every field must be present and not null.
     */
    public Task(Title title, TaskDescription taskDesc, Deadline deadline, Priority priority) {
        requireAllNonNull(title, taskDesc, deadline, priority);
        this.title = title;
        this.taskDesc = taskDesc;
        this.deadline = deadline;
        this.priority = priority;
    }

    public Title getTitle() {
        return title;
    }
    public TaskDescription getTaskDesc() {
        return taskDesc;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public Priority getPriority() {
        return priority;
    }

    public int getDeadlineDay() {
        return deadline.day;
    }

    public int getDeadlineYear() {
        return deadline.year;
    }

    public int getDeadlineMonth() {
        return deadline.month;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.address.model.person.Person)) {
            return false;
        }

        seedu.address.model.task.Task otherTask = (seedu.address.model.task.Task) other;
        return  otherTask.getTitle().equals(this.getTitle())
                && otherTask.getTaskDesc().equals(this.getTaskDesc())
                && otherTask.getDeadline().equals(this.getDeadline())
                && otherTask.getPriority().equals(this.getPriority());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskDesc, deadline, priority);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Title: ")
                .append(getTitle())
                .append(" Task TaskDescription: ")
                .append(getTaskDesc())
                .append(" Deadline: ")
                .append(getDeadline())
                .append(" Calculated Priority: ")
                .append(getPriority());
        return builder.toString();
    }
    @Override
    public int compareTo(Task task) {
        return task.getPriority().value - this.getPriority().value;
    }
}
```
###### /java/seedu/address/model/task/UniqueTaskList.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    private final ObservableList<Task>[][] calendarList = new ObservableList[7][32];

    private Date dateNow = new Date();

    private LocalDate now = dateNow.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    private int monthNow = now.getMonthValue();

    public UniqueTaskList() {
        super();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 32; j++) {
                calendarList[i][j] = FXCollections.observableArrayList();
            }
        }
    }

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(Task toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     */
    public void add(Task toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
        int diff = toAdd.getDeadline().diff;
        calendarList[diff][toAdd.getDeadlineDay()].add(toAdd);
        Collections.sort(calendarList[diff][toAdd.getDeadlineDay()]);
    }

```
###### /java/seedu/address/model/task/TaskDescription.java
``` java
/**
 * Represents a short description of a todo task
 */
public class TaskDescription {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "Task description can take any values, and it should not be blank";

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DESCRIPTION_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    public final String shortDesc;

    /**
     * Constructs an {@code TaskDescription}.
     *
     * @param description A valid address.
     */
    public TaskDescription(String description) {
        assert description != null : MESSAGE_DESCRIPTION_CONSTRAINTS;
        checkArgument(isValidDescription(description));
        this.value = description;
        if (value.length() <= 20) {
            shortDesc = value;
        } else {
            shortDesc = value.substring(0, 20) + "...";
        }
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDescription // instanceof handles nulls
                && this.value.equals(((TaskDescription) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/task/Deadline.java
``` java
/**
 * Represents a Task's deadline in the address book.
 * Guarantees: immutable;
 */
public class Deadline {


    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
            "Deadline should be a valid date that exists and in the format dd-mm-yyyy. Tasks cannot be scheduled in the past."
                    + "And can only be scheduled at most 6 months in advance. (Based on months: tasks cannot be"
                    + " scheduled on 1st August 2018 if the current date is 31st January 2018).";
    public final String dateString;
    public final LocalDate value;
    public final int diff;
    public final int day;
    public final int month;
    public final int year;

    /**
     * Constructs a {@code Deadline}.
     *
     * @param deadline A valid deadline.
     */
    public Deadline(String deadline) {
        requireNonNull(deadline);
        dateString = deadline;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate deadlineDate = LocalDate.parse(deadline, formatter);
        this.value = deadlineDate;
        LocalDate now = LocalDate.now();
        this.diff = calculateDifference(deadlineDate, now);
        this.day = deadlineDate.getDayOfMonth();
        this.month = deadlineDate.getMonthValue();
        this.year = deadlineDate.getYear();
    }

    /**
     * Returns true if a given string is a valid deadline.
     */
    public static boolean isValidDeadline(String test) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate deadlineDate = LocalDate.parse(test, formatter);
            LocalDate now = LocalDate.now();
            if (deadlineDate.getYear() < now.getYear()) {
                throw new IllegalArgumentException(MESSAGE_DEADLINE_CONSTRAINTS);
            }
            if (deadlineDate.getMonthValue() < now.getMonthValue() && deadlineDate.getYear() == now.getYear()) {
                throw new IllegalArgumentException(MESSAGE_DEADLINE_CONSTRAINTS);
            }
            if (deadlineDate.getMonthValue() == now.getMonthValue()
                    && deadlineDate.getYear() == now.getYear() && deadlineDate.getDayOfMonth() < now.getDayOfMonth()) {
                throw new IllegalArgumentException(MESSAGE_DEADLINE_CONSTRAINTS);
            }
            if (!isWithinSixMonths(deadlineDate, now)) {
                throw new IllegalArgumentException(MESSAGE_DEADLINE_CONSTRAINTS);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * checks and see if the deadline is within 6 months of the current date.
     * @return
     */
    public static boolean isWithinSixMonths(LocalDate deadlineDate, LocalDate now) {
        int difference;
        if (deadlineDate.getYear() == now.getYear()) {
            difference = deadlineDate.getMonthValue() - now.getMonthValue();
        } else if (deadlineDate.getYear() - now.getYear() == 1) {
            difference = 12 - now.getMonthValue() + deadlineDate.getMonthValue();
        } else {
            difference = 100;
        }
        return difference <= 6;
    }

    /**
     * Calculates the value of the difference in months between the deadline and the current date.
     * @return
     */
    private int calculateDifference(LocalDate deadlineDate, LocalDate now) {
        int diff;
        if (deadlineDate.getYear() == now.getYear()) {
            diff = deadlineDate.getMonthValue() - now.getMonthValue();
        } else {
            diff = 12 - now.getMonthValue() + deadlineDate.getMonthValue();
        }
        return diff;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.value.equals(((Deadline) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/task/Priority.java
``` java
/**
 * Represents a Task's priority in the todo list and calendar.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {


    public static final String MESSAGE_PRIORITY_CONSTRAINTS =
            "Priority value input can only be a value from 1 to 3. 1 being lowest priority and 3 being highest.";
    public static final String PRIORITY_VALIDATION_REGEX = "[1-3]{1}";
    public final String priority;
    public final int value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param priorityValue A valid priority value.
     */
    public Priority(String priorityValue) {
        requireNonNull(priorityValue);
        checkArgument(isValidPriority(priorityValue), MESSAGE_PRIORITY_CONSTRAINTS);
        this.priority = priorityValue;
        this.value = Integer.parseInt(priorityValue);
    }

    /**
     * Returns true if a given string is a valid task priority.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return priority;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.priority.equals(((Priority) other).priority)); // state check
    }

    @Override
    public int hashCode() {
        return priority.hashCode();
    }

}
```
