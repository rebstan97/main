package seedu.address.testutil.salesrecords;

import static seedu.address.logic.commands.sales.RecordSalesCommand.MESSAGE_INGREDIENT_UPDATE_SUCCESS;
import static seedu.address.logic.commands.sales.RecordSalesCommand.MESSAGE_REQUIRED_INGREDIENTS_NOT_FOUND;

import java.util.HashMap;

import seedu.address.logic.commands.sales.RecordSalesCommand.RequiredIngredientsNotFoundException;
import seedu.address.model.Model;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.menu.Item;
import seedu.address.model.menu.Name;
import seedu.address.model.salesrecord.SalesRecord;

/**
 * A utility class to help generate the ingredientUpdateStatus message in RecordSalesCommand. There are five different
 * messages that could occur.
 */
public class RecordSalesCommandUtil {
    // to be updated once merged
    public static String getIngredientUpdateStatus(Model model, SalesRecord record) {
        try {
            int quantitySold = record.getQuantitySold().getValue();
            Name itemName = new Name(record.getName().toString());

            Item item = model.findItem(itemName);
            HashMap<IngredientName, Integer> ingredientsUsed = model.getRequiredIngredients(item);

            if (ingredientsUsed.isEmpty()) {
                throw new RequiredIngredientsNotFoundException();
            }

            ingredientsUsed.replaceAll((ingredient, quantityUsed) -> quantityUsed * quantitySold);
            model.consumeIngredients(ingredientsUsed);
//        } catch (ItemNotFoundException e) {
//            return MESSAGE_ITEM_NOT_FOUND;
        } catch (RequiredIngredientsNotFoundException e) {
            return MESSAGE_REQUIRED_INGREDIENTS_NOT_FOUND;
//        } catch (IngredientNotFoundException e) {
//            return MESSAGE_INGREDIENT_NOT_FOUND;
//        } catch (IngredientNotEnoughException e) {
//            return MESSAGE_INGREDIENT_NOT_ENOUGH;
        }
        return MESSAGE_INGREDIENT_UPDATE_SUCCESS;
    }
}
