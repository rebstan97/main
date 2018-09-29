package seedu.address.model.accounts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_DEMO_ONE;
import static seedu.address.testutil.accounts.TypicalAccounts.DEFAULT_ADMIN_ACCOUNT;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_ONE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.accounts.AccountBuilder;

public class AccountTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void check_toString() {
        Account adminAccount = new AccountBuilder(DEFAULT_ADMIN_ACCOUNT).build();

        // same username
        assertEquals(adminAccount.toString(), DEFAULT_ADMIN_ACCOUNT.getUsername().toString());

        // different username
        assertNotEquals(adminAccount.toString(), DEMO_ONE.getUsername().toString());
    }

    @Test
    public void equals() {
        // same values -> returns true
        Account adminAccount = new AccountBuilder(DEFAULT_ADMIN_ACCOUNT).build();
        assertEquals(DEFAULT_ADMIN_ACCOUNT, adminAccount);

        // same object -> returns true
        assertEquals(DEFAULT_ADMIN_ACCOUNT, DEFAULT_ADMIN_ACCOUNT);

        // null -> returns false
        assertNotEquals(null, DEFAULT_ADMIN_ACCOUNT);

        // different type -> returns false
        assertNotEquals(5, DEFAULT_ADMIN_ACCOUNT);

        // different account -> returns false
        Account demoAccount = new AccountBuilder(DEMO_ONE).build();
        assertNotEquals(DEFAULT_ADMIN_ACCOUNT, demoAccount);

        // different username -> returns false
        Account editedAdminAccount =
                new AccountBuilder(DEFAULT_ADMIN_ACCOUNT).withUsername(VALID_USERNAME_DEMO_ONE).build();
        assertNotEquals(DEFAULT_ADMIN_ACCOUNT, editedAdminAccount);
    }

    @Test
    public void hash_code() {
        Account adminAccount = new AccountBuilder(DEFAULT_ADMIN_ACCOUNT).build();

        assertEquals(adminAccount.hashCode(), adminAccount.hashCode());

        Account demoAccount = new AccountBuilder(DEMO_ONE).build();

        assertNotEquals(adminAccount.hashCode(), demoAccount.hashCode());
    }
}
