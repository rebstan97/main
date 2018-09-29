package seedu.address.testutil.accounts;

import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_DEMO_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_DEMO_THREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_DEMO_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_DEMO_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_DEMO_THREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_DEMO_TWO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.accounts.Account;
import seedu.address.model.accounts.AccountRecord;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalAccounts {

    public static final Account DEFAULT_ADMIN_ACCOUNT = new AccountBuilder().build();
    public static final Account DEMO_ONE =
            new AccountBuilder().withUsername(VALID_USERNAME_DEMO_ONE).withPassword(VALID_PASSWORD_DEMO_ONE).build();
    public static final Account DEMO_TWO =
            new AccountBuilder().withUsername(VALID_USERNAME_DEMO_TWO).withPassword(VALID_PASSWORD_DEMO_TWO).build();
    public static final Account DEMO_THREE =
            new AccountBuilder().withUsername(VALID_USERNAME_DEMO_THREE).withPassword(VALID_PASSWORD_DEMO_THREE).build();

    private TypicalAccounts() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AccountRecord getTypicalAccountRecord() {
        AccountRecord accountRecord = new AccountRecord();
        for (Account account : getTypicalAccounts()) {
            accountRecord.addAccount(account);
        }
        return accountRecord;
    }

    public static List<Account> getTypicalAccounts() {
        return new ArrayList<>(Arrays.asList(DEFAULT_ADMIN_ACCOUNT, DEMO_ONE, DEMO_TWO));
    }
}
