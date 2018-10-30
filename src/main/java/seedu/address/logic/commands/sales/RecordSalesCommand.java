package seedu.address.logic.commands.sales;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY_SOLD;

import java.util.HashMap;
import java.util.Map;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DisplayRecordListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.exceptions.IngredientNotEnoughException;
import seedu.address.model.ingredient.exceptions.IngredientNotFoundException;
import seedu.address.model.menu.Item;
import seedu.address.model.menu.Name;
import seedu.address.model.menu.exceptions.ItemNotFoundException;
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
            + "Please add the item into the menu and specify the ingredients it requires to enable auto-ingredient "
            + "update.";
    public static final String MESSAGE_REQUIRED_INGREDIENTS_NOT_FOUND = "However, the ingredients required to make "
            + "this item have not been specified. Please specify the ingredients required to enable auto-ingredient."
            + "update.";
    public static final String MESSAGE_INGREDIENT_NOT_FOUND = "However, some ingredient(s) required to make this item"
            + " were not found in the ingredient list. Please add the ingredient(s) to enable auto-ingredient update.";
    public static final String MESSAGE_INGREDIENT_NOT_ENOUGH = "However, some ingredient(s) in the ingredient section"
            + " fall short of that required to make the amount of item specified in the quantity sold field. Please "
            + "stockup your ingredients to enable the auto-ingredient update.";
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
        } catch (ItemNotFoundException e) {
            ingredientsUpdateStatus = MESSAGE_ITEM_NOT_FOUND;
        } catch (RequiredIngredientsNotFoundException e) {
            ingredientsUpdateStatus = MESSAGE_REQUIRED_INGREDIENTS_NOT_FOUND;
        } catch (IngredientNotFoundException e) {
            ingredientsUpdateStatus = MESSAGE_INGREDIENT_NOT_FOUND;
        } catch (IngredientNotEnoughException e) {
            ingredientsUpdateStatus = MESSAGE_INGREDIENT_NOT_ENOUGH;
        }

        model.addRecord(toAdd); // record will be added even if one of the four exceptions above was caught
        model.commitAddressBook();
        EventsCenter.getInstance().post(new DisplayRecordListRequestEvent());
        return new CommandResult(String.format(MESSAGE_RECORD_SALES_SUCCESS, toAdd) + "\n"
                + ingredientsUpdateStatus);
    }

    private void updateIngredientList(Model model)  throws ItemNotFoundException, RequiredIngredientsNotFoundException,
            IngredientNotFoundException, IngredientNotEnoughException {
        Name itemName = new Name(toAdd.getName().toString());
        int quantitySold = toAdd.getQuantitySold().getValue();

        // finds the item in menu section
        Item item = model.findItem(itemName);

        // retrieve the name of ingredients and their corresponding quantity used to make one unit of "item"
        Map<IngredientName, Integer> requiredIngredients = model.getRequiredIngredients(item);

        if (requiredIngredients.isEmpty()) {
            throw new RequiredIngredientsNotFoundException();
        }

        Map<IngredientName, Integer> ingredientsUsed = new HashMap<>(requiredIngredients);

        // compute the total ingredients used after factoring quantity sold
        ingredientsUsed.replaceAll((ingredient, quantityUsed) -> quantityUsed * quantitySold);

        // saves the ingredientsUsed in the SalesRecord
        toAdd = toAdd.setIngredientsUsed(ingredientsUsed);

        // update ingredient list
        model.consumeIngredients(ingredientsUsed);

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
