package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.RestaurantBook;

/**
 * Represents a storage for {@link RestaurantBook}.
 */
public interface RestaurantBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns RestaurantBook data as a {@link ReadOnlyRestaurantBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyRestaurantBook> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyRestaurantBook> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyRestaurantBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyRestaurantBook addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyRestaurantBook)
     */
    void saveAddressBook(ReadOnlyRestaurantBook addressBook, Path filePath) throws IOException;

    /**
     * Backup the given {@link ReadOnlyRestaurantBook} to a temporary local backup file.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void backupAddressBook(ReadOnlyRestaurantBook addressBook) throws IOException;

}
