package seedu.address.logic.commands.ingredients;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INGREDIENTS;

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
 * Edits the details of an existing ingredient in the restaurant book.
 */
public class EditIngredientByNameCommand extends EditIngredientCommand {

    private final IngredientName targetName;
    private final EditIngredientDescriptor editIngredientDescriptor;

    /**
     * @param name of the ingredient in the filtered ingredient list to edit
     * @param editIngredientDescriptor details to edit the ingredient with
     */
    public EditIngredientByNameCommand(IngredientName name, EditIngredientDescriptor editIngredientDescriptor) {
        requireNonNull(name);
        requireNonNull(editIngredientDescriptor);

        this.targetName = name;
        this.editIngredientDescriptor = new EditIngredientDescriptor(editIngredientDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Ingredient ingredientToEdit;
        Ingredient editedIngredient;

        try {
            ingredientToEdit = model.findIngredient(targetName);
            editedIngredient = createEditedIngredient(ingredientToEdit, editIngredientDescriptor);
        } catch (IngredientNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INGREDIENT_NAME_NOT_FOUND);
        }

        if (!ingredientToEdit.isSameIngredient(editedIngredient) && model.hasIngredient(editedIngredient)) {
            throw new CommandException(MESSAGE_DUPLICATE_INGREDIENT);
        }

        model.updateIngredient(ingredientToEdit, editedIngredient);
        model.updateFilteredIngredientList(PREDICATE_SHOW_ALL_INGREDIENTS);
        model.commitRestaurantBook();
        EventsCenter.getInstance().post(new DisplayIngredientListRequestEvent());
        return new CommandResult(String.format(MESSAGE_EDIT_INGREDIENT_SUCCESS, editedIngredient));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditIngredientByNameCommand)) {
            return false;
        }

        // state check
        EditIngredientByNameCommand e = (EditIngredientByNameCommand) other;
        return targetName.equals(e.targetName)
                && editIngredientDescriptor.equals(e.editIngredientDescriptor);
    }

}
