package seedu.address.ui.account;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.account.Account;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of account.
 */
public class AccountListPanel extends UiPart<Region> {

    private static final String FXML = "AccountListPanel.fxml";

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
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Account} using a {@code AccountCard}.
     */
    private class AccountListViewCell extends ListCell<Account> {

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
