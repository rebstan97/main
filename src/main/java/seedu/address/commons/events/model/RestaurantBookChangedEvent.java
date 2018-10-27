package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyRestaurantBook;

/**
 * Indicates the RestaurantBook in the model has changed
 */
public class RestaurantBookChangedEvent extends BaseEvent {

    public final ReadOnlyRestaurantBook data;

    public RestaurantBookChangedEvent(ReadOnlyRestaurantBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        //TODO: Display number of reservations, sales and ingredients as well?
        return "number of persons " + data.getPersonList().size()
                + ", number of accounts " + data.getAccountList().size()
                + ", number of items " + data.getItemList().size();
    }
}
