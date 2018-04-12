package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import seedu.address.model.person.exceptions.IllegalMarksException;
import seedu.address.testutil.Assert;

//@@author Alaru
public class ParticipationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Participation((String) null));
        Assert.assertThrows(NullPointerException.class, () -> new Participation((Integer) null));
    }

    @Test
    public void constructor_invalidEmptyMark_throwsIllegalArgumentException() {
        String marks = "";
        Assert.assertThrows(IllegalMarksException.class, () -> new Participation(marks));

    }
    @Test
    public void constructor_invalidAlphaMark_throwsIllegalArgumentException() {
        String marks = "abcde";
        Assert.assertThrows(IllegalMarksException.class, () -> new Participation(marks));
    }

    @Test
    public void constructor_invalidAlphaNegativeMark_throwsIllegalArgumentException() {
        String marks = "-100";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Participation(marks));
    }

    @Test
    public void constructor_invalidAlphaOverLimitMark_throwsIllegalArgumentException() {
        String marks = "500";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Participation(marks));
    }

    @Test
    public void isValidParticipation() {
        // null participation
        Assert.assertThrows(NullPointerException.class, () -> Participation.isValidParticipation(null));

        // invalid participation
        Assert.assertThrows(IllegalMarksException.class, () -> Participation.isValidParticipation("")); // empty string
        Assert.assertThrows(IllegalMarksException.class, () -> Participation.isValidParticipation(" ")); // spaces only
        Assert.assertThrows(IllegalMarksException.class, () -> Participation.isValidParticipation("^")); // only non-alphanumeric characters
        Assert.assertThrows(IllegalMarksException.class, () -> Participation.isValidParticipation("abcd")); // contains alpha characters
        Assert.assertThrows(IllegalMarksException.class, () -> Participation.isValidParticipation("peter*")); // contains non-alphanumeric characters
        assertFalse(Participation.isValidParticipation("101")); // over limit
        assertFalse(Participation.isValidParticipation("-500")); // below limit

        // valid participation
        assertTrue(Participation.isValidParticipation("100")); // numbers only within 0 to 100
    }
}
