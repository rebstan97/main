package seedu.address.ui.sales;

import java.util.logging.Logger;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.salesrecord.SalesRecord;
import seedu.address.model.salesrecord.SalesReport;
import seedu.address.ui.UiPart;

/**
 * Controller for a sales report window
 */
public class SalesReportWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(SalesReportWindow.class);
    private static final String FXML = "SalesReportWindow.fxml";

    private SalesReport salesReport;

    @FXML
    private TableView<SalesRecord> tableView;
//    @FXML
//    private Tabl
    @FXML
    private Label date;

    /**
     * Creates a new SalesReportWindow
     *
     * @param root Stage to use as the root of the SalesReportWindow.
     * @param salesReport the {@code SalesReport} to display in this window
     */
    public SalesReportWindow(Stage root, SalesReport salesReport) {
        super(FXML, root);
        this.salesReport = salesReport;
    }

    /**
     * Creates a new SalesReportWindow.
     */
    public SalesReportWindow(SalesReport salesReport) {
        this(new Stage(), salesReport);
    }

    /**
     * Shows the SalesReportWindow.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Displaying sales report.");
        getRoot().show();
    }

    /**
     * Returns true if the SalesReportWindow is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }
}

