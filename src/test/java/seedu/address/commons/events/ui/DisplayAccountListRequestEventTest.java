package seedu.address.commons.events.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.ui.accounts.DisplayAccountListRequestEvent;

public class DisplayAccountListRequestEventTest {
    @Test
    public void createEvent_success() {
        BaseEvent event = new DisplayAccountListRequestEvent();
        assertEquals("DisplayAccountListRequestEvent", event.toString());
    }
}
