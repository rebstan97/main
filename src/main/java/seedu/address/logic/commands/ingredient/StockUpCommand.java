package seedu.address.logic.commands.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.ingredient.EditIngredientCommand.createEditedIngredient;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_NUM;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INGREDIENTS;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.DisplayIngredientListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.ingredient.EditIngredientCommand.EditIngredientDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.NumUnits;
import seedu.address.model.ingredient.exceptions.IngredientNotFoundException;

/**
 * Stocks up an ingredient in the restaurant book.
 */
public class StockUpCommand extends Command {

    public static final String COMMAND_WORD = "stockup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Stocks up an ingredient in the restaurant book "
            + "Parameters: "
            + PREFIX_INGREDIENT_NAME + "NAME... "
            + PREFIX_INGREDIENT_NUM + "NUM_OF_UNITS... \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INGREDIENT_NAME + "Cod Fish "
            + PREFIX_INGREDIENT_NUM + "20 ";

    public static final String MESSAGE_STOCKUP_INGREDIENT_SUCCESS = "Ingredient(s) stocked up: %1$s";

    private final List<ChangeStockDescriptor> stockDescriptorList;

    /**
     * Creates a StockUpCommand to stock up the specified {@code Ingredient}
     */
    public StockUpCommand(List<ChangeStockDescriptor> stockDescriptorList) {
        requireNonNull(stockDescriptorList);
        this.stockDescriptorList = stockDescriptorList;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Ingredient ingredientToStockUp;
        Ingredient stockedUpIngredient;
        StringBuilder ingredientString = new StringBuilder();

        for (int index = 0; index < stockDescriptorList.size(); index++) {
            try {
                IngredientName name = stockDescriptorList.get(index).getName();
                NumUnits unitsToAdd = stockDescriptorList.get(index).getNumUnits();
                ingredientToStockUp = model.findIngredient(name);
                EditIngredientDescriptor editIngredientDescriptor = new EditIngredientDescriptor();
                editIngredientDescriptor.setName(name);
                editIngredientDescriptor.setNumUnits(unitsToAdd);
                stockedUpIngredient = createEditedIngredient(ingredientToStockUp, editIngredientDescriptor);
            } catch (IngredientNotFoundException e) {
                throw new CommandException(Messages.MESSAGE_INGREDIENT_NAME_NOT_FOUND);
            }

            model.updateIngredient(ingredientToStockUp, stockedUpIngredient);
            model.updateFilteredIngredientList(PREDICATE_SHOW_ALL_INGREDIENTS);
            ingredientString.append("\n" + stockedUpIngredient);
        }

        model.commitRestaurantBook();
        EventsCenter.getInstance().post(new DisplayIngredientListRequestEvent());
        return new CommandResult(String.format(MESSAGE_STOCKUP_INGREDIENT_SUCCESS, ingredientString));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StockUpCommand // instanceof handles nulls
                    && stockDescriptorList.equals(((StockUpCommand) other).stockDescriptorList));
    }

    /**
     * Stores the name of the ingredient and the number of units to be stocked up or consumed.
     */
    public static class ChangeStockDescriptor {
        private IngredientName name;
        private NumUnits numUnits;

        public ChangeStockDescriptor() {}

        public ChangeStockDescriptor(IngredientName name, NumUnits numUnits) {
            this.name = name;
            this.numUnits = numUnits;
        }

        /**
         * Copy constructor.
         */
        public ChangeStockDescriptor(ChangeStockDescriptor toCopy) {
            setName(toCopy.name);
            setNumUnits(toCopy.numUnits);
        }

        public void setName(IngredientName name) {
            this.name = name;
        }

        public IngredientName getName() {
            return this.name;
        }

        public void setNumUnits(NumUnits numUnits) {
            this.numUnits = numUnits;
        }

        public NumUnits getNumUnits() {
            return this.numUnits;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof ChangeStockDescriptor)) {
                return false;
            }

            // state check
            ChangeStockDescriptor e = (ChangeStockDescriptor) other;

            return getName().equals(e.getName())
                    && getNumUnits().equals(e.getNumUnits());
        }
    }
}
