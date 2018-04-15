# JoonKai1995
###### \java\seedu\address\model\task\DeadlineTest.java
``` java
public class DeadlineTest {

    private LocalDate now = LocalDate.now();
    private LocalDate yesterday = now.minusDays(1);
    private LocalDate tomorrow = now.plusDays(1);
    private String dateYesterday = yesterday.toString();
    private String correctDateYesterday = dateYesterday.substring(8, 10) + "-" + dateYesterday.substring(5, 7)
        + "-" + dateYesterday.substring(0, 4);
    private String dateTomorrow = tomorrow.toString();
    private String correctDateTomorrow = dateTomorrow.substring(8, 10) + "-" + dateTomorrow.substring(5, 7)
        + "-" + dateTomorrow.substring(0, 4);

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Deadline(null));
    }

    @Test
    public void constructor_invalidDeadline_throwsDateTimeParseException() {
        String invalidDeadline = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Deadline(invalidDeadline));
    }

    @Test
    public void isValidDeadline() {
        // invalid dates
        assertFalse(Deadline.isValidDeadline("")); // empty string
        assertFalse(Deadline.isValidDeadline(" ")); // spaces only
        assertFalse(Deadline.isValidDeadline("91")); // numbers
        assertFalse(Deadline.isValidDeadline("02/04/2017")); // / instead of -
        assertFalse(Deadline.isValidDeadline(correctDateYesterday)); // scheduled yesterday

        // valid dates
        assertTrue(Deadline.isValidDeadline(correctDateTomorrow));
    }
}

```
###### \java\seedu\address\model\task\PriorityTest.java
``` java
public class PriorityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Priority(null));
    }

    @Test
    public void constructor_invalidPriority_throwsNumberFormatException() {
        String invalidPriority = "";
        Assert.assertThrows(NumberFormatException.class, () -> new Priority(invalidPriority));
    }

    @Test
    public void isValidPriority() {

        // invalid priority values
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("phone")); // non-numeric
        assertFalse(Priority.isValidPriority("911")); // more than 1 number

        // valid priority values
        assertTrue(Priority.isValidPriority("1")); //only 3 unique cases for priority
        assertTrue(Priority.isValidPriority("2"));
        assertTrue(Priority.isValidPriority("3"));
    }
}

```
###### \java\seedu\address\model\task\TaskDescriptionTest.java
``` java
public class TaskDescriptionTest {

    @Test
    public void constructor_null_throwsAssertionError() {
        Assert.assertThrows(AssertionError.class, () -> new TaskDescription(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TaskDescription(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> TaskDescription.isValidDescription(null));

        // invalid name
        assertFalse(TaskDescription.isValidDescription("")); // empty string
        assertFalse(TaskDescription.isValidDescription(" ")); // spaces only

        // valid name
        assertTrue(TaskDescription.isValidDescription("^")); // only non-alphanumeric characters
        assertTrue(TaskDescription.isValidDescription("peter*")); // contains non-alphanumeric characters
        assertTrue(TaskDescription.isValidDescription("peter jack")); // alphabets only
        assertTrue(TaskDescription.isValidDescription("12345")); // numbers only
        assertTrue(TaskDescription.isValidDescription("peter the 2nd")); // alphanumeric characters
        assertTrue(TaskDescription.isValidDescription("Capital Tan")); // with capital letters
        assertTrue(TaskDescription.isValidDescription("David Roger Jackson Ray Jr 2nd")); // long desc
    }
}

```
###### \java\seedu\address\model\task\TitleTest.java
``` java
public class TitleTest {

    @Test
    public void constructor_null_throwsAssertionError() {
        Assert.assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidTitle = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Title(invalidTitle));
    }

    @Test
    public void isValidDescription() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Title.isValidTitle(null));

        // invalid name
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only
        assertFalse(Title.isValidTitle("^")); // only non-alphanumeric characters
        assertFalse(Title.isValidTitle("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Title.isValidTitle("peter jack")); // alphabets only
        assertTrue(Title.isValidTitle("12345")); // numbers only
        assertTrue(Title.isValidTitle("peter the 2nd")); // alphanumeric characters
        assertTrue(Title.isValidTitle("Capital Tan")); // with capital letters
        assertTrue(Title.isValidTitle("David Roger Jackson Ray Jr 2nd")); // long titles
    }
}


```
###### \java\seedu\address\ui\CalendarViewTest.java
``` java
public class CalendarViewTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        ObservableList<Task>[][] lists = new ObservableList[1][1];
        lists[0][0] = FXCollections.observableArrayList();
        CalendarView calendar = new CalendarView(lists);
        uiPartRule.setUiPart(calendar);
        YearMonth yearMonth = YearMonth.now();
        String correctTitle = yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear());
        assertCalendarTitle(calendar, correctTitle);

    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCalendarTitle(CalendarView calendar, String expectedTitle) {
        guiRobot.pauseForHuman();

        CalendarViewHandle calendarViewHandle = new CalendarViewHandle(calendar.getRoot());

        // verify id is displayed correctly
        assertEquals(expectedTitle, calendarViewHandle.getCalendarTitle());
    }
}
```
