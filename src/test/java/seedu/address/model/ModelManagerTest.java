package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_TAG_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_TAG_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TEST;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ACCOUNTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECORDS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.DYLAN;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_ADMIN;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_ONE;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_TWO;
import static seedu.address.testutil.ingredients.TypicalIngredients.AVOCADO;
import static seedu.address.testutil.ingredients.TypicalIngredients.BROCCOLI;
import static seedu.address.testutil.menu.TypicalItems.APPLE_JUICE;
import static seedu.address.testutil.menu.TypicalItems.BURGER;
import static seedu.address.testutil.menu.TypicalItems.CHEESE_BURGER;
import static seedu.address.testutil.menu.TypicalItems.FRIES;
import static seedu.address.testutil.menu.TypicalItems.ITEM_DEFAULT;
import static seedu.address.testutil.salesrecords.TypicalRecords.RECORD_DEFAULT;
import static seedu.address.testutil.salesrecords.TypicalRecords.RECORD_ONE;
import static seedu.address.testutil.salesrecords.TypicalRecords.RECORD_TWO;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.accounts.Account;
import seedu.address.model.accounts.exceptions.AccountNotFoundException;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.NumUnits;
import seedu.address.model.ingredient.exceptions.IngredientNotEnoughException;
import seedu.address.model.ingredient.exceptions.IngredientNotFoundException;
import seedu.address.model.menu.Item;
import seedu.address.model.menu.exceptions.ItemNotFoundException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.salesrecord.SalesRecord;
import seedu.address.model.salesrecord.exceptions.SalesRecordNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.accounts.AccountBuilder;
import seedu.address.testutil.ingredients.IngredientBuilder;
import seedu.address.testutil.menu.ItemBuilder;
import seedu.address.testutil.salesrecords.RecordBuilder;

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
    public void getFilteredRecordList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredRecordList().remove(0);
    }

    @Test
    public void getFilteredAccountList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredAccountList().remove(0);
    }

    @Test
    public void getFilteredItemList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredItemList().remove(0);
    }

    @Test
    public void removeTag_noSuchTag_addressBookUnmodified() {
        addressBookWithPersons = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager unmodifiedModelManager = new ModelManager(addressBookWithPersons, userPrefs);
        unmodifiedModelManager.removeTag(new Tag(VALID_TAG_TEST));

        ModelManager expectedModelManager = new ModelManager(addressBookWithPersons, userPrefs);

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
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modifiedModelManager = new ModelManager(addressBookWithPersons, userPrefs);
        modifiedModelManager.removeTag(new Tag(VALID_TAG_FRIEND));

        Person amyWithoutTags = new PersonBuilder(AMY).withTags().build();

        ModelManager expectedModelManager = new ModelManager(addressBookWithPersons, userPrefs);
        expectedModelManager.updatePerson(AMY, amyWithoutTags);

        assertEquals(modifiedModelManager, expectedModelManager);
    }

    @Test
    public void hasRecord_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasRecord(null);
    }
    @Test
    public void hasRecord_recordNotInSalesBook_returnsFalse() {
        assertFalse(modelManager.hasRecord(RECORD_DEFAULT));
    }
    @Test
    public void hasRecord_recordInSalesBook_returnsTrue() {
        modelManager.addRecord(RECORD_DEFAULT);
        assertTrue(modelManager.hasRecord(RECORD_DEFAULT));
    }
    @Test
    public void getRecordList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getAddressBook().getRecordList().remove(0);
    }
    @Test
    public void deleteRecord_recordNotInSalesBook_throwsRecordsNotFoundException() {
        thrown.expect(SalesRecordNotFoundException.class);
        modelManager.deleteRecord(RECORD_DEFAULT);
    }
    @Test
    public void deleteRecord_recordInSalesBook_returnTrue() {
        modelManager.addRecord(RECORD_DEFAULT);
        assertTrue(modelManager.hasRecord(RECORD_DEFAULT));
        modelManager.deleteRecord(RECORD_DEFAULT);
        assertFalse(modelManager.hasRecord(RECORD_DEFAULT));
    }
    @Test
    public void updateRecord_recordNotInSalesBook_throwsRecordNotFoundException() {
        thrown.expect(SalesRecordNotFoundException.class);
        modelManager.updateRecord(RECORD_DEFAULT, RECORD_ONE);
    }
    @Test
    public void updateRecord_recordInSalesBook_returnTrue() {
        modelManager.addRecord(RECORD_DEFAULT);
        SalesRecord record = new RecordBuilder(RECORD_ONE).build();
        modelManager.updateRecord(RECORD_DEFAULT, record);
        assertFalse(modelManager.hasRecord(RECORD_DEFAULT));
        assertTrue(modelManager.hasRecord(record));
    }
    @Test
    public void getSalesReport_nullDate_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.getSalesReport(null);
    }

    @Test
    public void hasAccount_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasAccount(null);
    }

    @Test
    public void hasAccount_accountNotInAccountRecord_returnsFalse() {
        assertFalse(modelManager.hasAccount(DEMO_ADMIN));
    }

    @Test
    public void hasAccount_accountInAccountRecord_returnsTrue() {
        modelManager.addAccount(DEMO_ADMIN);
        assertTrue(modelManager.hasAccount(DEMO_ADMIN));
    }

    @Test
    public void getAccountList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getAddressBook().getAccountList().remove(0);
    }

    @Test
    public void removeAccount_accountNotInAccountRecord_throwsAccountNotFoundException() {
        thrown.expect(AccountNotFoundException.class);
        modelManager.removeAccount(DEMO_ADMIN);
    }

    @Test
    public void removeAccount_accountInAccountRecord_returnTrue() {
        modelManager.addAccount(DEMO_ADMIN);
        assertTrue(modelManager.hasAccount(DEMO_ADMIN));

        modelManager.removeAccount(DEMO_ADMIN);
        assertFalse(modelManager.hasAccount(DEMO_ADMIN));
    }

    @Test
    public void updateAccount_accountNotInAccountRecord_throwsAccountNotFoundException() {
        thrown.expect(AccountNotFoundException.class);
        modelManager.updateAccount(DEMO_ADMIN, DEMO_ONE);
    }

    @Test
    public void updateAccount_accountInAccountRecord_returnTrue() {
        modelManager.addAccount(DEMO_ADMIN);
        Account account = new AccountBuilder(DEMO_ONE).build();

        modelManager.updateAccount(DEMO_ADMIN, account);
        assertFalse(modelManager.hasAccount(DEMO_ADMIN));
        assertTrue(modelManager.hasAccount(account));
    }

    @Test
    public void hasIngredient_nullIngredient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasIngredient(null);
    }

    @Test
    public void hasIngredient_ingredientNotIngredientList_returnsFalse() {
        assertFalse(modelManager.hasIngredient(AVOCADO));
    }

    @Test
    public void hasIngredient_ingredientInIngredientList_returnsTrue() {
        modelManager.addIngredient(AVOCADO);
        assertTrue(modelManager.hasIngredient(AVOCADO));
    }

    @Test
    public void deleteIngredient_ingredientNotInIngredientList_throwsItemNotFoundException() {
        thrown.expect(IngredientNotFoundException.class);
        modelManager.deleteIngredient(BROCCOLI);
    }
    @Test
    public void deleteIngredient_ingredientInIngredientList_returnTrue() {
        modelManager.addIngredient(BROCCOLI);
        assertTrue(modelManager.hasIngredient(BROCCOLI));
        modelManager.deleteIngredient(BROCCOLI);
        assertFalse(modelManager.hasIngredient(BROCCOLI));
    }
    @Test
    public void updateIngredient_ingredientNotInIngredientList_throwsIngredientNotFoundException() {
        thrown.expect(IngredientNotFoundException.class);
        modelManager.updateIngredient(BROCCOLI, AVOCADO);
    }
    @Test
    public void updateIngredient_ingredientInIngredientList_returnTrue() {
        modelManager.addIngredient(BROCCOLI);
        Ingredient ingredient = new IngredientBuilder(AVOCADO).build();
        modelManager.updateIngredient(BROCCOLI, AVOCADO);
        assertFalse(modelManager.hasIngredient(BROCCOLI));
        assertTrue(modelManager.hasIngredient(ingredient));
    }

    @Test
    public void findIngredient_ingredientNotInIngredientList_throwsIngredientNotFoundException() {
        thrown.expect(IngredientNotFoundException.class);
        modelManager.findIngredient(BROCCOLI.getName());
    }

    @Test
    public void findIngredient_ingredientInIngredientList_assertEquals() {
        modelManager.addIngredient(AVOCADO);
        Ingredient ingredient = modelManager.findIngredient(AVOCADO.getName());
        assertEquals(AVOCADO, ingredient);
    }

    @Test
    public void stockUpIngredient_ingredientNotInIngredientList_throwsIngredientNotFoundException() {
        HashMap<IngredientName, Integer> recipe = new HashMap<>();
        recipe.put(BROCCOLI.getName(), 12);
        thrown.expect(IngredientNotFoundException.class);
        modelManager.stockUpIngredients(recipe);
    }

    @Test
    public void stockUpIngredient_ingredientInIngredientList_assertEquals() {
        Ingredient ingredient = new IngredientBuilder(BROCCOLI).withNumUnits(20).build();
        modelManager.addIngredient(ingredient);
        HashMap<IngredientName, Integer> recipe = new HashMap<>();
        recipe.put(BROCCOLI.getName(), 12);
        modelManager.stockUpIngredients(recipe);
        Ingredient stockedUpIngredient = modelManager.findIngredient(BROCCOLI.getName());
        NumUnits updatedNumUnits = stockedUpIngredient.getNumUnits();
        assertEquals(new NumUnits(32), updatedNumUnits);
    }

    @Test
    public void consumeIngredient_ingredientNotInIngredientList_throwsIngredientNotFoundException() {
        HashMap<IngredientName, Integer> recipe = new HashMap<>();
        recipe.put(BROCCOLI.getName(), 12);
        thrown.expect(IngredientNotFoundException.class);
        modelManager.consumeIngredients(recipe);
    }

    @Test
    public void consumeIngredient_ingredientNotEnough_throwsIngredientNotEnoughException() {
        Ingredient ingredient = new IngredientBuilder(BROCCOLI).withNumUnits(2).build();
        modelManager.addIngredient(ingredient);
        HashMap<IngredientName, Integer> recipe = new HashMap<>();
        recipe.put(BROCCOLI.getName(), 12);
        thrown.expect(IngredientNotEnoughException.class);
        modelManager.consumeIngredients(recipe);
    }

    @Test
    public void consumeIngredient_ingredientInIngredientList_assertEquals() {
        Ingredient ingredient = new IngredientBuilder(BROCCOLI).withNumUnits(20).build();
        modelManager.addIngredient(ingredient);
        HashMap<IngredientName, Integer> recipe = new HashMap<>();
        recipe.put(BROCCOLI.getName(), 2);
        modelManager.consumeIngredients(recipe);
        Ingredient consumedIngredient = modelManager.findIngredient(BROCCOLI.getName());
        NumUnits updatedNumUnits = consumedIngredient.getNumUnits();
        assertEquals(new NumUnits(18), updatedNumUnits);
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
    public void deleteItem_itemNotInMenu_throwsItemNotFoundException() {
        thrown.expect(ItemNotFoundException.class);
        modelManager.deleteItem(ITEM_DEFAULT);
    }
    @Test
    public void deleteItem_itemInMenu_returnTrue() {
        modelManager.addItem(ITEM_DEFAULT);
        assertTrue(modelManager.hasItem(ITEM_DEFAULT));
        modelManager.deleteItem(ITEM_DEFAULT);
        assertFalse(modelManager.hasItem(ITEM_DEFAULT));
    }
    @Test
    public void updateItem_itemNotInMenu_throwsItemNotFoundException() {
        thrown.expect(ItemNotFoundException.class);
        modelManager.updateItem(ITEM_DEFAULT, APPLE_JUICE);
    }
    @Test
    public void updateItem_itemInMenu_returnTrue() {
        modelManager.addItem(ITEM_DEFAULT);
        Item item = new ItemBuilder(APPLE_JUICE).build();
        modelManager.updateItem(ITEM_DEFAULT, item);
        assertFalse(modelManager.hasItem(ITEM_DEFAULT));
        assertTrue(modelManager.hasItem(item));
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
        AddressBook addressBook = new AddressBookBuilder()
                .withPerson(ALICE)
                .withPerson(BENSON)
                .withRecord(RECORD_ONE)
                .withRecord(RECORD_TWO)
                .withAccount(DEMO_ONE)
                .withAccount(DEMO_TWO)
                .build();
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
        String[] keywords = ALICE.getName().toString().split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        modelManager.updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORDS);
        modelManager.updateFilteredAccountList(PREDICATE_SHOW_ALL_ACCOUNTS);

        //TODO: Test updateFilteredAccountList

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
