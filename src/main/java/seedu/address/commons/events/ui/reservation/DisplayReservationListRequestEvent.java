package seedu.address.commons.events.ui.reservation;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to display ReservationListPanel.
 */
public class DisplayReservationListRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
