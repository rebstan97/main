package seedu.address.logic.commands.ingredients;

import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_MINIMUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_UNIT;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientPrice;
import seedu.address.model.ingredient.IngredientUnit;
import seedu.address.model.ingredient.MinimumUnit;
import seedu.address.model.ingredient.NumUnits;

/**
 * Edits the details of an existing ingredient in the restaurant book.
 */
public abstract class EditIngredientCommand extends Command {

    public static final String COMMAND_WORD = "edit-ingredient";

    public static final String COMMAND_ALIAS = "edit-ing";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the ingredient identified "
            + "by the index number used in the displayed ingredient list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_INGREDIENT_NAME + "NAME] "
            + "[" + PREFIX_INGREDIENT_UNIT + "UNIT] "
            + "[" + PREFIX_INGREDIENT_PRICE + "PRICE] "
            + "[" + PREFIX_INGREDIENT_MINIMUM + "MINIMUM] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_INGREDIENT_PRICE + "$5.60 "
            + PREFIX_INGREDIENT_MINIMUM + "15";

    public static final String MESSAGE_EDIT_INGREDIENT_SUCCESS = "Edited Ingredient: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided";
    public static final String MESSAGE_DUPLICATE_INGREDIENT = "This ingredient already exists in the restaurant book";

    @Override
    public abstract CommandResult execute(Model model, CommandHistory history) throws CommandException;

    /**
     * Creates and returns an {@code Ingredient} with the details of {@code ingredientToEdit}
     * edited with {@code editIngredientDescriptor}.
     */
    public static Ingredient createEditedIngredient(Ingredient ingredientToEdit,
            EditIngredientDescriptor editIngredientDescriptor) {
        assert ingredientToEdit != null;

        IngredientName updatedName = editIngredientDescriptor.getName().orElse(ingredientToEdit.getName());
        IngredientUnit updatedUnit = editIngredientDescriptor.getUnit().orElse(ingredientToEdit.getUnit());
        IngredientPrice updatedPrice = editIngredientDescriptor.getPrice().orElse(ingredientToEdit.getPrice());
        MinimumUnit updatedMinUnit = editIngredientDescriptor.getMinimum().orElse(ingredientToEdit.getMinimum());

        NumUnits numToAdd = editIngredientDescriptor.getNumUnits().orElse(new NumUnits("0"));
        NumUnits updatedNumUnits = ingredientToEdit.getNumUnits().add(numToAdd);

        return new Ingredient(updatedName, updatedUnit, updatedPrice, updatedMinUnit, updatedNumUnits);
    }

    @Override
    public abstract boolean equals(Object other);

    /**
     * Stores the details to edit the ingredient with. Each non-empty field value will replace the
     * corresponding field value of the ingredient.
     */
    public static class EditIngredientDescriptor {
        private IngredientName name;
        private IngredientUnit unit;
        private IngredientPrice price;
        private MinimumUnit minimumUnit;
        private NumUnits numUnits;

        public EditIngredientDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditIngredientDescriptor(EditIngredientDescriptor toCopy) {
            setName(toCopy.name);
            setUnit(toCopy.unit);
            setPrice(toCopy.price);
            setMinimum(toCopy.minimumUnit);
            setNumUnits(toCopy.numUnits);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, unit, price, minimumUnit, numUnits);
        }

        public void setName(IngredientName name) {
            this.name = name;
        }

        public Optional<IngredientName> getName() {
            return Optional.ofNullable(name);
        }

        public void setUnit(IngredientUnit unit) {
            this.unit = unit;
        }

        public Optional<IngredientUnit> getUnit() {
            return Optional.ofNullable(unit);
        }

        public void setPrice(IngredientPrice price) {
            this.price = price;
        }

        public Optional<IngredientPrice> getPrice() {
            return Optional.ofNullable(price);
        }

        public void setMinimum(MinimumUnit minimumUnit) {
            this.minimumUnit = minimumUnit;
        }

        public Optional<MinimumUnit> getMinimum() {
            return Optional.ofNullable(minimumUnit);
        }

        public void setNumUnits(NumUnits numUnits) {
            this.numUnits = numUnits;
        }

        public Optional<NumUnits> getNumUnits() {
            return Optional.ofNullable(numUnits);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditIngredientDescriptor)) {
                return false;
            }

            // state check
            EditIngredientDescriptor e = (EditIngredientDescriptor) other;

            return getName().equals(e.getName())
                    && getUnit().equals(e.getUnit())
                    && getPrice().equals(e.getPrice())
                    && getMinimum().equals(e.getMinimum())
                    && getNumUnits().equals(e.getNumUnits());
        }
    }
}
