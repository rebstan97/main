package seedu.address.logic.commands.accounts;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ACCOUNTS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.accounts.DisplayAccountListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all items in the menu to the user.
 */
public class ListAccountsCommand extends Command {

    public static final String COMMAND_WORD = "list-accounts";

    public static final String COMMAND_ALIAS = "la";

    public static final String MESSAGE_SUCCESS = "Listed all accounts";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredAccountList(PREDICATE_SHOW_ALL_ACCOUNTS);
        EventsCenter.getInstance().post(new DisplayAccountListRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
