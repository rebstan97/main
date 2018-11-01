package seedu.address.commons.events.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.ui.reservation.DisplayReservationListRequestEvent;

public class DisplayReservationListRequestEventTest {
    @Test
    public void createEvent_success() {
        BaseEvent event = new DisplayReservationListRequestEvent();
        assertEquals("DisplayReservationListRequestEvent", event.toString());
    }
}
