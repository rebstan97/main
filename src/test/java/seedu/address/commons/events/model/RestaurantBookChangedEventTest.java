package seedu.address.commons.events.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.RestaurantBook;
import seedu.address.model.util.SampleDataUtil;

public class RestaurantBookChangedEventTest {
    @Test
    public void createEvent_withEmptyRestaurantBook_success() {
        BaseEvent event = new RestaurantBookChangedEvent(new RestaurantBook());
        assertEquals("number of accounts 0, number of items 0, number of reservations 0, "
                + "number of sales record 0, " + "number of ingredients 0", event.toString());
    }

    @Test
    public void createEvent_withStockRestaurantBook_success() {
        BaseEvent event = new RestaurantBookChangedEvent(SampleDataUtil.getSampleRestaurantBook());
        assertEquals("number of accounts 1, number of items 0, number of reservations 0, "
                + "number of sales record 0, number of ingredients 0", event.toString());
    }
}
