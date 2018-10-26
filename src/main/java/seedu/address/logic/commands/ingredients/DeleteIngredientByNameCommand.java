package seedu.address.logic.commands.ingredients;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.DisplayIngredientListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.exceptions.IngredientNotFoundException;

/**
 * Deletes an ingredient identified using its name from the restaurant book.
 */
public class DeleteIngredientByNameCommand extends DeleteIngredientCommand {

    private final IngredientName targetName;

    public DeleteIngredientByNameCommand(IngredientName targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            Ingredient ingredientToDelete = model.findIngredient(targetName);
            model.deleteIngredient(ingredientToDelete);
            model.commitAddressBook();
            EventsCenter.getInstance().post(new DisplayIngredientListRequestEvent());
            return new CommandResult(String.format(MESSAGE_DELETE_INGREDIENT_SUCCESS, ingredientToDelete));
        } catch (IngredientNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INGREDIENT_NAME_NOT_FOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteIngredientByNameCommand // instanceof handles nulls
                    && (targetName.equals(((DeleteIngredientByNameCommand) other).targetName))); // state check
    }
}
