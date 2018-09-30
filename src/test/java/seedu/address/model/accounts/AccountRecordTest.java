package seedu.address.model.accounts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_DEMO_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TEST;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.DYLAN;
import static seedu.address.testutil.accounts.TypicalAccounts.DEFAULT_ADMIN_ACCOUNT;
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
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.accounts.exceptions.AccountNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;
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
        Account account = new AccountBuilder(DEFAULT_ADMIN_ACCOUNT)
                .withPassword(VALID_PASSWORD_DEMO_ONE)
                .build();
        List<Account> accounts = Arrays.asList(DEFAULT_ADMIN_ACCOUNT, account);
        AccountRecordStub newData = new AccountRecordStub(accounts);

        thrown.expect(DuplicatePersonException.class);
        accountRecord.resetData(newData);
    }

    @Test
    public void hasAccount_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        accountRecord.hasAccount(null);
    }

    @Test
    public void updateAccount_notFound_throwsAccountNotFoundException() {
        Account account = new AccountBuilder(DEFAULT_ADMIN_ACCOUNT).build();
        accountRecord.addAccount(account);
        thrown.expect(AccountNotFoundException.class);
        accountRecord.updateAccount(DEMO_ONE, DEMO_ONE);
    }

    @Test
    public void hasAccount_accountNotInAccountRecord_returnsFalse() {
        assertFalse(accountRecord.hasAccount(DEFAULT_ADMIN_ACCOUNT));
    }

    @Test
    public void hasAccount_accountInAddressBook_returnsTrue() {
        accountRecord.addAccount(DEFAULT_ADMIN_ACCOUNT);
        assertTrue(accountRecord.hasAccount(DEFAULT_ADMIN_ACCOUNT));
    }

    @Test
    public void hasAccount_accountWithSamePasswordInAccountRecord_returnsTrue() {
        accountRecord.addAccount(DEFAULT_ADMIN_ACCOUNT);
        Account accountWithSamePassword = new AccountBuilder(DEMO_ONE)
                .withPassword(DEFAULT_ADMIN_ACCOUNT.getPassword().toString())
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
