package seedu.address.commons.core.session;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.accounts.Account;
import seedu.address.model.accounts.Username;

/**
 * Since this is a local desktop application which may or may not work with internet connection, we assume the
 * multiplicity of 0...1 user will be logged in at any time. Thus, it does not make sense to create a list of user
 * sessions as it is impossible in this project's context.
 */
public class UserSession {

    private static final Logger logger = LogsCenter.getLogger(UserSession.class);

    private static boolean isAuthenticated = false;
    private static Username username;

    /**
     * Stores this {@link Account} info as part of this session.
     *
     * @param account logged in for this session.
     */
    public static void login(Account account) {
        isAuthenticated = true;
        username = account.getUsername();
    }

    /**
     * Logs out of this account which releases this session.
     */
    public static void logout() {
        if (isAuthenticated) {
            isAuthenticated = false;
            username = null;
        }
    }

    /**
     * Checks if this session exists.
     *
     * @return true if this session exists. Otherwise, false.
     */
    public static boolean isAuthenticated() {
        return isAuthenticated;
    }

    /**
     * Gets the username that is logged in for this session.
     *
     * @return a {@link Username} object.
     */
    public static Username getUsername() {
        return username;
    }
}
