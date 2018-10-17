package seedu.address.commons.core.session;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.accounts.AccountBuilder;

public class UserSessionTest {

    @Test
    public void session_isAuthenticated() {
        // check equality using the same base
        UserSession.login(new AccountBuilder().build());
        assertTrue(UserSession.isAuthenticated());
        assertNotNull(UserSession.getUsername());
    }

    @Test
    public void session_isNotAuthenticated() {
        // check equality using the same base
        UserSession.login(new AccountBuilder().build());
        assertTrue(UserSession.isAuthenticated());
        assertNotNull(UserSession.getUsername());

        UserSession.logout();
        assertFalse(UserSession.isAuthenticated());
        assertNull(UserSession.getUsername());
    }
}
