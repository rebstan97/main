package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a user has just logged out of system.
 */
public class LogoutEvent extends BaseEvent {

    public LogoutEvent() {}

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
