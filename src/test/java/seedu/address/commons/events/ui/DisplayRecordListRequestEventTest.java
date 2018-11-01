package seedu.address.commons.events.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.events.BaseEvent;

public class DisplayRecordListRequestEventTest {
    @Test
    public void createEvent_success() {
        BaseEvent event = new DisplayRecordListRequestEvent();
        assertEquals("DisplayRecordListRequestEvent", event.toString());
    }
}
