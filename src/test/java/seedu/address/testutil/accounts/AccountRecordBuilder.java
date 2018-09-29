package seedu.address.testutil.accounts;

import seedu.address.model.accounts.Account;
import seedu.address.model.accounts.AccountRecord;

/**
 * A utility class to help with building AccountRecord objects. Example usage: <br> {@code AccountRecordBuilder arb =
 * new AccountRecordBuilder().withAccount(Account).build();}
 */
public class AccountRecordBuilder {

    private AccountRecord accountRecord;

    public AccountRecordBuilder() {
        accountRecord = new AccountRecord();
    }

    public AccountRecordBuilder(AccountRecord addressBook) {
        this.accountRecord = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AccountRecordBuilder withAccount(Account account) {
        accountRecord.addAccount(account);
        return this;
    }

    public AccountRecord build() {
        return accountRecord;
    }
}
