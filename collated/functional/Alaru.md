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
        for (String item : itemsToDelete) {
            boolean notUsed = true;
            for (Person p : persons) {
                if (p.getDisplayPic().toString().equals(item) || p.getDisplayPic().isDefault()) {
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
```
###### \java\seedu\address\commons\util\FileUtil.java
``` java
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
```
###### \java\seedu\address\commons\util\HashUtil.java
``` java
public class HashUtil {

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
    public static final String MESSAGE_INVALID_PARAMETER_VALUE =
            "The marks/ field cannot be empty and it must be an integer from 0 to 100 inclusive";

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
            throw new AssertionError("There cannot be a duplicate when adding participation");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

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
     * Creates and returns a {@code Person} with the details of {@code personToMark}
     * edited with the new marks.
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
###### \java\seedu\address\logic\commands\UpdateDisplayCommand.java
``` java
public class UpdateDisplayCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "updateDP";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the display picture for a student "
            + "Parameters: [INDEX] (must be a positive integer) "
            + PREFIX_DISPLAY_PIC + "[PATH TO IMAGE]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_DISPLAY_PIC + "C:\\Users\\Name\\Desktop\\John.png";

    public static final String MESSAGE_SUCCESS = "Display Picture successfully updated for %1$s!";

    private final Index targetIndex;
    //private final EditCommand.EditPersonDescriptor editPersonDescriptor;
    private final DisplayPic dp;

    private Person personToUpdate;
    private Person updatedPerson;

    /**
     * Creates an MarkCommand to add the participation marks of {@code marks}
     */
    public UpdateDisplayCommand(Index index, DisplayPic dp) {
        requireNonNull(index);
        requireNonNull(dp);
        this.targetIndex = index;
        this.dp = dp;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.updatePerson(personToUpdate, updatedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("There cannot be a duplicate when just updating the display pic");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SUCCESS, personToUpdate.getName().toString()));

    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToUpdate = lastShownList.get(targetIndex.getZeroBased());
        updatedPerson = createUpdatedPerson(personToUpdate, dp);

    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToUpdate}
     * edited with the new Display Pic.
     */
    private Person createUpdatedPerson(Person personToUpdate, DisplayPic dp)
            throws CommandException {
        assert personToUpdate != null;

        Name updatedName = personToUpdate.getName();
        MatriculationNumber updatedMatricNumber = personToUpdate.getMatricNumber();
        Phone updatedPhone = personToUpdate.getPhone();
        Email updatedEmail = personToUpdate.getEmail();
        Address updatedAddress = personToUpdate.getAddress();
        DisplayPic updatedDisplay = DisplayPicStorage.toSaveDisplay(dp,
                personToUpdate.getDisplayPic(), personToUpdate.getDetails());
        Participation updatedPart = personToUpdate.getParticipation();
        Set<Tag> updatedTags = personToUpdate.getTags();

        return new Person(updatedName, updatedMatricNumber, updatedPhone, updatedEmail, updatedAddress, updatedDisplay,
                updatedPart, updatedTags);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UpdateDisplayCommand // instanceof handles nulls
                && targetIndex.equals(((UpdateDisplayCommand) other).targetIndex)
                && dp.equals(((UpdateDisplayCommand) other).dp));
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
            Integer marks = ParserUtil.parseMarks(argMultimap.getValue(PREFIX_MARK_PARTICIPATION).get());
            checkArgument(Participation.isValidParticipation(Integer.toString(marks)),
                    Participation.MESSAGE_PARTICIPATION_CONSTRAINTS);
            return new MarkCommand(index, marks);
        } catch (IllegalArgumentException | IllegalValueException ie) {
            throw new ParseException(MarkCommand.MESSAGE_INVALID_PARAMETER_VALUE);
        } catch (NoSuchElementException nsee) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }
    }

}

```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String displayPic} into an {@code DisplayPic}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code displayPic} is invalid.
     */
    public static DisplayPic parseDisplayPic(String displayPic)
            throws IllegalValueException {
        if (displayPic == null) {
            return new DisplayPic();
        } else {
            String trimmedDisplayPath = displayPic.trim();
            if (!DisplayPicStorage.isValidPath(trimmedDisplayPath)) {
                throw new IllegalValueException(DisplayPic.MESSAGE_DISPLAY_PIC_NONEXISTENT_CONSTRAINTS);
            }
            if (!DisplayPicStorage.isValidImage(trimmedDisplayPath)) {
                throw new IllegalValueException(DisplayPic.MESSAGE_DISPLAY_PIC_NOT_IMAGE);
            }
            return new DisplayPic(displayPic);
        }
    }

    /**
     * Parses a {@code Optional<String> displayPic} into an {@code Optional<DisplayPic>}
     * if {@code displayPic} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<DisplayPic> parseDisplayPic(Optional<String> displayPic) throws IllegalValueException {
        if (displayPic.isPresent()) {
            return Optional.of(parseDisplayPic(displayPic.get()));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Parses a {@code Optional<String> displayPic} into an {@code Optional<DisplayPic>}
     * if {@code displayPic} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<DisplayPic> parseEditDisplayPic(Optional<String> displayPic) throws IllegalValueException {
        requireNonNull(displayPic);
        return displayPic.isPresent() ? Optional.of(parseDisplayPic(displayPic.get())) : Optional.empty();
    }

    /**
     * Parses {@code String marks} into a {@code Integer marks}
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static Integer parseMarks(String marks) throws IllegalValueException {
        requireNonNull(marks);
        return Integer.parseInt(marks);
    }

    /**
     * Parses a {@code Optional<String> marks} into an {@code Optional<Integer>} if {@code marks} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     * @param marks are the marks to add
     */
    public static Optional<Integer> parseMarks(Optional<String> marks) throws IllegalValueException {
        requireNonNull(marks);
        return marks.isPresent() ? Optional.of(parseMarks(marks.get())) : Optional.empty();
    }
```
###### \java\seedu\address\logic\parser\UpdateDisplayCommandParser.java
``` java
public class UpdateDisplayCommandParser implements Parser<UpdateDisplayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkCommand
     * and returns an MarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateDisplayCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DISPLAY_PIC);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateDisplayCommand.MESSAGE_USAGE));
        }

        try {
            DisplayPic dp = ParserUtil.parseEditDisplayPic(argMultimap.getValue(PREFIX_DISPLAY_PIC)).get();
            return new UpdateDisplayCommand(index, dp);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        } catch (NoSuchElementException nsee) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateDisplayCommand.MESSAGE_USAGE));
        }


    }

}

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Adds an item to be scheduled to be deleted to the address book.
     */
    public void addDeleteItem(String filepath) {
        itemList.add(filepath);
    }

    /**
     * Removes all items to be scheduled to be deleted to the address book.
     */
    public void clearItems() {
        itemList.clear();
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public List<String> getItemList() {
        return itemList.getItemList();
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
        if (!contains(toAdd)) {
            internalList.add(toAdd);
        }
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
        for (String item : replacement) {
            if (!this.internalList.contains(item)) {
                this.internalList.add(item);
            }
        }
    }

    /**
     * Puts all the display picture paths into the UniqueItemList
     * @param persons is a UniquePersonList which contains all the people in the application
     */
    public void updateItemList(ObservableList<Person> persons) {
        for (Person p : persons) {
            add(p.getDisplayPic().toString());
        }
    }

    public void clear() {
        this.internalList.clear();
    }

    public List<String> getItemList() {
        List<String> toReturn = new ArrayList<>(internalList);
        return Collections.unmodifiableList(toReturn);
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
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void clearDeleteItems() {
        addressBook.clearItems();
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\person\DisplayPic.java
``` java
public class DisplayPic {

    public static final String DEFAULT_DISPLAY_PIC = "/images/displayPic/default.png";
    public static final String DEFAULT_IMAGE_LOCATION = "data/displayPic/";
    public static final String MESSAGE_DISPLAY_PIC_NONEXISTENT_CONSTRAINTS =
            "The filepath should lead to a file that exists.";
    public static final String MESSAGE_DISPLAY_PIC_NOT_IMAGE =
            "The filepath should point to a valid image file.";
    public static final String MESSAGE_DISPLAY_PIC_NO_EXTENSION =
            "The filepath should point to a file with an extension.";

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
    public DisplayPic(String filePath) {
        requireNonNull(filePath);
        checkArgument(DisplayPicStorage.isValidPath(filePath), MESSAGE_DISPLAY_PIC_NONEXISTENT_CONSTRAINTS);
        checkArgument(DisplayPicStorage.hasValidExtension(filePath), MESSAGE_DISPLAY_PIC_NO_EXTENSION);
        checkArgument(DisplayPicStorage.isValidImage(filePath), MESSAGE_DISPLAY_PIC_NOT_IMAGE);
        this.originalPath = filePath;
        this.value = filePath;
    }

    /**
     * Saves the display picture to the specified storage location.
     */
    public void saveDisplay(String personDetails) throws IllegalValueException {
        if (originalPath.equals(DEFAULT_DISPLAY_PIC)) {
            return;
        }
        String fileType = FileUtil.getFileType(originalPath);
        String uniqueFileName = DisplayPicStorage.saveDisplayPic(personDetails, originalPath, fileType);
        this.value = DEFAULT_IMAGE_LOCATION + uniqueFileName + '.' + fileType;
    }

    public void updateToDefault() {
        this.value = DEFAULT_DISPLAY_PIC;
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
###### \java\seedu\address\model\person\exceptions\IllegalMarksException.java
``` java
public class IllegalMarksException extends IllegalArgumentException {
    public IllegalMarksException() {
        super("Mark values are not allowed");
    }
}
```
###### \java\seedu\address\model\person\Participation.java
``` java
public class Participation {

    public static final String MESSAGE_PARTICIPATION_CONSTRAINTS = "Participation marks cannot be negative or over 100!";
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
        requireNonNull(value);
        checkArgument(isValidParticipation(value), MESSAGE_PARTICIPATION_CONSTRAINTS);
        this.value = Integer.parseInt(value);
        threshold = 50;
    }

    public Participation(Integer value) {
        requireNonNull(value);
        checkArgument(isValidParticipation(Integer.toString(value)), MESSAGE_PARTICIPATION_CONSTRAINTS);
        this.value = value;
        threshold = 50;
    }

    public Integer getMarks() {
        return value;
    }

    public boolean overThreshold() {
        return (value >= threshold);
    }

    public static boolean isValidParticipation(String value) {
        requireNonNull(value);
        try {
            return Integer.parseInt(value) <= 100 && Integer.parseInt(value) > -1;
        } catch (NumberFormatException nfe) {
            throw new IllegalMarksException();
        }
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
     * Tries to save a copy of the image provided by the user into a default location.
     * @param name the name of the new image file
     * @param filePath the location of the current image file
     * @param fileType the file extension of the current image file
     * @return the filename of the image
     */
    public static String saveDisplayPic(String name, String filePath, String fileType) throws IllegalValueException {
        try {
            boolean sameFile = false;
            File input = new File(filePath);
            String uniqueFileName = HashUtil.generateUniqueName(name);
            File toSave = new File(SAVE_LOCATION + uniqueFileName + '.' + fileType);
            while (FileUtil.isFileExists(toSave)) {
                if (FileUtil.isSameFile(input, toSave)) {
                    sameFile = true;
                    break;
                }
                uniqueFileName = HashUtil.generateUniqueName(uniqueFileName);
                toSave = new File(SAVE_LOCATION + uniqueFileName + '.' + fileType);
            }
            if (!sameFile) {
                FileUtil.copyImage(filePath, toSave);
            }
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

    /**
     * Checks whether the display picture filepath between 2 DisplayPic objects are the same.
     * If they are not the same, the new display picture (in @code display1) will be updated and save.
     */
    public static DisplayPic toSaveDisplay(DisplayPic display1, DisplayPic display2, String details) {
        if (!display1.equals(display2)) {
            try {
                display1.saveDisplay(details);
                return display1;
            } catch (IllegalValueException ive) {
                display1.updateToDefault();
                return display1;
            }
        }
        return display1;
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
###### \java\seedu\address\ui\PersonCard.java
``` java
    private void initDisplay() {
        Image image = DisplayPicStorage.fetchDisplay(person.getDisplayPic());
        displayPic.setFill(new ImagePattern(image));
        if (this.person.getParticipation().overThreshold()) {
            displayPic.setEffect(new DropShadow(+25d, 0d, +2d, Color.CHARTREUSE));
        } else {
            displayPic.setEffect(new DropShadow(+25d, 0d, +2d, Color.MAROON));
        }
    }
```
###### \resources\view\PersonListCard.fxml
``` fxml
  <Circle fx:id="displayPic" fill="chartreuse" pickOnBounds="true" radius = "55.0" >
      <HBox.margin>
         <Insets left="15.0" />
      </HBox.margin>
   </Circle>
```
