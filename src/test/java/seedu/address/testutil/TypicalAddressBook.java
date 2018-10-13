package seedu.address.testutil;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.testutil.accounts.TypicalAccounts.getTypicalAccounts;
import static seedu.address.testutil.menu.TypicalItems.getTypicalItems;
import static seedu.address.testutil.salesrecords.TypicalRecords.getTypicalRecords;

import seedu.address.model.AddressBook;
import seedu.address.model.accounts.Account;
import seedu.address.model.menu.Item;
import seedu.address.model.person.Person;
import seedu.address.model.salesrecord.SalesRecord;

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
        for (Account account : getTypicalAccounts()) {
            ab.addAccount(account);
        }
        for (Item item : getTypicalItems()) {
            ab.addItem(item);
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
    public static AddressBook getTypicalAddressBookWithRecordOnly() {
        AddressBook ab = new AddressBook();
        for (SalesRecord record : getTypicalRecords()) {
            ab.addRecord(record);
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with {@code Account} objects only.
     */
    public static AddressBook getTypicalAddressBookWithAccountOnly() {
        AddressBook ab = new AddressBook();
        for (Account account : getTypicalAccounts()) {
            ab.addAccount(account);
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with {@code Item} objects only.
     */
    public static AddressBook getTypicalAddressBookWithItemOnly() {
        AddressBook ab = new AddressBook();
        for (Item item : getTypicalItems()) {
            ab.addItem(item);
        }
        return ab;
    }
}
