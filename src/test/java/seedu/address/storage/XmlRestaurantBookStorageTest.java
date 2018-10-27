package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.DYLAN;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.RestaurantBook;

public class XmlRestaurantBookStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlRestaurantBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readRestaurantBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readRestaurantBook(null);
    }

    private java.util.Optional<ReadOnlyRestaurantBook> readRestaurantBook(String filePath) throws Exception {
        return new XmlRestaurantBookStorage(Paths.get(filePath))
                .readRestaurantBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readRestaurantBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readRestaurantBook("NotXmlFormatRestaurantBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readRestaurantBook_invalidPersonRestaurantBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readRestaurantBook("invalidPersonRestaurantBook.xml");
    }

    @Test
    public void readRestaurantBook_invalidAndValidPersonRestaurantBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readRestaurantBook("invalidAndValidPersonRestaurantBook.xml");
    }

    @Test
    public void readAndSaveRestaurantBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempRestaurantBook.xml");
        RestaurantBook original = getTypicalRestaurantBook();
        XmlRestaurantBookStorage xmlRestaurantBookStorage = new XmlRestaurantBookStorage(filePath);

        //Save in new file and read back
        xmlRestaurantBookStorage.saveRestaurantBook(original, filePath);
        ReadOnlyRestaurantBook readBack = xmlRestaurantBookStorage.readRestaurantBook(filePath).get();
        assertEquals(original, new RestaurantBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        xmlRestaurantBookStorage.saveRestaurantBook(original, filePath);
        readBack = xmlRestaurantBookStorage.readRestaurantBook(filePath).get();
        assertEquals(original, new RestaurantBook(readBack));

        //Save and read without specifying file path
        original.addPerson(IDA);
        xmlRestaurantBookStorage.saveRestaurantBook(original); //file path not specified
        readBack = xmlRestaurantBookStorage.readRestaurantBook().get(); //file path not specified
        assertEquals(original, new RestaurantBook(readBack));
    }

    @Test
    public void saveRestaurantBook_nullRestaurantBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveRestaurantBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code restaurantBook} at the specified {@code filePath}.
     */
    private void saveRestaurantBook(ReadOnlyRestaurantBook restaurantBook, String filePath) {
        try {
            new XmlRestaurantBookStorage(Paths.get(filePath))
                    .saveRestaurantBook(restaurantBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveRestaurantBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveRestaurantBook(new RestaurantBook(), null);
    }

    @Test
    public void backupRestaurantBook_nullRestaurantBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        backupRestaurantBook(null, "SomeFile.xml");
    }

    @Test
    public void backupRestaurantBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        backupRestaurantBook(new RestaurantBook(), null);
    }

    /**
     * Backup {@code restaurantBook} at the specified {@code filePath}.
     */
    private void backupRestaurantBook(ReadOnlyRestaurantBook restaurantBook, String filePath) {
        try {
            new XmlRestaurantBookStorage(Paths.get(filePath))
                    .backupRestaurantBook(restaurantBook);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void readAndBackupRestaurantBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempRestaurantBook.xml");
        RestaurantBook original = getTypicalRestaurantBook();
        XmlRestaurantBookStorage xmlRestaurantBookStorage = new XmlRestaurantBookStorage(filePath);
        Path path = Paths.get(filePath.toString() + ".backup");

        //Backup in a temporary storage and read back
        xmlRestaurantBookStorage.backupRestaurantBook(original);
        ReadOnlyRestaurantBook readBack = xmlRestaurantBookStorage.readRestaurantBook(path).get();
        assertEquals(original, new RestaurantBook(readBack));

        //Modify data, overwrite exiting backup file, and read back
        original.addPerson(DYLAN);
        original.removePerson(ALICE);
        xmlRestaurantBookStorage.backupRestaurantBook(original);
        readBack = xmlRestaurantBookStorage.readRestaurantBook(path).get();
        assertEquals(original, new RestaurantBook(readBack));
    }
}
