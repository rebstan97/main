package seedu.address.model.accounts;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an account record
 */
public interface ReadOnlyAccountRecord {

    /**
     * Returns an unmodifiable view of the accounts list. This list will not contain any duplicate accounts.
     */
    ObservableList<Account> getAccountList();

}
