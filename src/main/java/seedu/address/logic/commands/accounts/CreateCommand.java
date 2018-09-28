package seedu.address.logic.commands.accounts;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.accounts.Account;

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
            + PREFIX_PASSWORD + "1122qq";

    public static final String MESSAGE_SUCCESS = "New account created: %1$s";
    public static final String MESSAGE_DUPLICATE_USERNAME = "This username already exists";

    private final Account toCreate;

    public CreateCommand(Account account) {
        requireNonNull(account);
        toCreate = account;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(toCreate.getUsername() + ", " + toCreate.getPassword());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateCommand // instanceof handles nulls
                && toCreate.equals(((CreateCommand) other).toCreate));
    }
}
