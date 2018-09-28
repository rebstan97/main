package seedu.address.logic.commands.accounts;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds a new user account to the account file.
 */
public class CreateCommand extends Command {

    public static final String COMMAND_WORD = "create-acc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new user account. "
            + "Parameters: "
            + PREFIX_ID + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "azhikai "
            + PREFIX_PASSWORD + "p@55w04d";

    public static final String MESSAGE_SUCCESS = "New account created: %1$s";
    public static final String MESSAGE_DUPLICATE_USERNAME = "This username already exists";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException("test");
    }
}
