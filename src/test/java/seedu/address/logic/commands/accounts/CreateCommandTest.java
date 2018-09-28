package seedu.address.logic.commands.accounts;

import static seedu.address.testutil.accounts.AccountBuilder.DEFAULT_PASSWORD;
import static seedu.address.testutil.accounts.AccountBuilder.DEFAULT_USERNAME;
import static seedu.address.testutil.accounts.TypicalAccounts.DEFAULT_ADMIN_ACCOUNT;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.accounts.Account;
import seedu.address.testutil.accounts.AccountBuilder;

public class CreateCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager();

    @Test
    public void execute_throwsException() throws CommandException {
        thrown.expect(CommandException.class);
        thrown.expectMessage(DEFAULT_USERNAME + ", " + DEFAULT_PASSWORD);
        Account account = new AccountBuilder(DEFAULT_ADMIN_ACCOUNT).build();
        new CreateCommand(account).execute(model, commandHistory);
    }
}
