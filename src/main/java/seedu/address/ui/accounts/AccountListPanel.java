package seedu.address.ui.accounts;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.accounts.AccountPanelSelectionChangedEvent;
import seedu.address.model.accounts.Account;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of items.
 */
public class AccountListPanel extends UiPart<Region> {

    private static final String FXML = "AccountListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AccountListPanel.class);

    @FXML
    private ListView<Account> accountListView;

    public AccountListPanel(ObservableList<Account> accountList) {
        super(FXML);
        setConnections(accountList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Account> itemList) {
        accountListView.setItems(itemList);
        accountListView.setCellFactory(listView -> new AccountListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        accountListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in item list panel changed to : '" + newValue + "'");
                        raise(new AccountPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code ItemCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            accountListView.scrollTo(index);
            accountListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Item} using a {@code ItemCard}.
     */
    class AccountListViewCell extends ListCell<Account> {

        @Override
        protected void updateItem(Account account, boolean empty) {
            super.updateItem(account, empty);

            if (empty || account == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AccountCard(account, getIndex() + 1).getRoot());
            }
        }
    }
}
