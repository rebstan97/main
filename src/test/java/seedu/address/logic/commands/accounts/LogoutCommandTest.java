package seedu.address.logic.commands.accounts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
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

public class LogoutCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Before
    public void setUp() throws CommandException {
        // Logs in before every test case, if not logged in yet
        if (!UserSession.isAuthenticated()) {
            Account validAccount = new AccountBuilder().build();
            new LoginCommand(validAccount).execute(model, commandHistory);
        }
    }

    @Test
    public void session_isAuthenticated() {
        assertTrue(UserSession.isAuthenticated());
    }

    @Test
    public void execute() throws CommandException {
        CommandResult commandResult = new LogoutCommand().execute(model, commandHistory);
        assertEquals(LogoutCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        assertFalse(UserSession.isAuthenticated());
        assertNull(UserSession.getAccount());
    }

    @Test
    public void execute_logout_logoutAgain() throws CommandException {
        thrown.expect(CommandException.class);
        thrown.expectMessage(LogoutCommand.MESSAGE_NOT_AUTHENTICATED);

        new LogoutCommand().execute(model, commandHistory);
        new LogoutCommand().execute(model, commandHistory);
    }
}
