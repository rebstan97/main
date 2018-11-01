package seedu.restaurant.logic.commands;

import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import org.junit.Before;
import org.junit.Test;

import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
