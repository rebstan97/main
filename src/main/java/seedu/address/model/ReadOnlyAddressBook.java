package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.accounts.Account;
import seedu.address.model.person.Person;
import seedu.address.model.reservation.Reservation;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list. This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the accounts list. This list will not contain any duplicate accounts.
     */
    ObservableList<Account> getAccountList();

    // Reservation Management
    /**
     * Returns an unmodifiable view of the reservations list.
     * This list will not contain any duplicate reservations.
     */
    ObservableList<Reservation> getReservationList();

}
