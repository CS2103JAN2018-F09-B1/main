package seedu.address.model;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.stage.Stage;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.user.Password;
import seedu.address.model.user.UniqueUserList;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;
import seedu.address.model.user.exceptions.DuplicateUserException;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlAddressBookStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * Represents the in-memory model of the login data.
 * All changes to any model should be synchronized.
 */
public class LoginManager extends ComponentManager implements Login {
    private static final Logger logger = LogsCenter.getLogger(LoginManager.class);

    private Ui ui;
    private Logic logic;
    private Storage storage;
    private Model model;
    private Config config;
    private UserPrefs userPrefs;

    private Stage primaryStage;
    private UniqueUserList userList;
    private UserPrefsStorage userPrefsStorage;
    private String username;

    /**
     * Initializes a LoginManager with the given username and password.
     */

    public LoginManager() {
        userList = new UniqueUserList();
    }

    public void setUserPrefsStorage(UserPrefsStorage userPrefsStorage) {
        this.userPrefsStorage = userPrefsStorage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public void setUserPrefs(UserPrefs userPrefs) {
        this.userPrefs = userPrefs;
    }

    public Ui getUi() {
        return ui;
    }
    public Logic getLogic() {
        return logic;
    }
    public Storage getStorage() {
        return storage;
    }
    public Model getModel() {
        return model;
    }
    public UserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    /**
     * Adds user into the userList hashmap.
     */
    public synchronized void addUser(String username, String password) throws DuplicateUserException {
        if (!userList.getUserList().containsKey(username)) {
            Username addUsername = new Username(username);
            Password addPassword = new Password(password);
            User toAdd = new User(addUsername, addPassword);
            userList.add(toAdd);
        }
    }

    public synchronized void addUser(User user) throws DuplicateUserException {
        userList.add(user);
    }

    @Override
    public void authenticate(String username, String password) throws DuplicateUserException {

        logger.fine("Authenticating user: " + username);
        this.username = username;
        String filepath = username + ".xml";
        if (userList.getUserList().containsKey(username)) {
            if (userList.getUserList().get(username).getPassword().getPassword().equals(password)) {
                loginUser(filepath);
            } else {
                throw new DuplicateUserException();
            }
        } else {
            addUser(username, password);
            loginUser(filepath);
        }

    }

    public ObservableList<User> getUserList() {
        return userList.asObservableList();
    }

    @Override
    public void loginUser(String filePath) {
        String properFilePath = username + "/" + userPrefs.getAddressBookFilePath() + filePath;
        AddressBookStorage addressBookStorage = new XmlAddressBookStorage(properFilePath);
        storage = new StorageManager(addressBookStorage, userPrefsStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, username);

        ui = new UiManager(logic, config, userPrefs);

        ui.start(primaryStage);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AddressBook");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        }

        return new ModelManager(initialData, userPrefs, username);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginManager // instanceof handles nulls
                && this.userList.equals(((LoginManager) other).userList));
    }
}
