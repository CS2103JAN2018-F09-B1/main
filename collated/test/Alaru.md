# Alaru
###### \java\seedu\address\logic\commands\EditPersonDescriptorTest.java
``` java
        //different display picture -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withDisplayPic(VALID_DISPLAY_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        //different participation -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withParticipation(VALID_PARTICIPATION_MARK).build();
        assertFalse(DESC_AMY.equals(editedAmy));
```
###### \java\seedu\address\logic\commands\MarkCommandTest.java
``` java
public class MarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_correctFieldSpecifiedUnfilteredList_success() throws Exception {
        //Rest of the fields must be the same as the typicaladdressbook
        Person updateMarkPerson = new PersonBuilder().withEmail("alice@example.com")
                .withMatriculationNumber("A1234567X").withParticipation(VALID_PARTICIPATION_MARK).build();
        MarkCommand markCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_INT_PART_MARK);

        String expectedMessage = String.format(MarkCommand.MESSAGE_SUCCESS,
                updateMarkPerson.getName().toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updateMarkPerson);

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }


    /**
     * Returns an {@code MarkCommand} with parameters {@code index} and {@code marks}
     */
    private MarkCommand prepareCommand(Index index, Integer marks) {
        MarkCommand markCommand = new MarkCommand(index, marks);
        markCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return markCommand;
    }
}
```
###### \java\seedu\address\logic\commands\UpdateDisplayCommandTest.java
``` java
public class UpdateDisplayCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_correctFieldSpecifiedUnfilteredList_success() throws Exception {
        //Rest of the fields must be the same as the typicaladdressbook
        Person editedDisplayPerson = new PersonBuilder().withEmail("alice@example.com")
                .withMatriculationNumber("A1234567X").build();
        DisplayPic editedDisplay = new DisplayPic(VALID_DEFAULT_DISPLAY);
        UpdateDisplayCommand updateDisplayCommand = prepareCommand(INDEX_FIRST_PERSON, editedDisplay);

        String expectedMessage = String.format(UpdateDisplayCommand.MESSAGE_SUCCESS,
                editedDisplayPerson.getName().toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedDisplayPerson);

        assertCommandSuccess(updateDisplayCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedPerson = new PersonBuilder(personInFilteredList).withDisplayPic(VALID_DEFAULT_DISPLAY).build();
        UpdateDisplayCommand updateDisplayCommand = prepareCommand(INDEX_FIRST_PERSON,
                new DisplayPic(VALID_DEFAULT_DISPLAY));

        String expectedMessage = String.format(UpdateDisplayCommand.MESSAGE_SUCCESS, updatedPerson.getName()
                .toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(updateDisplayCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DisplayPic displayPic = new DisplayPic(VALID_DEFAULT_DISPLAY);
        UpdateDisplayCommand updateDisplayCommand = prepareCommand(outOfBoundIndex, displayPic);

        assertCommandFailure(updateDisplayCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UpdateDisplayCommand updateDisplayCommand = prepareCommand(outOfBoundIndex,
                new DisplayPic(VALID_DEFAULT_DISPLAY));

        assertCommandFailure(updateDisplayCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person personToUpdate = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DisplayPic displayPic = new DisplayPic(VALID_DEFAULT_DISPLAY);
        Person updatedPerson = new PersonBuilder(personToUpdate).withDisplayPic(VALID_DEFAULT_DISPLAY).build();
        UpdateDisplayCommand updateDisplayCommand = prepareCommand(INDEX_FIRST_PERSON, displayPic);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // edit -> first person edited
        updateDisplayCommand.execute();
        undoRedoStack.push(updateDisplayCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person edited again
        expectedModel.updatePerson(personToUpdate, updatedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DisplayPic displayPic = new DisplayPic(VALID_DEFAULT_DISPLAY);
        UpdateDisplayCommand updateDisplayCommand = prepareCommand(outOfBoundIndex, displayPic);

        // execution failed -> editCommand not pushed into undoRedoStack
        assertCommandFailure(updateDisplayCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * Returns an {@code UpdateDisplayCommand} with parameters {@code index} and {@code editedDisplay}
     */
    private UpdateDisplayCommand prepareCommand(Index index, DisplayPic editedDisplay) {
        UpdateDisplayCommand updateDisplayCommand = new UpdateDisplayCommand(index, editedDisplay);
        updateDisplayCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return updateDisplayCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // multiple display paths - last display accepted
        assertParseSuccess(parser, NAME_DESC_BOB + MATRIC_NUMBER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + DISPLAY_DESC_AMY + DISPLAY_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // no display picture
        Person expectedPersonNoDisplay = new PersonBuilder().withName(VALID_NAME_BOB)
                .withMatriculationNumber(VALID_MATRIC_NUMBER_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withParticipation(VALID_PARTICIPATION_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, NAME_DESC_BOB + MATRIC_NUMBER_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedPersonNoDisplay));
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // invalid display pic - missing file
        assertParseFailure(parser, NAME_DESC_BOB + MATRIC_NUMBER_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + INVALID_DISPLAY_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, DisplayPic.MESSAGE_DISPLAY_PIC_NONEXISTENT_CONSTRAINTS);

        // invalid display pic - not image file
        assertParseFailure(parser, NAME_DESC_BOB + MATRIC_NUMBER_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + INVALID_DISPLAY_TYPE_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, DisplayPic.MESSAGE_DISPLAY_PIC_NOT_IMAGE);
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    /*@Test
    public void parseCommand_email() throws Exception {
        EmailCommand command = (EmailCommand) parser.parseCommand(
                EmailCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new EmailCommand(INDEX_FIRST_PERSON), command);
    }*/
```
###### \java\seedu\address\logic\parser\MarkCommandParserTest.java
``` java
public class MarkCommandParserTest {

    private MarkCommandParser parser = new MarkCommandParser();

    @Test
    public void parse_validArgs_returnsMarkCommand() {
        assertParseSuccess(parser, "1"
                + PARTICIPATION_DESC_MARK, new MarkCommand(INDEX_FIRST_PERSON, VALID_INT_PART_MARK));
    }

    @Test
    public void parse_invalidMarksArg_throwsParseException() {
        assertParseFailure(parser, "1" + INVALID_PARTICIPATION_MARK,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidMissingMarksArgs_throwsParseException() {
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_alphabetMarksArgs_throwsParseException() {
        assertParseFailure(parser, "1" + INVALID_ALPHABET_MARK_DESC, MarkCommand.MESSAGE_INVALID_PARAMETER_VALUE);
    }

    @Test
    public void parse_overLimitMarksArgs_throwsParseException() {
        assertParseFailure(parser, "1" + INVALID_OVER_MARK_DESC, MarkCommand.MESSAGE_INVALID_PARAMETER_VALUE);
    }
}
```
###### \java\seedu\address\logic\parser\UpdateDisplayCommandParserTest.java
``` java
public class UpdateDisplayCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateDisplayCommand.MESSAGE_USAGE);

    private UpdateDisplayCommandParser parser = new UpdateDisplayCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + DISPLAY_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + DISPLAY_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid (non existent) image
        assertParseFailure(parser, "1" + INVALID_DISPLAY_DESC, DisplayPic.MESSAGE_DISPLAY_PIC_NONEXISTENT_CONSTRAINTS);
        // invalid (not image) file
        assertParseFailure(parser, "1" + INVALID_DISPLAY_TYPE_DESC, DisplayPic.MESSAGE_DISPLAY_PIC_NOT_IMAGE);
    }

    @Test
    public void parse_validValue_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + DISPLAY_DESC_AMY;
        DisplayPic display = new DisplayPic(VALID_DISPLAY_AMY);
        UpdateDisplayCommand expectedCommand = new UpdateDisplayCommand(targetIndex, display);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedField_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + DISPLAY_DESC_AMY + DISPLAY_DESC_BOB;
        DisplayPic display = new DisplayPic(VALID_DISPLAY_BOB);
        UpdateDisplayCommand expectedCommand = new UpdateDisplayCommand(targetIndex, display);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\model\person\DisplayPicTest.java
``` java
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
```
###### \java\seedu\address\model\person\ParticipationTest.java
``` java
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
```
###### \java\seedu\address\model\UniqueItemListTest.java
``` java
public class UniqueItemListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asUnmodifiableList_modifyList_throwsUnsupportedOperationException() {
        UniqueItemList uniqueItemList = new UniqueItemList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueItemList.getItemList().remove(0);
    }

    @Test
    public void noDuplicateItems() {
        UniqueItemList uniqueItemList = new UniqueItemList();
        UniqueItemList otherItemList = new UniqueItemList();

        uniqueItemList.add("TEST");
        otherItemList.add("TEST");
        otherItemList.add("TEST");

        assertEquals(uniqueItemList, otherItemList);
    }
}
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
        /* Case: invalid display pic (missing) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_DISPLAY_DESC,
                DisplayPic.MESSAGE_DISPLAY_PIC_NONEXISTENT_CONSTRAINTS);

        /* Case: invalid display pic (not image) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + INVALID_DISPLAY_TYPE_DESC,
                DisplayPic.MESSAGE_DISPLAY_PIC_NOT_IMAGE);
```
