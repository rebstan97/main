package seedu.address.commons.events.ui.accounts;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to display ItemListPanel.
 */
public class DisplayAccountListRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
