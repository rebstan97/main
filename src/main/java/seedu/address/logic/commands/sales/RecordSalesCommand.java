package seedu.address.logic.commands.sales;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY_SOLD;

import java.util.HashMap;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.menu.Item;
import seedu.address.model.menu.Name;
import seedu.address.model.salesrecord.SalesRecord;

/**
 * Record the sales volume of a menu item
 */
public class RecordSalesCommand extends Command {
    public static final String COMMAND_WORD = "record-sales";

    public static final String COMMAND_ALIAS = "rs";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Records the sales volume of a menu item within a day"
            + " into the sales book.\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE (must be in DD-MM-YYYY format) "
            + PREFIX_ITEM_NAME + "ITEM_NAME "
            + PREFIX_QUANTITY_SOLD + "QUANTITY SOLD "
            + PREFIX_ITEM_PRICE + "ITEM_PRICE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + " 25-09-2018 "
            + PREFIX_ITEM_NAME + "Fried Rice "
            + PREFIX_QUANTITY_SOLD + "35 "
            + PREFIX_ITEM_PRICE + "5.50";

    public static final String MESSAGE_RECORD_SALES_SUCCESS = "Sales volume recorded.\n%1$s";
    public static final String MESSAGE_DUPLICATE_SALES_RECORD = "Sales record of \"%1$s\" already exists on the same "
            + "date.";
    public static final String MESSAGE_ITEM_NOT_FOUND = "However, the item does not exist in the menu section. "
            + "Please add the item via the [INSERT_COMMAND] and specify the ingredients required via the "
            + "[INSERT_COMMAND] to enable auto-ingredient update.";
    public static final String MESSAGE_REQUIRED_INGREDIENTS_NOT_FOUND = "However, the ingredients required to make "
            + "this item have not been specified. Please do so via the [INSERT_COMMAND] to enable auto-ingredient."
            + "update.";
    public static final String MESSAGE_INGREDIENT_NOT_FOUND = "However, some ingredient(s) required to make this item"
            + " were not found in the ingredient section. Please add the ingredient(s) via the [INSERT_COMMAND] to "
            + "enable auto-ingredient update.";
    public static final String MESSAGE_INGREDIENT_NOT_ENOUGH = "However, some ingredient(s) in the ingredient section"
            + " fall short of that required to make the amount of item specified in the quantity sold field. Please "
            + "stockup your ingredients via the [INSERT_COMMAND] to enable the auto-ingredient update.";
    public static final String MESSAGE_INGREDIENT_UPDATE_SUCCESS = "Ingredient list has been updated.";

    private SalesRecord toAdd;

    /**
     * @param salesRecord to be appended to the sales book
     */
    public RecordSalesCommand(SalesRecord salesRecord) {
        requireNonNull(salesRecord);
        this.toAdd = salesRecord;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasRecord(toAdd)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_SALES_RECORD, toAdd.getName()));
        }

        String ingredientsUpdateStatus = MESSAGE_INGREDIENT_UPDATE_SUCCESS;

        try {
            updateIngredientList(model);
//        } catch (ItemNotFoundException e) {
//            ingredientsUpdateStatus = MESSAGE_ITEM_NOT_FOUND;
        } catch (RequiredIngredientsNotFoundException e) {
            ingredientsUpdateStatus = MESSAGE_REQUIRED_INGREDIENTS_NOT_FOUND;
//        } catch (IngredientNotFoundException e) {
//            ingredientsUpdateStatus = MESSAGE_INGREDIENT_NOT_FOUND;
//        } catch (IngredientNotEnoughException e) {
//            ingredientsUpdateStatus = MESSAGE_INGREDIENT_NOT_ENOUGH;
        }

        model.addRecord(toAdd); // record will be added even if one of the four exceptions above was caught
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_RECORD_SALES_SUCCESS, toAdd) + "\n"
                + ingredientsUpdateStatus);
    }

    private void updateIngredientList(Model model)  throws /*ItemNotFoundException, */
    RequiredIngredientsNotFoundException
        /*, IngredientNotFoundException, IngredientNotEnoughException */{
        Name itemName = new Name(toAdd.getName().toString());
        int quantitySold = toAdd.getQuantitySold().getValue();

        // finds the item in menu section
        Item item = model.findItem(itemName);

        // retrieve the name of ingredients and their corresponding quantity used to make one unit of "item"
        HashMap<IngredientName, Integer> ingredientsUsed = model.getRequiredIngredients(item);

        if (ingredientsUsed.isEmpty()) {
            throw new RequiredIngredientsNotFoundException();
        }

        // compute the total ingredients used after factoring quantity sold
        ingredientsUsed.replaceAll((ingredient, quantityUsed) -> quantityUsed * quantitySold);

        // update ingredient list
        model.consumeIngredients(ingredientsUsed);

        // saves the ingredientsUsed in the SalesRecord
        toAdd = toAdd.setIngredientsUsed(ingredientsUsed);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecordSalesCommand // instanceof handles nulls
                    && toAdd.equals(((RecordSalesCommand) other).toAdd));
    }

    /**
     * Signal that the ingredients required to make the item has not been specified in menu section.
     */
    public static class RequiredIngredientsNotFoundException extends Exception {}
}


// exceptions: RequiredIngredientsNotFoundException, ItemNotFoundException, IngredientNotFoundException,
// IngredientNotEnoughException

// save ingredientsUsed into storage

// API for edit ingredientUsed if ingredient name changes



// show ingredientsUsed in UI browser panel

// update UGDG -> remove delete/edit by name and date

// merge and update using search "to be updated once merged"
