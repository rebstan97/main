package seedu.address.commons.events.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.events.BaseEvent;

public class DisplayItemListRequestEventTest {
    @Test
    public void createEvent_success() {
        BaseEvent event = new DisplayItemListRequestEvent();
        assertEquals("DisplayItemListRequestEvent", event.toString());
    }
}
