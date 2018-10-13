package seedu.address.ui.sales;

import java.util.logging.Logger;

import javax.swing.text.TableView;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.salesrecord.SalesRecord;
import seedu.address.ui.UiPart;

/**
 * Controller for a sales report
 */
public class SalesReportWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(SalesReportWindow.class);
    private static final String FXML = "SalesReportWindow.fxml";

    private ObservableList<SalesRecord> salesReport;

    @FXML
    private TableView table ;
    @FXML
    private Label date;

    /**
     * Creates a new SalesReportWindow
     *
     * @param root Stage to use as the root of the SalesReportWindow.
     * @param salesReport ObservableList of sales records that make up the salesReport
     */
    public SalesReportWindow(Stage root, ObservableList<SalesRecord> salesReport) {
        super(FXML, root);
        this.salesReport = salesReport;
    }

    /**
     * Creates a new SalesReportWindow.
     */
    public SalesReportWindow(ObservableList<SalesRecord> salesReport) {
        this(new Stage(), salesReport);
    }

    /**
     * Shows the SalesReportWindow window.
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
        logger.fine("Showing sales report.");
        getRoot().show();
    }

    /**
     * Returns true if the SalesReportWindow is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }
}

