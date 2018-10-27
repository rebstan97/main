package seedu.address.logic.commands.accounts;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.LoginEvent;
import seedu.address.commons.events.ui.LogoutEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.accounts.ChangePasswordCommand.EditAccountDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.accounts.Account;
import seedu.address.testutil.accounts.AccountBuilder;
import seedu.address.testutil.accounts.EditAccountDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class ChangePasswordCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // Logs into the first account in the account list with a hashed password
        EventsCenter.getInstance().post(new LoginEvent(model.getFilteredAccountList().get(0)));
    }

    @After
    public void reset() {
        EventsCenter.getInstance().post(new LogoutEvent());
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Account editedAccount = new AccountBuilder().build();
        ChangePasswordCommand changePasswordCommand = new ChangePasswordCommand(
                new EditAccountDescriptorBuilder(editedAccount).build());

        String expectedMessage = String.format(ChangePasswordCommand.MESSAGE_SUCCESS, editedAccount);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateAccount(model.getFilteredAccountList().get(0), editedAccount);
        expectedModel.commitAddressBook();

        assertCommandSuccess(changePasswordCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        ChangePasswordCommand changePasswordCommand = new ChangePasswordCommand(new EditAccountDescriptor());
        Account editedAccount = model.getFilteredAccountList().get(0);

        String expectedMessage = String.format(ChangePasswordCommand.MESSAGE_SUCCESS, editedAccount);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(changePasswordCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        Account accountInFilteredList = model.getFilteredAccountList().get(0);
        Account editedAccount = new AccountBuilder(accountInFilteredList).withPassword("newpassword").build();
        ChangePasswordCommand changePasswordCommand = new ChangePasswordCommand(
                new EditAccountDescriptorBuilder().withPassword("newpassword").build());

        String expectedMessage = String.format(ChangePasswordCommand.MESSAGE_SUCCESS, editedAccount);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateAccount(model.getFilteredAccountList().get(0), editedAccount);
        expectedModel.commitAddressBook();

        assertCommandSuccess(changePasswordCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
