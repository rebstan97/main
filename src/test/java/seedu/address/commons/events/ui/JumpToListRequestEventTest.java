package seedu.address.commons.events.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

public class JumpToListRequestEventTest {

    @Test
    public void createEvent_success() {
        BaseEvent event = new JumpToListRequestEvent(Index.fromOneBased(1));
        assertEquals("JumpToListRequestEvent", event.toString());
    }
}
