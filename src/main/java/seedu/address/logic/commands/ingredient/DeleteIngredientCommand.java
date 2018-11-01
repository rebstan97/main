package seedu.address.logic.commands.ingredient;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Deletes an ingredient identified using its displayed index or name from the restaurant book.
 */
public abstract class DeleteIngredientCommand extends Command {

    public static final String COMMAND_WORD = "delete-ingredient";

    public static final String COMMAND_ALIAS = "delete-ing";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the ingredient identified by the index number used in the displayed ingredient list or its "
            + "name.\n"
            + "Parameters: "
            + "[INDEX (must be a positive integer)] "
            + "[NAME] \n"
            + "Examples: " + COMMAND_WORD + " 1, " + COMMAND_WORD + " Chicken Thigh";

    public static final String MESSAGE_DELETE_INGREDIENT_SUCCESS = "Deleted Ingredient: %1$s";

    @Override
    public abstract CommandResult execute(Model model, CommandHistory history) throws CommandException;

    @Override
    public abstract boolean equals(Object other);

}
