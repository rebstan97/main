package seedu.address.logic.commands.ingredients;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DisplayIngredientListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ingredient.Ingredient;

/**
 * Lists all ingredients in the restaurant book to the user.
 */
public class LowStockCommand extends Command {

    public static final String COMMAND_WORD = "low-stock";

    public static final String MESSAGE_SUCCESS = "Listed all ingredients that are low in stock count";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        Predicate<Ingredient> predicate = ingredient ->
                ingredient.getNumUnits().getNumberOfUnits() < ingredient.getMinimum().getMinimumUnit();
        model.updateFilteredIngredientList(predicate);
        EventsCenter.getInstance().post(new DisplayIngredientListRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
