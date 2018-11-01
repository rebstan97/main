package seedu.restaurant.logic.commands.ingredient;

import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.ingredient.StockUpCommand.MESSAGE_STOCKUP_INGREDIENT_SUCCESS;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;
import static seedu.restaurant.testutil.ingredient.TypicalIngredients.AVOCADO;
import static seedu.restaurant.testutil.ingredient.TypicalIngredients.BEANS;
import static seedu.restaurant.testutil.ingredient.TypicalIngredients.CABBAGE;
import static seedu.restaurant.testutil.ingredient.TypicalIngredients.DUCK;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.ingredient.StockUpCommand.ChangeStockDescriptor;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.RestaurantBook;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.testutil.ingredient.ChangeStockDescriptorBuilder;
import seedu.restaurant.testutil.ingredient.IngredientBuilder;

public class StockUpCommandTest {
    private Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private ChangeStockDescriptorBuilder descriptorBuilder = new ChangeStockDescriptorBuilder();
    private List<ChangeStockDescriptor> descriptorList;

    @Test
    public void execute_validIngredient_success() {
        descriptorList = new ArrayList<>();
        ChangeStockDescriptor descriptor =
                descriptorBuilder.buildChangeStockDescriptor("Mexican Avocado", 20);
        descriptorList.add(descriptor);

        StockUpCommand stockUpCommand = new StockUpCommand(descriptorList);

        Ingredient updatedIngredient = new IngredientBuilder(AVOCADO).withNumUnits(20).build();
        String expectedMessage = String.format(MESSAGE_STOCKUP_INGREDIENT_SUCCESS, "\n" + updatedIngredient);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.updateIngredient(model.findIngredient(AVOCADO.getName()), updatedIngredient);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(stockUpCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleValidIngredients_success() {
        descriptorList = new ArrayList<>();
        ChangeStockDescriptor descriptor =
                descriptorBuilder.buildChangeStockDescriptor("Chinese Cabbage", 10);
        descriptorList.add(descriptor);
        descriptor =
                descriptorBuilder.buildChangeStockDescriptor("Baked Beans", 87);
        descriptorList.add(descriptor);
        descriptor =
                descriptorBuilder.buildChangeStockDescriptor("Duck Drumstick", 59);
        descriptorList.add(descriptor);

        StockUpCommand stockUpCommand = new StockUpCommand(descriptorList);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        StringBuilder updatedIngredients = new StringBuilder();

        Ingredient updatedIngredient = new IngredientBuilder(CABBAGE).withNumUnits(10).build();
        updatedIngredients.append("\n" + updatedIngredient);
        expectedModel.updateIngredient(CABBAGE, updatedIngredient);

        updatedIngredient = new IngredientBuilder(BEANS).withNumUnits(87).build();
        updatedIngredients.append("\n" + updatedIngredient);
        expectedModel.updateIngredient(BEANS, updatedIngredient);

        updatedIngredient = new IngredientBuilder(DUCK).withNumUnits(59).build();
        updatedIngredients.append("\n" + updatedIngredient);
        expectedModel.updateIngredient(DUCK, updatedIngredient);

        String expectedMessage = String.format(MESSAGE_STOCKUP_INGREDIENT_SUCCESS, updatedIngredients);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(stockUpCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIngredient_failure() {
        descriptorList = new ArrayList<>();
        ChangeStockDescriptor descriptor =
                descriptorBuilder.buildChangeStockDescriptor("Iceberg Lettuce", 35);
        descriptorList.add(descriptor);

        StockUpCommand stockUpCommand = new StockUpCommand(descriptorList);
        String expectedMessage = String.format(Messages.MESSAGE_INGREDIENT_NAME_NOT_FOUND);

        assertCommandFailure(stockUpCommand, model, commandHistory, expectedMessage);
    }


}
