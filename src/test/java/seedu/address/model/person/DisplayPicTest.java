package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISPLAY_AMY;

import org.junit.Test;
import seedu.address.model.person.exceptions.IllegalMarksException;
import seedu.address.testutil.Assert;

//@@author Alaru
public class DisplayPicTest {

    public static final String INVALID_DISPLAY_PATH = "src/test/resources/images/displayPic/missing"; //Missing file
    public static final String INVALID_DISPLAY_TYPE_PATH =
            "src/test/resources/images/displayPic/wrong.txt"; //not image file
    public static final String INVALID_DISPLAY_NO_EXT_PATH =
            "src/test/resources/images/displayPic/missingExt"; //file missing extension

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DisplayPic(null));
    }

    @Test
    public void constructor_invalidMissingPath_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new DisplayPic(INVALID_DISPLAY_PATH));
    }

    @Test
    public void constructor_invalidImageFile_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new DisplayPic(INVALID_DISPLAY_TYPE_PATH));
    }

    @Test
    public void constructor_invalidMissingExtension_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new DisplayPic(INVALID_DISPLAY_NO_EXT_PATH));
    }
}
