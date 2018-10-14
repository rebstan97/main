package seedu.address.logic.commands.accounts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.session.UserSession;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.accounts.Account;
import seedu.address.testutil.accounts.AccountBuilder;

public class LoginCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Before
    public void setUp() {
        // Logs out before every test case
        UserSession.logout();
    }

    @Test
    public void session_notAuthenticated() {
        assertFalse(UserSession.isAuthenticated());
    }

    @Test
    public void execute_validAccount_session_isAuthenticated() throws CommandException {
        Account validAccount = new AccountBuilder().build();
        CommandResult commandResult = new LoginCommand(validAccount).execute(model, commandHistory);

        assertEquals(String.format(LoginCommand.MESSAGE_SUCCESS, validAccount), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // Since logged in, session will be set
        assertTrue(UserSession.isAuthenticated());
        assertNotNull(UserSession.getUsername());
    }

    @Test
    public void execute_validAccount_session_isAuthenticated_loginAgain() throws CommandException {
        thrown.expect(CommandException.class);
        thrown.expectMessage(LoginCommand.MESSAGE_ALREADY_AUTHENTICATED);

        Account validAccount = new AccountBuilder().build();
        new LoginCommand(validAccount).execute(model, commandHistory);
        new LoginCommand(validAccount).execute(model, commandHistory);
    }

    @Test
    public void execute_invalidPassword() throws CommandException {
        Account invalidAccount = new AccountBuilder().withPassword("1122qq!@#123").build();
        CommandResult commandResult = new LoginCommand(invalidAccount).execute(model, commandHistory);

        assertEquals(LoginCommand.MESSAGE_WRONG_PASSWORD, commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_invalidAccount() throws CommandException {
        thrown.expect(CommandException.class);
        thrown.expectMessage(LoginCommand.MESSAGE_ACCOUNT_NOT_FOUND);

        Account invalidAccount = new AccountBuilder().withUsername("invalidusername").build();
        new LoginCommand(invalidAccount).execute(model, commandHistory);
    }
}
