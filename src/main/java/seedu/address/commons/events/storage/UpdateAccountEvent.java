package seedu.address.commons.events.storage;

import seedu.address.commons.core.session.UserSession;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.accounts.Account;
import seedu.address.model.accounts.Username;

/**
 * Indicates a user account has just been updated.
 */
public class UpdateAccountEvent extends BaseEvent {

    public final Username username;

    public UpdateAccountEvent(Account account) {
        UserSession.update(account);
        this.username = account.getUsername();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
