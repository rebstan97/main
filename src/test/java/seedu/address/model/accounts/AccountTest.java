package seedu.address.model.accounts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_DEMO_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_DEMO_TWO;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.accounts.TypicalAccounts.DEFAULT_ADMIN_ACCOUNT;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_ONE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.accounts.AccountBuilder;

public class AccountTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() {
        // same values -> returns true
        Account adminAccount = new AccountBuilder(DEFAULT_ADMIN_ACCOUNT).build();
        assertTrue(DEFAULT_ADMIN_ACCOUNT.equals(adminAccount));

        // same object -> returns true
        assertTrue(DEFAULT_ADMIN_ACCOUNT.equals(DEFAULT_ADMIN_ACCOUNT));

        // null -> returns false
        assertFalse(DEFAULT_ADMIN_ACCOUNT.equals(null));

        // different type -> returns false
        assertFalse(DEFAULT_ADMIN_ACCOUNT.equals(5));

        // different account -> returns false
        Account demoAccount = new AccountBuilder(DEMO_ONE).build();
        assertFalse(DEFAULT_ADMIN_ACCOUNT.equals(demoAccount));

        // different username -> returns false
        Account editedAdminAccount =
                new AccountBuilder(DEFAULT_ADMIN_ACCOUNT).withUsername(VALID_USERNAME_DEMO_ONE).build();
        assertFalse(DEFAULT_ADMIN_ACCOUNT.equals(editedAdminAccount));
    }
}
