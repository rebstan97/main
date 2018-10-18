package seedu.address.logic.commands.accounts;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.session.UserSession;
import seedu.address.commons.events.ui.LogoutEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Logs the user into an existing toLogin, and create a {@link UserSession}.
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logout of the system. "
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "You have been logged out.";
    public static final String MESSAGE_NOT_AUTHENTICATED = "You are not logged in.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!UserSession.isAuthenticated()) {
            throw new CommandException(MESSAGE_NOT_AUTHENTICATED);
        }

        EventsCenter.getInstance().post(new LogoutEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
