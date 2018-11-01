package seedu.restaurant.logic.commands;

import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import org.junit.Before;
import org.junit.Test;

import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.model.person.Person;
import seedu.restaurant.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(new AddCommand(validPerson), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getRestaurantBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
