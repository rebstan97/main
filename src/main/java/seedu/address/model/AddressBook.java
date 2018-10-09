package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.accounts.Account;
import seedu.address.model.accounts.UniqueAccountList;
import seedu.address.model.menu.Item;
import seedu.address.model.menu.UniqueItemList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueAccountList accounts;
    private final UniqueItemList items;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        accounts = new UniqueAccountList();
        items = new UniqueItemList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}. {@code persons} must not contain duplicate
     * persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setAccounts(newData.getAccountList());
        setItems(newData.getItemList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book. The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}. {@code target} must exist in the
     * address book. The person identity of {@code editedPerson} must not be the same as another existing person in the
     * address book.
     */
    public void updatePerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}. {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Removes {@code tag} from {@code person} in this {@code AddressBook}.
     *
     * @param person whose tag is being removed.
     * @param tag to be removed.
     */
    private void removeTagForPerson(Person person, Tag tag) {
        Set<Tag> tags = new HashSet<>(person.getTags());

        if (!tags.remove(tag)) {
            return;
        }

        Person newPerson = new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                person.getRemark(), tags);
        updatePerson(person, newPerson);
    }

    /**
     * Removes {@code tag} from all {@code person} in this {@code AddressBook}.
     *
     * @param tag to be removed.
     */
    public void removeTag(Tag tag) {
        persons.forEach(person -> removeTagForPerson(person, tag));
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    //// account-level operations

    /**
     * Replaces the contents of the person list with {@code persons}. {@code persons} must not contain duplicate
     * persons.
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts.setAccounts(accounts);
    }

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasAccount(Account account) {
        return accounts.contains(account);
    }

    /**
     * Adds a account to the account record. The account must not already exist in the account record.
     */
    public void addAccount(Account account) {
        // hash password before
        //account.getPassword().hash(account.getUsername().toString());
        accounts.add(account);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}. {@code target} must exist in the
     * address book. The person identity of {@code editedPerson} must not be the same as another existing person in the
     * address book.
     */
    public void updateAccount(Account target, Account editedAccount) {
        accounts.update(target, editedAccount);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}. {@code key} must exist in the address book.
     */
    public void removeAccount(Account key) {
        accounts.remove(key);
    }

    @Override
    public ObservableList<Account> getAccountList() {
        return accounts.asUnmodifiableObservableList();
    }

    // Menu Management
    /**
     * Replaces the contents of the person list with {@code persons}. {@code persons} must not contain duplicate
     * persons.
     */
    public void setItems(List<Item> items) {
        this.items.setItems(items);
    }

    /**
     * Returns true if a item with the same identity as {@code item} exists in the menu.
     */
    public boolean hasItem(Item item) {
        requireNonNull(item);
        return items.contains(item);
    }

    /**
     * Adds an item to the menu. The item must not already exist in the menu.
     */
    public void addItem(Item i) {
        items.add(i);
    }

    /**
     * Replaces the given item {@code target} in the list with {@code editedItem}. {@code target} must exist in the
     * menu. The item identity of {@code editedItem} must not be the same as another existing item in the menu.
     */
    public void updateItem(Item target, Item editedItem) {
        requireNonNull(editedItem);

        items.setItem(target, editedItem);
    }

    /**
     * Removes {@code key} from this {@code Menu}. {@code key} must exist in the menu.
     */
    public void removeItem(Item key) {
        items.remove(key);
    }

    /**
     * Removes {@code tag} from {@code item} in this {@code Menu}.
     *
     * @param item whose tag is being removed.
     * @param tag to be removed.
     */
    private void removeTagForItem(Item item, Tag tag) {
        Set<Tag> tags = new HashSet<>(item.getTags());

        if (!tags.remove(tag)) {
            return;
        }

        Item newItem = new Item(item.getName(), item.getPrice(), item.getRemark(), tags);
        updateItem(item, newItem);
    }

    /**
     * Removes {@code tag} from all {@code item} in this {@code AddressBook}.
     *
     * @param tag to be removed.
     */
    public void removeTagForMenu(Tag tag) {
        items.forEach(item -> removeTagForItem(item, tag));
    }

    @Override
    public ObservableList<Item> getItemList() {
        return items.asUnmodifiableObservableList();
    }

    //// util methods

    @Override
    public String toString() {
        return String.valueOf(persons.asUnmodifiableObservableList().size()) + " persons\n"
                + accounts.asUnmodifiableObservableList().size() + " accounts"
                + items.asUnmodifiableObservableList().size() + " items";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        return persons.equals(((AddressBook) other).persons)
                && accounts.equals(((AddressBook) other).accounts)
                && items.equals(((AddressBook) other).items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, accounts, items);
    }
}
