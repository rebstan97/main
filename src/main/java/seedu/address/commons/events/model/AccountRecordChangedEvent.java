package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.accounts.AccountRecord;

/**
 * Indicates the account record in the model has changed
 */
public class AccountRecordChangedEvent extends BaseEvent {

    public final AccountRecord data;

    public AccountRecordChangedEvent(AccountRecord data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
