package seedu.address.logic.commands.menu;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECIPE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.menu.Item;
import seedu.address.model.menu.Recipe;

/**
 * Adds a recipe to a item in the menu.
 */
public class RecipeItemCommand extends Command {
    public static final String COMMAND_WORD = "recipe-item";
    public static final String COMMAND_ALIAS = "ri";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the recipe for a item identified "
            + "by the index number used in the displayed item list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_RECIPE + "RECIPE\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_RECIPE + "Boil water over high heat and reduce the heat to medium-low, "
            + "pour in the vinegar and 2 teaspoons of salt and egg.";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added recipe to Item: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed recipe from Item: %1$s";

    private final Index index;
    private final Recipe recipe;

    /**
     * @param index of the item in the filtered item list to edit
     * @param recipe the recipe of the item
     */
    public RecipeItemCommand(Index index, Recipe recipe) {
        requireAllNonNull(index, recipe);

        this.index = index;
        this.recipe = recipe;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Item> lastShownList = model.getFilteredItemList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item itemToEdit = lastShownList.get(index.getZeroBased());
        Item editedItem = new Item(itemToEdit.getName(), itemToEdit.getPrice(), recipe, itemToEdit.getTags());

        model.updateItem(itemToEdit, editedItem);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitAddressBook();

        return new CommandResult(generateSuccessMessage(editedItem));
    }
    /**
     * Generates a command execution success message based on whether the recipe is added to or removed from
     * {@code itemToEdit}.
     */
    private String generateSuccessMessage(Item itemToEdit) {
        String message = !recipe.toString().isEmpty() ? MESSAGE_ADD_REMARK_SUCCESS : MESSAGE_DELETE_REMARK_SUCCESS;
        return String.format(message, itemToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof RecipeItemCommand)) {
            return false;
        }
        // state check
        RecipeItemCommand e = (RecipeItemCommand) other;
        return index.equals(e.index)
                && recipe.equals(e.recipe);
    }
}
