package seedu.address.logic.commands.accounts;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CreateCommandTest {
    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_throwsException() throws CommandException {
        thrown.expect(CommandException.class);
        new CreateCommand().execute(model, commandHistory);
    }
}
