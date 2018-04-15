package seedu.address.commons.util;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.DisplayPic;
import seedu.address.model.person.Person;

/**
 * A class for deleting unwanted storage files
 */
//@@author Alaru
public class DeleteUtil {

    private static final Logger logger = LogsCenter.getLogger(DeleteUtil.class);

    /**
     * Goes through the list of files to be deleted and only deletes those that are not in use
     * @param itemsToDelete List of items (files) to delete
     * @param persons List of Person objects in the addressbook
     */
    public static void clearImageFiles(List<String> itemsToDelete, ObservableList<Person> persons) {
        for (String item : itemsToDelete) {
            boolean notUsed = true;
            for (Person p : persons) {
                if (p.getDisplayPic().toString().equals(item) || item.equals(DisplayPic.DEFAULT_DISPLAY_PIC)) {
                    notUsed = false;
                    break;
                }
            }
            if (notUsed) {
                if (deleteFile(item)) {
                    logger.info("Successfully deleted image at " + item + " from disk.");
                } else {
                    logger.info("Unsuccessful in deleting image at " + item + " from disk.");
                }
            }
        }
    }

    /**
     * Delete a file at the given filepath.
     * @param filepath where the file is located.
     */
    public static boolean deleteFile(String filepath) {
        File toDelete = new File(filepath);
        return toDelete.delete();
    }
}
