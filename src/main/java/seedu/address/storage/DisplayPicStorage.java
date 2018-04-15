package seedu.address.storage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.AppUtil;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.HashUtil;
import seedu.address.model.person.DisplayPic;

/**
 *  A class to save and open image files from the user's hard disk.
 */
//@@author Alaru
public class DisplayPicStorage {

    public static final String SAVE_LOCATION = "data/displayPic/";
    public static final String INTERNAL_DEFAULT_PIC_SAVE_LOCATION = "/images/displayPic/default.png";

    private static final Logger logger = LogsCenter.getLogger(DisplayPicStorage.class);

    /**
     * Returns true if a given string points to a valid file.
     */
    public static boolean isValidPath(String test) {
        if (MainApp.class.getResourceAsStream(test) == null) {
            File file = new File(test);
            return FileUtil.isFileExists(file);
        } else {
            return true;
        }
    }

    /**
     * Checks if the image file provided can be opened properly as an image
     * @param test is a filepath to an image file
     * @return if the filePath it is pointing to is am image file that can be opened
     */
    public static boolean isValidImage(String test) {
        try {
            InputStream imageStream = ImageIO.class.getResourceAsStream(test);
            if (imageStream == null) {
                return openImage(test);
            }
            BufferedImage image = ImageIO.read(imageStream);
            return image != null;
        } catch (IOException ioe) {
            return openImage(test);
        }
    }


    /**
     * Attempts to open a file to see if it is an image
     * @param filepath is the path to a file
     * @return whether the file located at the specified filepath is a valid image file
     */
    private static boolean openImage(String filepath) {
        try {
            BufferedImage image = ImageIO.read(new File(filepath));
            return image != null;
        } catch (IOException ioe) {
            return false;
        }
    }

    /**
     * Returns true if a given string points to a valid file that has an extension.
     */
    public static boolean hasValidExtension(String test) {
        try {
            FileUtil.getFileType(test);
            return true;
        } catch (IllegalValueException ive) {
            return false;
        }
    }

    /**
     * Creates a unique and unused file name to store the image file as
     * @param name is the unique details of a person
     * @param filePath the filepath of the image to read from
     * @param fileType the extension of the imagefile
     * @return a String that will be used as the filename and is unique
     * @throws IllegalValueException the filepath is an illegal value
     */
    public static String generateDisplayPicName(String name, String filePath, String fileType)
            throws IllegalValueException {
        String uniqueFileName = HashUtil.generateUniqueName(name);
        File toSave = new File(SAVE_LOCATION + uniqueFileName + '.' + fileType);
        while (FileUtil.isFileExists(toSave)) {
            uniqueFileName = HashUtil.generateUniqueName(uniqueFileName);
            toSave = new File(SAVE_LOCATION + uniqueFileName + '.' + fileType);
        }
        return uniqueFileName;
    }

    /**
     * Tries to save a copy of the image provided by the user into a default location.
     * @param uniqueName the name of the new image file
     * @param filePath the location of the current image file
     * @param fileType the file extension of the current image file
     */
    public static void saveDisplayPic(String uniqueName, String filePath, String fileType)
            throws IllegalValueException {
        File toSave = new File(SAVE_LOCATION + uniqueName + '.' + fileType);
        FileUtil.copyImage(filePath, toSave);
        logger.info("Successfully saved " + uniqueName + '.' + fileType + " to disk.");
    }

    /**
     * Fetches an image from the hard drive to display
     * @param dp is a DisplayPic object
     * @return An image to display
     */
    public static Image fetchDisplay(DisplayPic dp) {
        if (dp.toString().equals(INTERNAL_DEFAULT_PIC_SAVE_LOCATION)) {
            return AppUtil.getImage(INTERNAL_DEFAULT_PIC_SAVE_LOCATION);
        } else {
            String filePath = dp.toString();
            if (!DisplayPicStorage.isValidPath(filePath) || !DisplayPicStorage.isValidImage(filePath)) {
                logger.fine("Unable to open image at : " + dp.toString()
                        + ", retrieving default display picture.");
                return AppUtil.getImage(INTERNAL_DEFAULT_PIC_SAVE_LOCATION);
            }
            File input = new File(dp.toString());
            return new Image(input.toURI().toString());
        }
    }

    /**
     * Checks whether the display picture filepath between 2 DisplayPic objects are the same.
     * If they are not the same, the new display picture (in @code display1) will be updated and save.
     */
    public static DisplayPic toSaveDisplay(DisplayPic display1, DisplayPic display2, String details) {
        if (!display1.equals(display2)) {
            try {
                String uniqueName = display1.getSaveDisplay(details);
                display1.saveDisplay(uniqueName);
                return display1;
            } catch (IllegalValueException ive) {
                display1.updateToDefault();
                return display1;
            }
        }
        return display1;
    }

}
