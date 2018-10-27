package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.DYLAN;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;

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
    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readAddressBook(null);
    }

    private java.util.Optional<ReadOnlyRestaurantBook> readAddressBook(String filePath) throws Exception {
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
        assertFalse(readAddressBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAddressBook("NotXmlFormatAddressBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidPersonAddressBook.xml");
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidAndValidPersonAddressBook.xml");
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.xml");
        RestaurantBook original = getTypicalAddressBook();
        XmlRestaurantBookStorage xmlAddressBookStorage = new XmlRestaurantBookStorage(filePath);

        //Save in new file and read back
        xmlAddressBookStorage.saveRestaurantBook(original, filePath);
        ReadOnlyRestaurantBook readBack = xmlAddressBookStorage.readRestaurantBook(filePath).get();
        assertEquals(original, new RestaurantBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        xmlAddressBookStorage.saveRestaurantBook(original, filePath);
        readBack = xmlAddressBookStorage.readRestaurantBook(filePath).get();
        assertEquals(original, new RestaurantBook(readBack));

        //Save and read without specifying file path
        original.addPerson(IDA);
        xmlAddressBookStorage.saveRestaurantBook(original); //file path not specified
        readBack = xmlAddressBookStorage.readRestaurantBook().get(); //file path not specified
        assertEquals(original, new RestaurantBook(readBack));
    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyRestaurantBook addressBook, String filePath) {
        try {
            new XmlRestaurantBookStorage(Paths.get(filePath))
                    .saveRestaurantBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(new RestaurantBook(), null);
    }

    @Test
    public void backupAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        backupAddressBook(null, "SomeFile.xml");
    }

    @Test
    public void backupAddressBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        backupAddressBook(new RestaurantBook(), null);
    }

    /**
     * Backup {@code addressBook} at the specified {@code filePath}.
     */
    private void backupAddressBook(ReadOnlyRestaurantBook addressBook, String filePath) {
        try {
            new XmlRestaurantBookStorage(Paths.get(filePath))
                    .backupRestaurantBook(addressBook);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void readAndBackupAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.xml");
        RestaurantBook original = getTypicalAddressBook();
        XmlRestaurantBookStorage xmlAddressBookStorage = new XmlRestaurantBookStorage(filePath);
        Path path = Paths.get(filePath.toString() + ".backup");

        //Backup in a temporary storage and read back
        xmlAddressBookStorage.backupRestaurantBook(original);
        ReadOnlyRestaurantBook readBack = xmlAddressBookStorage.readRestaurantBook(path).get();
        assertEquals(original, new RestaurantBook(readBack));

        //Modify data, overwrite exiting backup file, and read back
        original.addPerson(DYLAN);
        original.removePerson(ALICE);
        xmlAddressBookStorage.backupRestaurantBook(original);
        readBack = xmlAddressBookStorage.readRestaurantBook(path).get();
        assertEquals(original, new RestaurantBook(readBack));
    }
}
