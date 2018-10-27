package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of RestaurantBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private RestaurantBookStorage restaurantBookStorage;
    private UserPrefsStorage userPrefsStorage;

    public StorageManager(RestaurantBookStorage restaurantBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.restaurantBookStorage = restaurantBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ RestaurantBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return restaurantBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyRestaurantBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(restaurantBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyRestaurantBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return restaurantBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyRestaurantBook addressBook) throws IOException {
        saveAddressBook(addressBook, restaurantBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyRestaurantBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        restaurantBookStorage.saveAddressBook(addressBook, filePath);
    }

    @Override
    public void backupAddressBook(ReadOnlyRestaurantBook addressBook) throws IOException {
        restaurantBookStorage.backupAddressBook(addressBook);
    }

    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
}
