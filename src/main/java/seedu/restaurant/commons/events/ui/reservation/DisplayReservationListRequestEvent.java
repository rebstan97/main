package seedu.restaurant.commons.events.ui.reservation;

import seedu.restaurant.commons.events.BaseEvent;

/**
 * An event requesting to display ReservationListPanel.
 */
public class DisplayReservationListRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
