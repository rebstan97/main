package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.model.accounts.Account;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Account> PREDICATE_SHOW_ALL_ACCOUNTS = unused -> true;
    Predicate<Ingredient> PREDICATE_SHOW_ALL_INGREDIENTS = unused -> true;

    /**
     * Clears existing backing model and replaces with the provided new data for AddressBook.
     */
    void resetData(ReadOnlyAddressBook newData);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    //=========== API for Persons =============================================================

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person. The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person. {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}. {@code target} must exist in the address
     * book. The person identity of {@code editedPerson} must not be the same as another existing person in the address
     * book.
     *
     * @param target person to be updated.
     * @param editedPerson updated person.
     */
    void updatePerson(Person target, Person editedPerson);

    /**
     * Removes the given {@code tag} from all {@code Person}
     *
     * @param tag to be removed.
     */
    void removeTag(Tag tag);

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of {@code
     * versionedAddressBook}
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    //=========== API for Redo/Undo =============================================================

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

    //=========== API for Accounts =============================================================

    /**
     * Adds the given account. {@code account} must not already exist in the account storage.
     *
     * @param account to be added.
     */
    void addAccount(Account account);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasAccount(Account account);

    /**
     * Deletes the given account. The person must exist in the address book.
     *
     * @param account to be removed.
     */
    void removeAccount(Account account);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}. {@code target} must exist in the address
     * book. The person identity of {@code editedPerson} must not be the same as another existing person in the address
     * book.
     *
     * @param target account to be updated.
     * @param editedAccount updated account.
     */
    void updateAccount(Account target, Account editedAccount);

    ObservableList<Account> getFilteredAccountList();

    void updateFilteredAccountList(Predicate<Account> predicate);

    //=============== API for Ingredient ===============

    /**
     * Returns true if an ingredient with the same identity as {@code ingredient} exists in the address book.
     */
    boolean hasIngredient(Ingredient ingredient);

    /**
     * Deletes the given ingredient. The ingredient must exist in the address book.
     */
    void deleteIngredient(Person target);

    /**
     * Adds the given ingredient. {@code ingredient} must not already exist in the address book.
     */
    void addIngredient(Ingredient ingredient);

    /**
     * Replaces the given ingredient {@code target} with {@code editedIngredient}. {@code target} must exist in
     * the address book. The ingredient identity of {@code editedIngredient} must not be the same as another
     * existing ingredient in the address book.
     */
    void updateIngredient(Ingredient target, Ingredient editedIngredient);

    /**
     * Returns an unmodifiable view of the filtered ingredient list
     */
    ObservableList<Person> getFilteredIngredientList();

    /**
     * Updates the filter of the filtered ingredient list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredIngredientList(Predicate<Ingredient> predicate);

}
