# Alaru
###### \java\seedu\address\commons\util\DeleteUtil.java
``` java
public class DeleteUtil {

    /**
     * Goes through the list of files to be deleted and only deletes those that are not in use
     * @param itemsToDelete List of items (files) to delete
     * @param persons List of Person objects in the addressbook
     */
    public static void clearImageFiles(List<String> itemsToDelete, ObservableList<Person> persons) {
        for (String it : itemsToDelete) {
            boolean notUsed = true;
            for (Person p : persons) {
                if (p.getDisplayPic().toString().equals(it)) {
                    notUsed = false;
                    break;
                }
            }
            if (notUsed) {
                deleteFile(it);
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
```
###### \java\seedu\address\commons\util\FileUtil.java
``` java
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
     * Copies an image from the filepath provided to the specified destination
     */
    public static void copyImage(BufferedImage image, String fileType, String destPath) throws IllegalValueException {
        try {
            File newImage = new File(destPath);
            createIfMissing(newImage);
            ImageIO.write(image, fileType, newImage);
        } catch (IOException ioe) {
            throw new IllegalValueException("IMAGE FILE COULD NOT BE COPIED.");
        }
    }
}
```
###### \java\seedu\address\commons\util\NamingUtil.java
``` java
public class NamingUtil {

    /**
     * This method uses SHA-256 to hash the 2 input strings and returns it.
     */
    public static String generateUniqueName(String details) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashValue = digest.digest(details.getBytes(StandardCharsets.UTF_8));
            return toHex(hashValue);
        } catch (NoSuchAlgorithmException nae) {
            return details;
        }
    }

    /**
     * Turns a byte array into a string hex code
     * @param hashValue is a byte array
     * @return A hex encode of a byte array
     */
```
###### \java\seedu\address\logic\commands\EmailCommand.java
``` java
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final String MAIL_SYNTAX = "mailto:";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Email the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_EMAIL_PERSON_SUCCESS = "Email Person: %1$s";

    private final Index targetIndex;

    public EmailCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToEmail = lastShownList.get(targetIndex.getZeroBased());

        String emailAddress = personToEmail.getEmail().toString();
        String emailName = personToEmail.getName().toString();

        try {
            Desktop.getDesktop().mail(new URI(MAIL_SYNTAX + emailAddress));
        } catch (HeadlessException hlError) {
            throw new UnsupportDesktopException(Messages.MESSAGE_UNSUPPORTED_DESKTOP);
        } catch (URISyntaxException | IOException error) {
            throw new CommandException(Messages.MESSAGE_MAIL_APP_ERROR);
        }

        return new CommandResult(String.format(MESSAGE_EMAIL_PERSON_SUCCESS, emailName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.targetIndex.equals(((EmailCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\exceptions\UnsupportDesktopException.java
``` java
public class UnsupportDesktopException extends CommandException {
    public UnsupportDesktopException(String message) {
        super(message);
    }
}
```
###### \java\seedu\address\logic\commands\MarkCommand.java
``` java
public class MarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "markPart";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the participation for a student "
            + "Parameters: [INDEX] (must be a positive integer) "
            + PREFIX_MARK_PARTICIPATION + "[MARKS]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_MARK_PARTICIPATION + "50";

    public static final String MESSAGE_SUCCESS = "Participation marked for %1$s!";

    private final Index targetIndex;
    private final Integer marks;

    private Person personToMark;
    private Person updatedPerson;

    /**
     * Creates an MarkCommand to add the participation marks of {@code marks}
     */
    public MarkCommand(Index index, Integer marks) {
        requireNonNull(index);
        requireNonNull(marks);
        this.targetIndex = index;
        this.marks = marks;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.updatePerson(personToMark, updatedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, personToMark.getName().toString()));

    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToMark = lastShownList.get(targetIndex.getZeroBased());
        updatedPerson = createUpdatedPerson(personToMark);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private Person createUpdatedPerson(Person personToMark) {
        assert personToMark != null;

        Integer newMarks = marks + personToMark.getParticipation().getMarks();

        newMarks = (newMarks > 100) ? 100 : newMarks;

        Participation updatedPart = new Participation(newMarks);

        return new Person(personToMark.getName(), personToMark.getMatricNumber(),
                personToMark.getPhone(), personToMark.getEmail(), personToMark.getAddress(),
                personToMark.getDisplayPic(), updatedPart, personToMark.getTags());

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MarkCommand // instanceof handles nulls
                && targetIndex.equals(((MarkCommand) other).targetIndex)
                && marks.equals(((MarkCommand) other).marks));
    }
}
```
###### \java\seedu\address\logic\parser\MarkCommandParser.java
``` java
public class MarkCommandParser implements Parser<MarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkCommand
     * and returns an MarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MARK_PARTICIPATION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }

        try {
            Integer marks = ParserUtil.parseMarks(argMultimap.getValue(PREFIX_MARK_PARTICIPATION)).get();
            checkArgument(Participation.isValidParticipation(Integer.toString(marks)),
                    Participation.MESSAGE_PARTICPATION_CONSTRAINTS);
            return new MarkCommand(index, marks);
        } catch (IllegalArgumentException | IllegalValueException ie) {
            throw new ParseException(ie.getMessage(), ie);
        }


    }

}

