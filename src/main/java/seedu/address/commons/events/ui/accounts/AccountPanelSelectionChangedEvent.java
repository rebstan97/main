package seedu.address.commons.events.ui.accounts;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.accounts.Account;

/**
 * Represents a selection change in the Account List Panel
 */
public class AccountPanelSelectionChangedEvent extends BaseEvent {


    private final Account newSelection;

    public AccountPanelSelectionChangedEvent(Account newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Account getNewSelection() {
        return newSelection;
    }
}
