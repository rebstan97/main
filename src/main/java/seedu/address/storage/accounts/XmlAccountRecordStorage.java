package seedu.address.storage.accounts;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.accounts.ReadOnlyAccountRecord;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlAccountRecordStorage implements AccountRecordStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlAccountRecordStorage.class);

    private Path filePath;

    public XmlAccountRecordStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAccountRecordFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAccountRecord> readAccountRecord() throws DataConversionException, IOException {
        return readAccountRecord(filePath);
    }

    /**
     * Similar to {@link #readAccountRecord()} ()}
     *
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAccountRecord> readAccountRecord(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("AccountRecord file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableAccountRecord xmlAccountRecord = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlAccountRecord.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAccountRecord(ReadOnlyAccountRecord accountRecord) throws IOException {
        saveAccountRecord(accountRecord, filePath);
    }

    /**
     * Similar to {@link #saveAccountRecord(ReadOnlyAccountRecord)}
     *
     * @param filePath location of the data. Cannot be null
     */
    public void saveAccountRecord(ReadOnlyAccountRecord accountRecord, Path filePath) throws IOException {
        requireNonNull(accountRecord);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableAccountRecord(accountRecord));
    }

    static class XmlFileStorage {

        /**
         * Saves the given account record data to the specified file.
         */
        public static void saveDataToFile(Path file, XmlSerializableAccountRecord accountRecord)
                throws FileNotFoundException {
            try {
                XmlUtil.saveDataToFile(file, accountRecord);
            } catch (JAXBException e) {
                throw new AssertionError("Unexpected exception " + e.getMessage(), e);
            }
        }

        /**
         * Returns account record in the file or an empty account record
         */
        public static XmlSerializableAccountRecord loadDataFromSaveFile(Path file) throws DataConversionException,
                FileNotFoundException {
            try {
                return XmlUtil.getDataFromFile(file, XmlSerializableAccountRecord.class);
            } catch (JAXBException e) {
                throw new DataConversionException(e);
            }
        }
    }
}
