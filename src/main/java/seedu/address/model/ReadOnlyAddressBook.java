package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.menu.Item;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    // Menu management
    /**
     * Returns an unmodifiable view of the items list.
     * This list will not contain any duplicate items.
     */
    ObservableList<Item> getItemList();

}
