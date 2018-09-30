package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TEST;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.DYLAN;
import static seedu.address.testutil.accounts.TypicalAccounts.DEFAULT_ADMIN_ACCOUNT;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_ONE;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.accounts.Account;
import seedu.address.model.accounts.AccountRecord;
import seedu.address.model.accounts.exceptions.AccountNotFoundException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.accounts.AccountBuilder;

public class ModelManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();
    private AddressBook addressBookWithPersons = null;

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    @Test
    public void removeTag_noSuchTag_addressBookUnmodified() {
        addressBookWithPersons = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();
        AccountRecord accountRecord = new AccountRecord();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager unmodifiedModelManager = new ModelManager(addressBookWithPersons, accountRecord, userPrefs);
        unmodifiedModelManager.removeTag(new Tag(VALID_TAG_TEST));

        ModelManager expectedModelManager = new ModelManager(addressBookWithPersons, accountRecord, userPrefs);

        assertEquals(unmodifiedModelManager, expectedModelManager);
    }

    @Test
    public void removeTag_fromAllPersons_addressBookModified() {
        addressBookWithPersons = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();
        AccountRecord accountRecord = new AccountRecord();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modifiedModelManager = new ModelManager(addressBookWithPersons, accountRecord, userPrefs);
        modifiedModelManager.removeTag(new Tag(VALID_TAG_FRIEND));

        Person amyWithoutTags = new PersonBuilder(AMY).withTags().build();
        Person bobWithoutFriendTag = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();

        ModelManager expectedModelManager = new ModelManager(addressBookWithPersons, accountRecord, userPrefs);
        // Cannot init a new AddressBook due to difference in addressBookStateList
        expectedModelManager.updatePerson(AMY, amyWithoutTags);
        expectedModelManager.updatePerson(BOB, bobWithoutFriendTag);

        assertEquals(modifiedModelManager, expectedModelManager);
    }

    @Test
    public void removeTag_fromOnePerson_addressBookModified() {
        addressBookWithPersons = new AddressBookBuilder().withPerson(AMY).withPerson(DYLAN).build();
        AccountRecord accountRecord = new AccountRecord();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modifiedModelManager = new ModelManager(addressBookWithPersons, accountRecord, userPrefs);
        modifiedModelManager.removeTag(new Tag(VALID_TAG_FRIEND));

        Person amyWithoutTags = new PersonBuilder(AMY).withTags().build();

        ModelManager expectedModelManager = new ModelManager(addressBookWithPersons, accountRecord, userPrefs);
        expectedModelManager.updatePerson(AMY, amyWithoutTags);

        assertEquals(modifiedModelManager, expectedModelManager);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        AccountRecord accountRecord = new AccountRecord();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, accountRecord, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, accountRecord, userPrefs);
        assertEquals(modelManager, modelManagerCopy);

        // same object -> returns true
        assertEquals(modelManager, modelManager);

        // null -> returns false
        assertNotEquals(null, modelManager);

        // different types -> returns false
        assertNotEquals(5, modelManager);

        // different addressBook -> returns false
        assertNotEquals(modelManager, new ModelManager(differentAddressBook, accountRecord, userPrefs));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertNotEquals(modelManager, new ModelManager(addressBook, accountRecord, userPrefs));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertEquals(modelManager, new ModelManager(addressBook, accountRecord, differentUserPrefs));
    }

    @Test
    public void hasAccount_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasAccount(null);
    }

    @Test
    public void hasAccount_accountNotInAccountRecord_returnsFalse() {
        assertFalse(modelManager.hasAccount(DEFAULT_ADMIN_ACCOUNT));
    }

    @Test
    public void hasAccount_accountInAccountRecord_returnsTrue() {
        modelManager.addAccount(DEFAULT_ADMIN_ACCOUNT);
        assertTrue(modelManager.hasAccount(DEFAULT_ADMIN_ACCOUNT));
    }

    @Test
    public void getAccountList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getAccountRecord().getAccountList().remove(0);
    }

    @Test
    public void removeAccount_accountNotInAccountRecord_throwsAccountNotFoundException() {
        thrown.expect(AccountNotFoundException.class);
        modelManager.removeAccount(DEFAULT_ADMIN_ACCOUNT);
    }

    @Test
    public void removeAccount_accountInAccountRecord_returnTrue() {
        modelManager.addAccount(DEFAULT_ADMIN_ACCOUNT);
        assertTrue(modelManager.hasAccount(DEFAULT_ADMIN_ACCOUNT));

        modelManager.removeAccount(DEFAULT_ADMIN_ACCOUNT);
        assertFalse(modelManager.hasAccount(DEFAULT_ADMIN_ACCOUNT));
    }

    @Test
    public void updateAccount_accountNotInAccountRecord_throwsAccountNotFoundException() {
        thrown.expect(AccountNotFoundException.class);
        modelManager.updateAccount(DEFAULT_ADMIN_ACCOUNT, DEMO_ONE);
    }

    @Test
    public void updateAccount_accountInAccountRecord_returnTrue() {
        modelManager.addAccount(DEFAULT_ADMIN_ACCOUNT);
        Account account = new AccountBuilder(DEMO_ONE).build();

        modelManager.updateAccount(DEFAULT_ADMIN_ACCOUNT, account);
        assertFalse(modelManager.hasAccount(DEFAULT_ADMIN_ACCOUNT));
        assertTrue(modelManager.hasAccount(account));
    }
}
