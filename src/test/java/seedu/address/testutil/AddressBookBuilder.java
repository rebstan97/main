package seedu.address.testutil;

import seedu.address.model.RestaurantBook;
import seedu.address.model.accounts.Account;
import seedu.address.model.menu.Item;
import seedu.address.model.person.Person;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.salesrecord.SalesRecord;

/**
 * A utility class to help with building RestaurantBook objects. Example usage: <br> {@code RestaurantBook ab = new
 * AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private RestaurantBook restaurantBook;

    public AddressBookBuilder() {
        restaurantBook = new RestaurantBook();
    }

    public AddressBookBuilder(RestaurantBook restaurantBook) {
        this.restaurantBook = restaurantBook;
    }

    /**
     * Adds a new {@code Person} to the {@code RestaurantBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        restaurantBook.addPerson(person);
        return this;
    }

    /**
     * Adds a new {@code Record} to the {@code RestaurantBook} that we are building.
     */
    public AddressBookBuilder withRecord(SalesRecord record) {
        restaurantBook.addRecord(record);
        return this;
    }

    /**
     * Adds a new {@code Account} to the {@code RestaurantBook} that we are building.
     */
    public AddressBookBuilder withAccount(Account account) {
        restaurantBook.addAccount(account);
        return this;
    }

    // Menu Management
    /**
     * Adds a new {@code Item} to the {@code RestaurantBook} that we are building.
     */
    public AddressBookBuilder withItem(Item item) {
        restaurantBook.addItem(item);
        return this;
    }

    /**
     * Adds a new {@code Reservation} to the {@code RestaurantBook} that we are building.
     */
    public AddressBookBuilder withReservation(Reservation reservation) {
        restaurantBook.addReservation(reservation);
        return this;
    }

    public RestaurantBook build() {
        return restaurantBook;
    }
}
