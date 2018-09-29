package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.accounts.TypicalAccounts.DEFAULT_ADMIN_ACCOUNT;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_THREE;
import static seedu.address.testutil.accounts.TypicalAccounts.getTypicalAccountRecord;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.accounts.AccountRecord;
import seedu.address.model.accounts.ReadOnlyAccountRecord;
import seedu.address.model.accounts.Username;
import seedu.address.storage.accounts.XmlAccountRecordStorage;

public class XmlAccountRecordStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlAccountRecordStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAccountRecord_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readAccountRecord(null);
    }

    private Optional<ReadOnlyAccountRecord> readAccountRecord(String filePath) throws Exception {
        return new XmlAccountRecordStorage(Paths.get(filePath)).readAccountRecord(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAccountRecord("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAccountRecord("NotXmlFormatAccountRecord.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAccountRecord_validAccountRecord() throws Exception {
        ReadOnlyAccountRecord accountRecord = readAccountRecord("validAccountRecord.xml").get();
        assertEquals(accountRecord.getAccountList().get(0).getUsername(), new Username("azhikai"));
    }

    @Test
    public void readAccountRecord_validAccountRecord_indexOutOfBound() throws Exception {
        thrown.expect(IndexOutOfBoundsException.class);
        ReadOnlyAccountRecord accountRecord = readAccountRecord("validAccountRecord.xml").get();
        accountRecord.getAccountList().get(1);
    }

    @Test
    public void readAccountRecord_invalidAccountRecord_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAccountRecord("invalidAccountRecord.xml");
    }

    @Test
    public void readAccountRecord_invalidAndValidAccountRecord_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAccountRecord("invalidAndValidAccountRecord.xml");
    }

    @Test
    public void readAndSaveAccountRecord_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAccountRecord.xml");
        AccountRecord original = getTypicalAccountRecord();
        XmlAccountRecordStorage xmlAccountRecordStorage = new XmlAccountRecordStorage(filePath);

        //Save in new file and read back
        xmlAccountRecordStorage.saveAccountRecord(original, filePath);
        ReadOnlyAccountRecord readBack = xmlAccountRecordStorage.readAccountRecord(filePath).get();
        assertEquals(original, new AccountRecord(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addAccount(DEMO_THREE);
        original.removeAccount(DEFAULT_ADMIN_ACCOUNT);
        xmlAccountRecordStorage.saveAccountRecord(original, filePath);
        readBack = xmlAccountRecordStorage.readAccountRecord(filePath).get();
        assertEquals(original, new AccountRecord(readBack));

        //Save and read without specifying file path
        original.addAccount(DEFAULT_ADMIN_ACCOUNT);
        xmlAccountRecordStorage.saveAccountRecord(original); //file path not specified
        readBack = xmlAccountRecordStorage.readAccountRecord().get(); //file path not specified
        assertEquals(original, new AccountRecord(readBack));
    }

    @Test
    public void saveAccountRecord_nullAccountRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAccountRecord(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAccountRecord(ReadOnlyAccountRecord accountRecord, String filePath) {
        try {
            new XmlAccountRecordStorage(Paths.get(filePath))
                    .saveAccountRecord(accountRecord, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAccountRecord_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAccountRecord(new AccountRecord(), null);
    }
}
