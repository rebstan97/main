package seedu.address.commons.events.ui;

import seedu.address.commons.core.session.UserSession;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.account.Account;
import seedu.address.model.account.Username;

/**
 * Indicates a user has just logged into the system.
 */
public class LoginEvent extends BaseEvent {

    public final Username username;

    public LoginEvent(Account account) {
        UserSession.create(account);
        this.username = account.getUsername();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
