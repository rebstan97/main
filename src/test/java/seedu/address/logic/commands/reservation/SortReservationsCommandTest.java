package seedu.address.logic.commands.reservation;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.reservation.SortReservationsCommand.MESSAGE_SORTED;
import static seedu.address.testutil.reservation.TypicalReservations.ANDREW;
import static seedu.address.testutil.reservation.TypicalReservations.BILLY;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RestaurantBook;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.AddressBookBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code SortReservationsCommand}.
 */
public class SortReservationsCommandTest {
    private RestaurantBook ab = new AddressBookBuilder().withReservation(BILLY).withReservation(ANDREW).build();
    private Model model = new ModelManager(ab, new UserPrefs());
    private Model expectedModel = new ModelManager(ab, new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_sortReservations() {
        String expectedMessage = String.format(MESSAGE_SORTED);
        SortReservationsCommand command = new SortReservationsCommand();
        expectedModel.sortReservations();
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }
}
