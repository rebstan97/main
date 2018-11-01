package seedu.address.logic.commands.ingredient;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.testutil.ingredient.IngredientBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddIngredientCommand}.
 */
public class AddIngredientCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    }

    @Test
    public void execute_newIngredient_success() {
        Ingredient validIngredient = new IngredientBuilder().build();

        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.addIngredient(validIngredient);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(new AddIngredientCommand(validIngredient), model, commandHistory,
                String.format(AddIngredientCommand.MESSAGE_SUCCESS, validIngredient), expectedModel);
    }

    @Test
    public void execute_duplicateIngredient_throwsCommandException() {
        Ingredient ingredientInList = model.getRestaurantBook().getIngredientList().get(0);
        assertCommandFailure(new AddIngredientCommand(ingredientInList), model, commandHistory,
                AddIngredientCommand.MESSAGE_DUPLICATE_INGREDIENT);
    }

}
