package seedu.address.model.accounts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_DEMO_ONE;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_ADMIN;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_ONE;
import static seedu.address.testutil.accounts.TypicalAccounts.getTypicalAccountRecord;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.accounts.exceptions.AccountNotFoundException;
import seedu.address.model.accounts.exceptions.DuplicateAccountException;
import seedu.address.testutil.accounts.AccountBuilder;

public class AccountRecordTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AccountRecord accountRecord = new AccountRecord();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), accountRecord.getAccountList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        accountRecord.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAccountRecord_replacesData() {
        AccountRecord newData = getTypicalAccountRecord();
        accountRecord.resetData(newData);
        assertEquals(newData, accountRecord);
    }

    @Test
    public void resetData_withDuplicateAccounts_throwsDuplicateAccountException() {
        // same username but different password
        Account account = new AccountBuilder(DEMO_ADMIN)
                .withPassword(VALID_PASSWORD_DEMO_ONE)
                .build();
        List<Account> accounts = Arrays.asList(DEMO_ADMIN, account);
        AccountRecordStub newData = new AccountRecordStub(accounts);

        thrown.expect(DuplicateAccountException.class);
        accountRecord.resetData(newData);
    }

    @Test
    public void hasAccount_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        accountRecord.hasAccount(null);
    }

    @Test
    public void updateAccount_notFound_throwsAccountNotFoundException() {
        Account account = new AccountBuilder(DEMO_ADMIN).build();
        accountRecord.addAccount(account);
        thrown.expect(AccountNotFoundException.class);
        accountRecord.updateAccount(DEMO_ONE, DEMO_ONE);
    }

    @Test
    public void hasAccount_accountNotInAccountRecord_returnsFalse() {
        assertFalse(accountRecord.hasAccount(DEMO_ADMIN));
    }

    @Test
    public void hasAccount_accountInAddressBook_returnsTrue() {
        accountRecord.addAccount(DEMO_ADMIN);
        assertTrue(accountRecord.hasAccount(DEMO_ADMIN));
    }

    @Test
    public void hasAccount_accountWithSamePasswordInAccountRecord_returnsTrue() {
        accountRecord.addAccount(DEMO_ADMIN);
        Account accountWithSamePassword = new AccountBuilder(DEMO_ONE)
                .withPassword(DEMO_ADMIN.getPassword().toString())
                .build();
        accountRecord.addAccount(accountWithSamePassword);
        assertTrue(accountRecord.hasAccount(accountWithSamePassword));
    }

    @Test
    public void getAccountList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        accountRecord.getAccountList().remove(0);
    }

    /**
     * A stub ReadOnlyAccountRecord whose accounts list can violate interface constraints.
     */
    private static class AccountRecordStub implements ReadOnlyAccountRecord {

        private final ObservableList<Account> accounts = FXCollections.observableArrayList();

        AccountRecordStub(Collection<Account> accounts) {
            this.accounts.setAll(accounts);
        }

        @Override
        public ObservableList<Account> getAccountList() {
            return accounts;
        }
    }
}