```
###### \java\seedu\address\model\item\UniqueItemList.java
``` java
public class UniqueItemList {

    private final ArrayList<String> internalList = new ArrayList<>();

    /**
     * Returns true if the list contains an equivalent item/filepath as the given argument.
     */
    public boolean contains(String toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a filepath to the list.
     *
     */
    public void add(String toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent item/filepath from the list.
     *
     */
    public void remove(String toRemove) {
        requireNonNull(toRemove);
        internalList.remove(toRemove);
    }

    public void setItemList(List<String> replacement) {
        requireNonNull(replacement);
        this.internalList.clear();
        this.internalList.addAll(replacement);
    }

    public void clear() {
        this.internalList.clear();
    }

    public List<String> getItemList() {
        return Collections.unmodifiableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueItemList // instanceof handles nulls
                && this.internalList.equals(((UniqueItemList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}

```
###### \java\seedu\address\model\person\DisplayPic.java
``` java
public class DisplayPic {

    public static final String DEFAULT_DISPLAY_PIC = "/images/displayPic/default.png";
    public static final String DEFAULT_IMAGE_LOCATION = "data/displayPic/";

    public final String originalPath;
    private String value;

    public DisplayPic() {
        this.originalPath = DEFAULT_DISPLAY_PIC;
        this.value = DEFAULT_DISPLAY_PIC;
    }

    /**
     * Constructs an {@code DisplayPic}.
     *
     * @param filePath A valid string containing the path to the file.
     */
    public DisplayPic(String filePath, String personDetails) throws IllegalValueException {
        requireNonNull(filePath);
        String trimmedFilePath = filePath.trim();
        this.originalPath = trimmedFilePath;
        checkArgument(DisplayPicStorage.isValidPath(trimmedFilePath),
                Messages.MESSAGE_DISPLAY_PIC_NONEXISTENT_CONSTRAINTS);
        checkArgument(DisplayPicStorage.isValidImage(trimmedFilePath), Messages.MESSAGE_DISPLAY_PIC_NOT_IMAGE);
        String fileType = FileUtil.getFileType(trimmedFilePath);
        String uniqueFileName = NamingUtil.generateUniqueName(personDetails);
        this.value = DEFAULT_IMAGE_LOCATION + uniqueFileName + '.' + fileType;
    }

    public DisplayPic(String filePath) {
        requireNonNull(filePath);
        checkArgument(DisplayPicStorage.isValidPath(filePath), Messages.MESSAGE_DISPLAY_PIC_NONEXISTENT_CONSTRAINTS);
        checkArgument(DisplayPicStorage.isValidImage(filePath), Messages.MESSAGE_DISPLAY_PIC_NOT_IMAGE);
        this.originalPath = filePath;
        this.value = filePath;
    }

    /**
     * Saves the display picture to the specified storage location.
     */
    public void saveDisplay(String personDetails) throws IllegalValueException {
        if (originalPath.equals(value)) {
            return;
        }
        String fileType = FileUtil.getFileType(originalPath);
        String uniqueFileName = DisplayPicStorage.saveDisplayPic(personDetails, originalPath, fileType);
        this.value = DEFAULT_IMAGE_LOCATION + uniqueFileName + '.' + fileType;
    }

    public void updateToDefault() {
        this.value = DEFAULT_DISPLAY_PIC;
    }

    /**
     * Updates the path the DisplayPic object points to
     * @param personDetails are the details to hash to ensure a unique value
     */
    public void updateDisplay(String personDetails) {
        try {
            String fileType = FileUtil.getFileType(value);
            String uniqueFileName = DisplayPicStorage.saveDisplayPic(personDetails, value, fileType);
            this.value = DEFAULT_IMAGE_LOCATION + uniqueFileName + '.' + fileType;
        } catch (IllegalValueException ive) {
            assert false;
        }
    }

    public boolean isDefault() {
        return value.equals(DEFAULT_DISPLAY_PIC);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayPic // instanceof handles nulls
                && this.value.equals(((DisplayPic) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Participation.java
``` java
public class Participation {

    public static final String MESSAGE_PARTICPATION_CONSTRAINTS = "Participation marks cannot be negative or over 100!";
    public static final String UI_DISPLAY_HEADER = "Participation marks: ";

    public final Integer threshold;
    private Integer value;

    /**
     * Constructs a {@code Participation}.
     */
    public Participation() {
        this.value = 0;
        threshold = 50;
    }

    public Participation(String value) {
        this.value = Integer.parseInt(value);
        threshold = 50;
    }

    public Participation(Integer value) {
        this.value = value;
        threshold = 50;
    }

    public void addParticipation(int marks) {
        value = (value + marks) % 101;
    }

    public Integer getMarks() {
        return value;
    }

    public boolean overThreshold() {
        return (value > threshold);
    }

    public static boolean isValidParticipation(String value) {
        return Integer.parseInt(value) <= 100 && Integer.parseInt(value) > -1;
    }

    public String toDisplay() {
        return UI_DISPLAY_HEADER + Integer.toString(value);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Participation // instanceof handles nulls
                && this.value.equals(((Participation) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\storage\DisplayPicStorage.java
``` java
public class DisplayPicStorage {

    public static final String SAVE_LOCATION = "data/displayPic/";
    public static final String INTERNAL_DEFAULT_PIC_SAVE_LOCATION = "/images/displayPic/default.png";

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
                try {
                    BufferedImage image = ImageIO.read(new File(test));
                    return image != null;
                } catch (IOException e3) {
                    return false;
                }
            }
            BufferedImage image = ImageIO.read(imageStream);
            return image != null;
        } catch (IOException e) {
            try {
                BufferedImage image = ImageIO.read(new File(test));
                return image != null;
            } catch (IOException e2) {
                return false;
            }
        }
    }

    /**
     * Tries to save a copy of the image provided by the user into a default location.
     * @param name the name of the new image file
     * @param filePath the location of the current image file
     * @param fileType the file extension of the current image file
     * @return the filename of the image
     */
    public static String saveDisplayPic(String name, String filePath, String fileType) throws IllegalValueException {
        try {
            File input = new File(filePath);
            BufferedImage image = ImageIO.read(input);
            String uniqueFileName = NamingUtil.generateUniqueName(name);
            File toSave = new File(DisplayPic.DEFAULT_IMAGE_LOCATION + uniqueFileName + '.' + fileType);
            while (FileUtil.isFileExists(toSave)) {
                uniqueFileName = NamingUtil.generateUniqueName(uniqueFileName);
                toSave = new File(DisplayPic.DEFAULT_IMAGE_LOCATION + uniqueFileName + '.' + fileType);
            }
            FileUtil.copyImage(image, fileType, SAVE_LOCATION + uniqueFileName + '.' + fileType);
            return uniqueFileName;
        } catch (IOException | IllegalValueException exc) {
            throw new IllegalValueException("Unable to write file");
        }
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
                return AppUtil.getImage(INTERNAL_DEFAULT_PIC_SAVE_LOCATION);
            }
            File input = new File(dp.toString());
            return new Image(input.toURI().toString());
        }
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedItem.java
``` java
public class XmlAdaptedItem {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Filepath is missing.";

    @XmlElement(required = true)
    private String filepath;

    /**
     * Constructs an XmlAdaptedItem.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedItem() {}

    /**
     * Constructs an {@code XmlAdaptedItem} with the given item details.
     */
    public XmlAdaptedItem(String filepath) {
        this.filepath = filepath;
    }

    /**
     * Converts this jaxb-friendly adapted Item object into a string
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted item
     */
    public String toModelType() throws IllegalValueException {

        if (this.filepath == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }

        return filepath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedItem)) {
            return false;
        }

        XmlAdaptedItem otherTask = (XmlAdaptedItem) other;
        return Objects.equals(filepath, otherTask.filepath);
    }
}
```
###### \resources\view\PersonListCard.fxml
``` fxml
  <ImageView fx:id="displayPic" fitHeight="100.0" fitWidth="100.0" pickOnBounds="true" preserveRatio= "false">
    <HBox.margin>
      <Insets left="15.0" />
    </HBox.margin>
  </ImageView>
```
