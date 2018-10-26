package seedu.address.logic.commands.accounts;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_PASSWORD;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ACCOUNTS;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.accounts.Account;
import seedu.address.model.accounts.Password;
import seedu.address.model.accounts.Username;

/**
 * Change the password an existing {@code Account}.
 */
public class ChangePasswordCommand extends Command {

    public static final String COMMAND_WORD = "change-password";
    public static final String COMMAND_ALIAS = "cp";

    //TODO: Add support for admin to modify account by specifying username
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the password of an existing account. "
            + "Parameters: "
            + PREFIX_ID + "USERNAME "
            + PREFIX_NEW_PASSWORD + "NEW_PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NEW_PASSWORD + "1122qq";

    public static final String MESSAGE_SUCCESS = "Successfully updated the account %s";
    public static final String MESSAGE_ACCOUNT_NOT_FOUND = "The account does not exist";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided";
    public static final String MESSAGE_DUPLICATE_ACCOUNT = "This account already exists in the restaurant book";

    private final Account account;
    private final EditAccountDescriptor editAccountDescriptor;

    /**
     * @param account of the staff in the filtered account list to edit.
     * @param editAccountDescriptor details to edit the account with.
     */
    public ChangePasswordCommand(Account account, EditAccountDescriptor editAccountDescriptor) {
        requireNonNull(account);
        requireNonNull(editAccountDescriptor);

        this.account = account;
        this.editAccountDescriptor = new EditAccountDescriptor(editAccountDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.hasAccount(account)) {
            throw new CommandException(MESSAGE_ACCOUNT_NOT_FOUND);
        }

        Account accountToEdit = account;
        Account editedAccount = createEditedAccount(accountToEdit, editAccountDescriptor);

        if (!accountToEdit.isSameUsername(editedAccount) && model.hasAccount(editedAccount)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACCOUNT);
        }

        model.updateAccount(accountToEdit, editedAccount);
        model.updateFilteredAccountList(PREDICATE_SHOW_ALL_ACCOUNTS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedAccount));
    }

    /**
     * Creates and returns a {@code Account} with the details of {@code accountToEdit} edited with {@code
     * editAccountDescriptor}.
     */
    private static Account createEditedAccount(Account accountToEdit, EditAccountDescriptor editAccountDescriptor) {
        assert accountToEdit != null;

        Username updatedUsername = editAccountDescriptor.getUsername().orElse(accountToEdit.getUsername());
        Password updatedPassword = editAccountDescriptor.getPassword().orElse(accountToEdit.getPassword());

        return new Account(updatedUsername, updatedPassword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangePasswordCommand // instanceof handles nulls
                && account.equals(((ChangePasswordCommand) other).account));
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the corresponding field value
     * of the person.
     */
    public static class EditAccountDescriptor {

        private Username username;
        private Password password;

        public EditAccountDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditAccountDescriptor(EditAccountDescriptor toCopy) {
            setUsername(toCopy.username);
            setPassword(toCopy.password);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(username, password);
        }

        public void setUsername(Username username) {
            this.username = username;
        }

        public Optional<Username> getUsername() {
            return Optional.ofNullable(username);
        }

        public void setPassword(Password password) {
            this.password = password;
        }

        public Optional<Password> getPassword() {
            return Optional.ofNullable(password);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAccountDescriptor)) {
                return false;
            }

            // state check
            EditAccountDescriptor e = (EditAccountDescriptor) other;

            return getUsername().equals(e.getUsername())
                    && getPassword().equals(e.getPassword());
        }
    }
}
