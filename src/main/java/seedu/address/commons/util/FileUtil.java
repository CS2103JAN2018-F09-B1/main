package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Writes and reads files
 */
public class FileUtil {

    private static final String CHARSET = "UTF-8";

    public static boolean isFileExists(File file) {
        return file.exists() && file.isFile();
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     * @throws IOException if the file or directory cannot be created.
     */
    public static void createIfMissing(File file) throws IOException {
        if (!isFileExists(file)) {
            createFile(file);
        }
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories
     *
     * @return true if file is created, false if file already exists
     */
    public static boolean createFile(File file) throws IOException {
        if (file.exists()) {
            return false;
        }

        createParentDirsOfFile(file);

        return file.createNewFile();
    }

    /**
     * Creates the given directory along with its parent directories
     *
     * @param dir the directory to be created; assumed not null
     * @throws IOException if the directory or a parent directory cannot be created
     */
    public static void createDirs(File dir) throws IOException {
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Failed to make directories of " + dir.getName());
        }
    }

    /**
     * Creates parent directories of file if it has a parent directory
     */
    public static void createParentDirsOfFile(File file) throws IOException {
        File parentDir = file.getParentFile();

        if (parentDir != null) {
            createDirs(parentDir);
        }
    }

    /**
     * Assumes file exists
     */
    public static String readFromFile(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()), CHARSET);
    }

    /**
     * Writes given string to a file.
     * Will create the file if it does not exist yet.
     */
    public static void writeToFile(File file, String content) throws IOException {
        Files.write(file.toPath(), content.getBytes(CHARSET));
    }

    /**
     * Converts a string to a platform-specific file path
     * @param pathWithForwardSlash A String representing a file path but using '/' as the separator
     * @return {@code pathWithForwardSlash} but '/' replaced with {@code File.separator}
     */
    public static String getPath(String pathWithForwardSlash) {
        checkArgument(pathWithForwardSlash.contains("/"));
        return pathWithForwardSlash.replace("/", File.separator);
    }

    //@@author Alaru
    public static String getFileType(String filePath) throws IllegalValueException {
        requireNonNull(filePath);
        String trimmedFilePath = filePath.trim();
        int lastDot = trimmedFilePath.lastIndexOf('.');
        if (lastDot == -1) {
            throw new IllegalValueException("THE FILE MUST HAVE A FILE EXTENSION.");
        } else {
            return trimmedFilePath.substring(lastDot + 1);
        }
    }

    /**
     * Checks if the two given files are binary equivalent.
     * @param file1 is a file on harddisk
     * @param file2 is a different file from @code file1 on the harddisk
     * @return whether the two files given are equal
     * @throws IOException when there is an issue reading from either file
     */
    public static boolean isSameFile(File file1, File file2) throws IOException {
        if (file1.length() != file2.length()) {
            return false;
        }

        BufferedInputStream bisO = new BufferedInputStream(new FileInputStream(file1));
        BufferedInputStream bisN = new BufferedInputStream(new FileInputStream(file2));
        byte[] bufferO = new byte[4096];
        byte[] bufferN = new byte[4096];
        int fileBytes1 = bisO.read(bufferO);
        bisN.read(bufferN);
        while (fileBytes1 != -1) {
            if (!Arrays.equals(bufferO, bufferN)) {
                return false;
            }
            fileBytes1 = bisO.read(bufferO);
            bisN.read(bufferN);
        }
        return true;
    }

    /**
     * Copies a file over. The new file will be binary equivalent to the original.
     */
    public static void copyFile(String origFile, File outputFile) throws  IOException {
        byte[] buffer = new byte[4096];
        createIfMissing(outputFile);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(origFile));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFile));

        int fileBytes = bis.read(buffer);
        while (fileBytes != -1) {
            bos.write(buffer, 0, fileBytes);
            fileBytes = bis.read(buffer);
        }

        bis.close();
        bos.close();
    }

    /**
     * Copies an image from the filepath provided to the specified destination
     */
    public static void copyImage(String image, File toSave) throws IllegalValueException {
        try {
            copyFile(image, toSave);
        } catch (IOException ioe) {
            throw new IllegalValueException("IMAGE FILE COULD NOT BE COPIED.");
        }
    }

}
