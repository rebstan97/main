package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.salesrecord.Date;

/**
 * An event requesting to display the sales report of a specified date
 */
public class DisplaySalesReportEvent extends BaseEvent {

    private Date dateOfSalesReportToDisplay;

    public DisplaySalesReportEvent(Date dateOfSalesReportToDisplay) {
        this.dateOfSalesReportToDisplay = dateOfSalesReportToDisplay;
    }

    public Date getDateOfSalesReportToDisplay() {
        return dateOfSalesReportToDisplay;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
