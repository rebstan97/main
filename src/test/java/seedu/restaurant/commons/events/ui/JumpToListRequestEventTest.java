package seedu.restaurant.commons.events.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.BaseEvent;

public class JumpToListRequestEventTest {

    @Test
    public void createEvent_success() {
        BaseEvent event = new JumpToListRequestEvent(Index.fromOneBased(1));
        assertEquals("JumpToListRequestEvent", event.toString());
    }
}