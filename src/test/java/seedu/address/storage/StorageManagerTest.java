package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.accounts.TypicalAccounts.getTypicalAccountRecord;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.AccountRecordChangedEvent;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.accounts.AccountRecord;
import seedu.address.model.accounts.ReadOnlyAccountRecord;
import seedu.address.storage.accounts.XmlAccountRecordStorage;
import seedu.address.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(getTempFilePath("ab"));
        XmlAccountRecordStorage accountRecordStorage = new XmlAccountRecordStorage(getTempFilePath("ar"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, accountRecordStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.getRoot().toPath().resolve(fileName);
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlAddressBookStorageTest} class.
         */
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void addressBookReadBackup() throws Exception {
        AddressBook original = getTypicalAddressBook();
        storageManager.backupAddressBook(original);
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook(Paths.get(getTempFilePath("ab")
                .toString() + ".backup")).get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlAccountRecordStorage(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleAddressBookChangedEvent(new AddressBookChangedEvent(new AddressBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlAddressBookStorageExceptionThrowingStub extends XmlAddressBookStorage {

        public XmlAddressBookStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    @Test
    public void accountRecordReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlAccountRecordStorage} class.
         * More extensive testing is done in {@link XmlAccountRecordStorageTest} class.
         */
        AccountRecord original = getTypicalAccountRecord();
        storageManager.saveAccountRecord(original);
        ReadOnlyAccountRecord retrieved = storageManager.readAccountRecord().get();
        assertEquals(original, new AccountRecord(retrieved));
    }

    @Test
    public void getAccountRecordFilePath() {
        assertNotNull(storageManager.getAccountRecordFilePath());
    }

    @Test
    public void handleAccountRecordChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorage(Paths.get("dummy")),
                new XmlAccountRecordStorageExceptionThrowingStub(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleAccountRecordChangedEvent(new AccountRecordChangedEvent(new AccountRecord()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlAccountRecordStorageExceptionThrowingStub extends XmlAccountRecordStorage {

        public XmlAccountRecordStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveAccountRecord(ReadOnlyAccountRecord accountRecord, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }
}
