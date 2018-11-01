package seedu.restaurant.ui.reservation;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.restaurant.commons.core.LogsCenter;
import seedu.restaurant.commons.events.ui.JumpToListRequestEvent;
import seedu.restaurant.commons.events.ui.reservation.ReservationPanelSelectionChangedEvent;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.ui.UiPart;

/**
 * Panel containing the list of reservations.
 */
public class ReservationListPanel extends UiPart<Region> {

    private static final String FXML = "ReservationListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ReservationListPanel.class);

    @FXML
    private ListView<Reservation> reservationListView;

    public ReservationListPanel(ObservableList<Reservation> reservationList) {
        super(FXML);
        setConnections(reservationList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Reservation> reservationList) {
        reservationListView.setItems(reservationList);
        reservationListView.setCellFactory(listView -> new ReservationListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        reservationListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in reservation list panel changed to : '" + newValue + "'");
                        raise(new ReservationPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code ReservationCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            reservationListView.scrollTo(index);
            reservationListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Reservation} using a {@code ReservationCard}.
     */
    private class ReservationListViewCell extends ListCell<Reservation> {

        @Override
        protected void updateItem(Reservation reservation, boolean empty) {
            super.updateItem(reservation, empty);

            if (empty || reservation == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ReservationCard(reservation, getIndex() + 1).getRoot());
            }
        }
    }
}
