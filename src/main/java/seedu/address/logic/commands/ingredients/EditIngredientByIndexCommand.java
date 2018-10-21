package seedu.address.logic.commands.ingredients;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INGREDIENTS;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.DisplayIngredientListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ingredient.Ingredient;

/**
 * Edits the details of an existing ingredient in the address book.
 */
public class EditIngredientByIndexCommand extends EditIngredientCommand {

    private final Index targetIndex;
    private final EditIngredientDescriptor editIngredientDescriptor;

    /**
     * @param index of the ingredient in the filtered ingredient list to edit
     * @param editIngredientDescriptor details to edit the ingredient with
     */
    public EditIngredientByIndexCommand(Index index, EditIngredientDescriptor editIngredientDescriptor) {
        requireNonNull(index);
        requireNonNull(editIngredientDescriptor);

        this.targetIndex = index;
        this.editIngredientDescriptor = new EditIngredientDescriptor(editIngredientDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Ingredient> lastShownList = model.getFilteredIngredientList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
        }

        Ingredient ingredientToEdit = lastShownList.get(targetIndex.getZeroBased());
        Ingredient editedIngredient = createEditedIngredient(ingredientToEdit, editIngredientDescriptor);

        if (!ingredientToEdit.isSameIngredient(editedIngredient) && model.hasIngredient(editedIngredient)) {
            throw new CommandException(MESSAGE_DUPLICATE_INGREDIENT);
        }

        model.updateIngredient(ingredientToEdit, editedIngredient);
        model.updateFilteredIngredientList(PREDICATE_SHOW_ALL_INGREDIENTS);
        model.commitAddressBook();
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
        if (!(other instanceof EditIngredientByIndexCommand)) {
            return false;
        }

        // state check
        EditIngredientByIndexCommand e = (EditIngredientByIndexCommand) other;
        return targetIndex.equals(e.targetIndex)
                && editIngredientDescriptor.equals(e.editIngredientDescriptor);
    }

}
