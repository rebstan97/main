package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_TAG_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_TAG_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TEST;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.DYLAN;
import static seedu.address.testutil.menu.TypicalItems.APPLE_JUICE;
import static seedu.address.testutil.menu.TypicalItems.BURGER;
import static seedu.address.testutil.menu.TypicalItems.CHEESE_BURGER;
import static seedu.address.testutil.menu.TypicalItems.FRIES;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.menu.Item;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.menu.ItemBuilder;

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

        ModelManager unmodifiedModelManager = new ModelManager(addressBookWithPersons, new UserPrefs());
        unmodifiedModelManager.removeTag(new Tag(VALID_TAG_TEST));

        ModelManager expectedModelManager = new ModelManager(addressBookWithPersons, new UserPrefs());

        assertEquals(unmodifiedModelManager, expectedModelManager);
    }

    @Test
    public void removeTag_fromAllPersons_addressBookModified() {
        addressBookWithPersons = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modifiedModelManager = new ModelManager(addressBookWithPersons, userPrefs);
        modifiedModelManager.removeTag(new Tag(VALID_TAG_FRIEND));

        Person amyWithoutTags = new PersonBuilder(AMY).withTags().build();
        Person bobWithoutFriendTag = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();

        ModelManager expectedModelManager = new ModelManager(addressBookWithPersons, userPrefs);
        // Cannot init a new AddressBook due to difference in addressBookStateList
        expectedModelManager.updatePerson(AMY, amyWithoutTags);
        expectedModelManager.updatePerson(BOB, bobWithoutFriendTag);

        assertEquals(modifiedModelManager, expectedModelManager);
    }

    @Test
    public void removeTag_fromOnePerson_addressBookModified() {
        addressBookWithPersons = new AddressBookBuilder().withPerson(AMY).withPerson(DYLAN).build();

        ModelManager modifiedModelManager = new ModelManager(addressBookWithPersons, new UserPrefs());
        modifiedModelManager.removeTag(new Tag(VALID_TAG_FRIEND));

        Person amyWithoutTags = new PersonBuilder(AMY).withTags().build();

        ModelManager expectedModelManager = new ModelManager(addressBookWithPersons, new UserPrefs());
        expectedModelManager.updatePerson(AMY, amyWithoutTags);

        assertEquals(modifiedModelManager, expectedModelManager);
    }

    // Menu Management
    @Test
    public void hasItem_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasItem(null);
    }

    @Test
    public void hasItem_itemNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasItem(APPLE_JUICE));
    }

    @Test
    public void hasItem_itemInAddressBook_returnsTrue() {
        modelManager.addItem(APPLE_JUICE);
        assertTrue(modelManager.hasItem(APPLE_JUICE));
    }

    @Test
    public void getFilteredItemList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredItemList().remove(0);
    }

    @Test
    public void removeTagForMenu_noSuchTag_addressBookUnmodified() {
        addressBookWithPersons = new AddressBookBuilder().withItem(APPLE_JUICE).withItem(BURGER).build();

        ModelManager unmodifiedModelManager = new ModelManager(addressBookWithPersons, new UserPrefs());
        unmodifiedModelManager.removeTagForMenu(new Tag(VALID_TAG_TEST));

        ModelManager expectedModelManager = new ModelManager(addressBookWithPersons, new UserPrefs());

        assertEquals(unmodifiedModelManager, expectedModelManager);
    }

    @Test
    public void removeTagForMenu_fromAllItems_addressBookModified() {
        addressBookWithPersons = new AddressBookBuilder().withItem(CHEESE_BURGER).withItem(FRIES).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modifiedModelManager = new ModelManager(addressBookWithPersons, userPrefs);
        modifiedModelManager.removeTagForMenu(new Tag(VALID_ITEM_TAG_CHEESE));

        Item cheeseWithoutCheeseTags = new ItemBuilder(CHEESE_BURGER).withTags(VALID_ITEM_TAG_BURGER).build();
        Item friesWithoutTags = new ItemBuilder(FRIES).withTags().build();

        ModelManager expectedModelManager = new ModelManager(addressBookWithPersons, userPrefs);
        // Cannot init a new AddressBook due to difference in addressBookStateList
        expectedModelManager.updateItem(CHEESE_BURGER, cheeseWithoutCheeseTags);
        expectedModelManager.updateItem(FRIES, friesWithoutTags);

        assertEquals(modifiedModelManager, expectedModelManager);
    }

    @Test
    public void removeTagForMenu_fromOneItem_addressBookModified() {
        addressBookWithPersons = new AddressBookBuilder().withItem(FRIES).withItem(BURGER).build();

        ModelManager modifiedModelManager = new ModelManager(addressBookWithPersons, new UserPrefs());
        modifiedModelManager.removeTagForMenu(new Tag(VALID_ITEM_TAG_CHEESE));

        Item friesWithoutTags = new ItemBuilder(FRIES).withTags().build();

        ModelManager expectedModelManager = new ModelManager(addressBookWithPersons, new UserPrefs());
        expectedModelManager.updateItem(FRIES, friesWithoutTags);

        assertEquals(modifiedModelManager, expectedModelManager);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
