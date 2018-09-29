package seedu.address.storage.accounts;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.AccountRecordChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.accounts.AccountRecord;
import seedu.address.model.accounts.ReadOnlyAccountRecord;

/**
 * Represents a storage for {@link AccountRecord}.
 */
public interface AccountRecordStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAccountRecordFilePath();

    /**
     * Returns AccountRecord data as a {@link ReadOnlyAccountRecord}. Returns {@code Optional.empty()} if storage file
     * is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAccountRecord> readAccountRecord() throws DataConversionException, IOException;

    /**
     * @see #getAccountRecordFilePath()
     */
    Optional<ReadOnlyAccountRecord> readAccountRecord(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAccountRecord} to the storage.
     *
     * @param accountRecord cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAccountRecord(ReadOnlyAccountRecord accountRecord) throws IOException;

    /**
     * @see #saveAccountRecord(ReadOnlyAccountRecord)
     */
    void saveAccountRecord(ReadOnlyAccountRecord addressBook, Path filePath) throws IOException;
}
