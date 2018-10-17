package seedu.address.logic.commands.ingredients;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.exceptions.IngredientNotFoundException;

/**
 * Deletes an ingredient identified using its displayed index or name from the address book.
 */
public class DeleteIngredientCommand extends Command {

    public static final String COMMAND_WORD = "delete-ingredient";

    public static final String COMMAND_ALIAS = "delete-ing";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the ingredient identified by the index number used in the displayed person list or its name.\n"
            + "Parameters: "
            + "[INDEX (must be a positive integer)] "
            + "[NAME] \n"
            + "Examples: " + COMMAND_WORD + " 1, " + COMMAND_WORD + " Chicken Thigh";

    public static final String MESSAGE_DELETE_INGREDIENT_SUCCESS = "Deleted Ingredient: %1$s";

    private final Index targetIndex;

    private final IngredientName targetName;

    public DeleteIngredientCommand() {
        this.targetIndex = null;
        this.targetName = null;
    }

    public DeleteIngredientCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
    }

    public DeleteIngredientCommand(IngredientName targetName) {
        this.targetName = targetName;
        this.targetIndex = null;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        CommandResult result = new CommandResult("");

        if (this.targetIndex != null && this.targetName == null) {
            result = executeDeleteByIndex(model);
        }

        if (this.targetName != null && this.targetIndex == null) {
            result = executeDeleteByName(model, targetName);
        }

        return result;

    }

    /**
     * Deletes an ingredient with the given index and returns the result of the command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return The result of the delete by index command
     * @throws CommandException If an error occurs during command execution.
     */
    private CommandResult executeDeleteByIndex(Model model) throws CommandException {
        List<Ingredient> lastShownList = model.getFilteredIngredientList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
        }

        Ingredient ingredientToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteIngredient(ingredientToDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_INGREDIENT_SUCCESS, ingredientToDelete));
    }

    /**
     * Deletes an ingredient with the given name and returns the result of the command.
     *
     * @param model {@code Model} which the command should operate on.
     * @param ingredientName {@code IngredientName} which the command should operate on.
     * @return The result of the delete by name command
     * @throws CommandException If an error occurs during command execution.
     */
    private CommandResult executeDeleteByName(Model model, IngredientName ingredientName)
            throws CommandException {
        try {
            Ingredient ingredientToDelete = model.findIngredient(ingredientName);
            model.deleteIngredient(ingredientToDelete);
            model.commitAddressBook();
            return new CommandResult(String.format(MESSAGE_DELETE_INGREDIENT_SUCCESS, ingredientToDelete));
        } catch (IngredientNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INGREDIENT_NAME_NOT_FOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteIngredientCommand // instanceof handles nulls
                    && targetIndex.equals(((DeleteIngredientCommand) other).targetIndex)); // state check
    }
}
