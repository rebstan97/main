package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.accounts.Account;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.person.Person;
import seedu.address.model.salesrecord.SalesRecord;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list. This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the records list. This list will not contain any duplicate records.
     */
    ObservableList<SalesRecord> getRecordList();

    /**
     * Returns an unmodifiable view of the accounts list. This list will not contain any duplicate accounts.
     */
    ObservableList<Account> getAccountList();

    /**
     * Returns an unmodifiable view of the ingredients list. This list will not contain any duplicate ingredients.
     */
    ObservableList<Ingredient> getIngredientList();
}
