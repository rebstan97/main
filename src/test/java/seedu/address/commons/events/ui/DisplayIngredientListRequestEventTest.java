package seedu.address.commons.events.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.events.BaseEvent;

public class DisplayIngredientListRequestEventTest {
    @Test
    public void createEvent_success() {
        BaseEvent event = new DisplayIngredientListRequestEvent();
        assertEquals("DisplayIngredientListRequestEvent", event.toString());
    }
}
