package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to display the sales report.
 */
public class DisplaySalesReportEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
