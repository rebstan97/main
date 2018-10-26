package seedu.address.logic.commands.ingredients;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ingredients.StockUpCommand.MESSAGE_STOCKUP_INGREDIENT_SUCCESS;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.ingredients.TypicalIngredients.AVOCADO;
import static seedu.address.testutil.ingredients.TypicalIngredients.BEANS;
import static seedu.address.testutil.ingredients.TypicalIngredients.CABBAGE;
import static seedu.address.testutil.ingredients.TypicalIngredients.DUCK;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.ingredients.StockUpCommand.ChangeStockDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.testutil.ingredients.ChangeStockDescriptorBuilder;
import seedu.address.testutil.ingredients.IngredientBuilder;

public class StockUpCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private ChangeStockDescriptorBuilder descriptorBuilder = new ChangeStockDescriptorBuilder();
    private List<ChangeStockDescriptor> descriptorList;

    @Test
    public void execute_validIngredient_success() {
        descriptorList = new ArrayList<>();
        ChangeStockDescriptor descriptor =
                descriptorBuilder.buildChangeStockDescriptor("Mexican Avocado", "20");
        descriptorList.add(descriptor);

        StockUpCommand stockUpCommand = new StockUpCommand(descriptorList);

        Ingredient updatedIngredient = new IngredientBuilder(AVOCADO).withNumUnits("20").build();
        String expectedMessage = String.format(MESSAGE_STOCKUP_INGREDIENT_SUCCESS, "\n" + updatedIngredient);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateIngredient(model.findIngredient(AVOCADO.getName()), updatedIngredient);
        expectedModel.commitAddressBook();

        assertCommandSuccess(stockUpCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleValidIngredients_success() {
        descriptorList = new ArrayList<>();
        ChangeStockDescriptor descriptor =
                descriptorBuilder.buildChangeStockDescriptor("Chinese Cabbage", "10");
        descriptorList.add(descriptor);
        descriptor =
                descriptorBuilder.buildChangeStockDescriptor("Baked Beans", "87");
        descriptorList.add(descriptor);
        descriptor =
                descriptorBuilder.buildChangeStockDescriptor("Duck Drumstick", "59");
        descriptorList.add(descriptor);

        StockUpCommand stockUpCommand = new StockUpCommand(descriptorList);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        StringBuilder updatedIngredients = new StringBuilder();

        Ingredient updatedIngredient = new IngredientBuilder(CABBAGE).withNumUnits("10").build();
        updatedIngredients.append("\n" + updatedIngredient);
        expectedModel.updateIngredient(CABBAGE, updatedIngredient);

        updatedIngredient = new IngredientBuilder(BEANS).withNumUnits("87").build();
        updatedIngredients.append("\n" + updatedIngredient);
        expectedModel.updateIngredient(BEANS, updatedIngredient);

        updatedIngredient = new IngredientBuilder(DUCK).withNumUnits("59").build();
        updatedIngredients.append("\n" + updatedIngredient);
        expectedModel.updateIngredient(DUCK, updatedIngredient);

        String expectedMessage = String.format(MESSAGE_STOCKUP_INGREDIENT_SUCCESS, updatedIngredients);
        expectedModel.commitAddressBook();

        assertCommandSuccess(stockUpCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIngredient_failure() {
        descriptorList = new ArrayList<>();
        ChangeStockDescriptor descriptor =
                descriptorBuilder.buildChangeStockDescriptor("Iceberg Lettuce", "35");
        descriptorList.add(descriptor);

        StockUpCommand stockUpCommand = new StockUpCommand(descriptorList);
        String expectedMessage = String.format(Messages.MESSAGE_INGREDIENT_NAME_NOT_FOUND);

        assertCommandFailure(stockUpCommand, model, commandHistory, expectedMessage);
    }


}
