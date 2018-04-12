package seedu.address.commons.util;

import java.io.File;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

/**
 * A class for deleting and managing unwanted storage files
 * Currently only used for deleting unwanted image files
 */
//@@author Alaru
public class DeleteUtil {

    public static final String DEFAULT_DISPLAY_PIC = "/images/displayPic/default.png";

    /**
     * Goes through the list of files to be deleted and only deletes those that are not in use
     * @param itemsToDelete List of items (files) to delete
     * @param persons List of Person objects in the addressbook
     */
    public static void clearImageFiles(List<String> itemsToDelete, ObservableList<Person> persons) {
        for (String item : itemsToDelete) {
            boolean notUsed = true;
            for (Person p : persons) {
                if (item.equals(DEFAULT_DISPLAY_PIC) || p.getDisplayPic().toString().equals(item)) {
                    notUsed = false;
                    break;
                }
            }
            if (notUsed) {
                deleteFile(item);
            }
        }
    }

    /**
     * Delete a file at the given filepath.
     * @param filepath where the file is located.
     */
    public static void deleteFile(String filepath) {
        File toDelete = new File(filepath);
        toDelete.delete();
    }
}
