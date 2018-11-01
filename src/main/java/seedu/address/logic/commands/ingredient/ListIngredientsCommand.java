package seedu.address.logic.commands.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INGREDIENTS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DisplayIngredientListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all ingredients in the restaurant book to the user.
 */
public class ListIngredientsCommand extends Command {

    public static final String COMMAND_WORD = "list-ingredients";

    public static final String COMMAND_ALIAS = "list-ing";

    public static final String MESSAGE_SUCCESS = "Listed all ingredients";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredIngredientList(PREDICATE_SHOW_ALL_INGREDIENTS);
        EventsCenter.getInstance().post(new DisplayIngredientListRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
