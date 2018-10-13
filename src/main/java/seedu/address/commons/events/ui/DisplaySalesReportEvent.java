package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.salesrecord.SalesRecord;

/**
 * An event requesting to display the sales report of a specified date
 */
public class DisplaySalesReportEvent extends BaseEvent {

    private ObservableList<SalesRecord> salesReportToDisplay;

    public DisplaySalesReportEvent(ObservableList<SalesRecord> salesReportToDisplay) {
        this.salesReportToDisplay = salesReportToDisplay;
    }

    public ObservableList<SalesRecord> getSalesReportToDisplay() {
        return salesReportToDisplay;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
