package seedu.address.logic.commands.ingredients;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showIngredientAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.ingredient.Ingredient;

/**
 * Contains integration tests (interaction with the Model) and unit tests for LowStockCommand.
 */
public class LowStockCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();
    private Predicate<Ingredient> predicate;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        predicate = ingredient ->
                ingredient.getNumUnits().getNumberOfUnits() < ingredient.getMinimum().getMinimumUnit();
    }

    @Test
    public void execute_listIsNotFiltered_showsUpdatedFilteredList() {
        model.updateFilteredIngredientList(predicate);
        assertCommandSuccess(new LowStockCommand(), model, commandHistory,
                LowStockCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsUpdatedFilteredList() {
        showIngredientAtIndex(model, INDEX_FIRST);
        model.updateFilteredIngredientList(predicate);
        assertCommandSuccess(new LowStockCommand(), model, commandHistory,
                LowStockCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
