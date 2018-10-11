package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TEST;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.DYLAN;
import static seedu.address.testutil.TypicalReservations.ANDREW;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_ADMIN;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_ONE;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.accounts.Account;
import seedu.address.model.accounts.exceptions.DuplicateAccountException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.accounts.AccountBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();
    private AddressBook addressBookWithPersons = null;

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersonsWithAccounts_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE)
                .withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        List<Account> newAccounts = Arrays.asList(DEMO_ADMIN, DEMO_ONE);
        List<Reservation> newReservations = Arrays.asList(ANDREW);

        AddressBookStub newData = new AddressBookStub(newPersons, newAccounts, newReservations);

        thrown.expect(DuplicatePersonException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE)
                .withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPersonList().remove(0);
    }

    @Test
    public void removeTag_noSuchTag_addressBookUnmodified() {
        addressBookWithPersons = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();

        addressBookWithPersons.removeTag(new Tag(VALID_TAG_TEST));

        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();

        assertEquals(addressBookWithPersons, expectedAddressBook);
    }

    @Test
    public void removeTag_fromAllPersons_addressBookModified() {
        addressBookWithPersons = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();

        addressBookWithPersons.removeTag(new Tag(VALID_TAG_FRIEND));

        Person amyWithoutTags = new PersonBuilder(AMY).withTags().build();
        Person bobWithoutFriendTag = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();

        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(amyWithoutTags)
                .withPerson(bobWithoutFriendTag)
                .build();

        assertEquals(addressBookWithPersons, expectedAddressBook);
    }

    @Test
    public void removeTag_fromOnePerson_addressBookModified() {
        addressBookWithPersons = new AddressBookBuilder()
                .withPerson(AMY)
                .withPerson(DYLAN)
                .build();

        addressBookWithPersons.removeTag(new Tag(VALID_TAG_FRIEND));

        Person amyWithoutTags = new PersonBuilder(AMY).withTags().build();

        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(amyWithoutTags)
                .withPerson(DYLAN)
                .build();

        assertEquals(addressBookWithPersons, expectedAddressBook);
    }

    @Test
    public void resetData_withDuplicateAccountsWithPersons_throwsDuplicateAccountException() {
        // Two accounts with the same username
        Account account = new AccountBuilder(DEMO_ONE)
                .withUsername(DEMO_ADMIN.getUsername().toString())
                .build();
        List<Account> newAccounts = Arrays.asList(DEMO_ADMIN, account);
        List<Person> newPersons = Arrays.asList(ALICE, BOB);
        List<Reservation> newReservations = Arrays.asList(ANDREW);
        AddressBookStub newData = new AddressBookStub(newPersons, newAccounts, newReservations);

        thrown.expect(DuplicateAccountException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasAccount_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasAccount(null);
    }

    @Test
    public void hasAccount_accountNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasAccount(DEMO_ADMIN));
    }

    @Test
    public void hasAccount_accountInAddressBook_returnsTrue() {
        addressBook.addAccount(DEMO_ADMIN);
        assertTrue(addressBook.hasAccount(DEMO_ADMIN));
    }

    @Test
    public void hasAccount_accountWithSamePasswordInAddressBook_returnsTrue() {
        addressBook.addAccount(DEMO_ADMIN);
        Account account = new AccountBuilder(DEMO_ONE)
                .withPassword(DEMO_ADMIN.getPassword().toString())
                .build();
        addressBook.addAccount(account);
        assertTrue(addressBook.hasAccount(account));
    }

    @Test
    public void getAccountList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getAccountList().remove(0);
    }

    @Test
    public void equals() {
        addressBookWithPersons = new AddressBookBuilder().withPerson(AMY).withPerson(DYLAN).build();

        // same object
        assertTrue(addressBookWithPersons.equals(addressBookWithPersons));

        addressBookWithPersons.removeTag(new Tag(VALID_TAG_FRIEND));

        Person amyWithoutTags = new PersonBuilder(AMY).withTags().build();

        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(amyWithoutTags)
                .withPerson(DYLAN)
                .build();

        assertTrue(addressBookWithPersons.equals(expectedAddressBook));

        // different type
        assertFalse(addressBookWithPersons.equals(null));
        assertFalse(addressBookWithPersons.equals(0));

    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {

        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Reservation> reservations = FXCollections.observableArrayList();
        private final ObservableList<Account> accounts = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<Account> accounts,
                Collection<Reservation> reservations) {
            this.persons.setAll(persons);
            this.accounts.setAll(accounts);
            this.reservations.setAll(reservations);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Account> getAccountList() {
            return accounts;
        }

        @Override
        public ObservableList<Reservation> getReservationList() {
            return reservations;
        }

    }

}
