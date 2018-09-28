package seedu.address.testutil.accounts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.accounts.Account;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalAccounts {

    public static final Account DEFAULT_ADMIN_ACCOUNT = new AccountBuilder().build();
    public static final Account DEMO_ONE = new AccountBuilder().withUsername("demo1").withPassword("pw1").build();

    private TypicalAccounts() {} // prevents instantiation

    public static List<Account> getTypicalAccounts() {
        return new ArrayList<>(Arrays.asList(DEFAULT_ADMIN_ACCOUNT, DEMO_ONE));
    }
}
