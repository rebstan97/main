package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.RestaurantBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.RestaurantBook;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlRestaurantBookStorage addressBookStorage = new XmlRestaurantBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
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
         * {@link XmlRestaurantBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlRestaurantBookStorageTest} class.
         */
        RestaurantBook original = getTypicalRestaurantBook();
        storageManager.saveRestaurantBook(original);
        ReadOnlyRestaurantBook retrieved = storageManager.readRestaurantBook().get();
        assertEquals(original, new RestaurantBook(retrieved));
    }

    @Test
    public void addressBookReadBackup() throws Exception {
        RestaurantBook original = getTypicalRestaurantBook();
        storageManager.backupRestaurantBook(original);
        ReadOnlyRestaurantBook retrieved = storageManager.readRestaurantBook(Paths.get(getTempFilePath("ab")
                .toString() + ".backup")).get();
        assertEquals(original, new RestaurantBook(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getRestaurantBookFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlRestaurantBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleRestaurantBookChangedEvent(new RestaurantBookChangedEvent(new RestaurantBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlRestaurantBookStorageExceptionThrowingStub extends XmlRestaurantBookStorage {

        public XmlRestaurantBookStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveRestaurantBook(ReadOnlyRestaurantBook restaurantBook, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }
}
