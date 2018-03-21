package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import java.time.YearMonth;

import org.junit.Test;

import guitests.guihandles.CalendarViewHandle;

public class CalendarViewTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        CalendarView calendar = new CalendarView();
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
