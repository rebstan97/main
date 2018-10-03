package seedu.address.testutil;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.testutil.salesrecords.TypicalRecords.getTypicalRecords;
import seedu.address.model.AddressBook;
import seedu.address.model.salesrecord.SalesRecord;
import seedu.address.model.person.Person;
/**
 * A utility class containing a list of all possible types of an {@code AddressBook} objects to be used in tests.
 */
public class TypicalAddressBook {
    /**
     * Returns an {@code AddressBook} with all the typical objects it can possibly represent.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        for (SalesRecord record : getTypicalRecords()) {
            ab.addRecord(record);
        }
        return ab;
    }
    /**
     * Returns an {@code AddressBook} with {@code Person} objects only.
     */
    public static AddressBook getTypicalAddressBookWithPersonOnly() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }
    /**
     * Returns an {@code AddressBook} with {@code SalesRecord} objects only.
     */
    public static AddressBook getTypicalAddressBookWithRecordsOnly() {
        AddressBook ab = new AddressBook();
        for (SalesRecord record : getTypicalRecords()) {
            ab.addRecord(record);
        }
        return ab;
    }
}