package seedu.address.model.accounts;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AccountRecord implements ReadOnlyAccountRecord {

    private final UniqueAccountList accounts;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        accounts = new UniqueAccountList();
    }

    public AccountRecord() {}

    /**
     * Creates an AccountRecord using the Accounts in the {@code toBeCopied}
     */
    public AccountRecord(ReadOnlyAccountRecord toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the person list with {@code persons}. {@code persons} must not contain duplicate
     * persons.
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts.setAccounts(accounts);
    }
    /**
     * Resets the existing data of this {@code AccountRecord} with {@code newData}.
     */
    public void resetData(ReadOnlyAccountRecord newData) {
        requireNonNull(newData);

        setAccounts(newData.getAccountList());
    }

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasAccount(Account account) {
        return accounts.contains(account);
    }

    /**
     * Adds a account to the account record. The account must not already exist in the account record.
     */
    public void addAccount(Account account) {
        accounts.add(account);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}. {@code target} must exist in the
     * address book. The person identity of {@code editedPerson} must not be the same as another existing person in the
     * address book.
     */
    public void updateAccount(Account target, Account editedAccount) {
        accounts.update(target, editedAccount);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}. {@code key} must exist in the address book.
     */
    public void removeAccount(Account key) {
        accounts.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return accounts.asUnmodifiableObservableList().size() + " accounts";
    }

    @Override
    public ObservableList<Account> getAccountList() {
        return accounts.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AccountRecord // instanceof handles nulls
                && accounts.equals(((AccountRecord) other).accounts));
    }

    @Override
    public int hashCode() {
        return accounts.hashCode();
    }
}
