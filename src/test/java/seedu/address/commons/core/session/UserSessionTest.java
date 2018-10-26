package seedu.address.commons.core.session;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import seedu.address.testutil.accounts.AccountBuilder;

public class UserSessionTest {

    @Before
    public void setUp() {
        if (!UserSession.isAuthenticated()) {
            UserSession.login(new AccountBuilder().build());
        }
    }

    @AfterClass
    public static void tearDown() {
        // Make sure to unset the session, in case the last test case does logs out
        UserSession.logout();
    }

    @Test
    public void session_isAuthenticated() {
        assertTrue(UserSession.isAuthenticated());
        assertNotNull(UserSession.getAccount());
    }

    @Test
    public void session_isNotAuthenticated() {
        UserSession.logout();
        assertFalse(UserSession.isAuthenticated());
        assertNull(UserSession.getAccount());
    }
}
