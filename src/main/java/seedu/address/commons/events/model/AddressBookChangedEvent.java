package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Indicates the AddressBook in the model has changed
 */
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public AddressBookChangedEvent(ReadOnlyAddressBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of accounts " + data.getAccountList().size()
                + ", number of items " + data.getItemList().size()
                + ", number of reservations " + data.getReservationList().size()
                + ", number of sales record " + data.getRecordList().size()
                + ", number of ingredients " + data.getIngredientList().size();
    }
}
