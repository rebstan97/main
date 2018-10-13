package seedu.address.logic.commands.menu;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.menu.SortMenuCommand.MESSAGE_SORTED;
import static seedu.address.testutil.menu.TypicalItems.APPLE_JUICE;
import static seedu.address.testutil.menu.TypicalItems.BEEF_BURGER;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.menu.SortMenuCommand.SortMethod;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.AddressBookBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindItemCommand}.
 */
public class SortMenuCommandTest {
    private AddressBook ab = new AddressBookBuilder().withItem(BEEF_BURGER).withItem(APPLE_JUICE).build();
    private Model model = new ModelManager(ab, new UserPrefs());
    private Model expectedModel = new ModelManager(ab, new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_sortMenuByName() {
        SortMethod sortMethod = SortMethod.NAME;
        String expectedMessage = String.format(MESSAGE_SORTED, sortMethod.name());
        SortMenuCommand command = new SortMenuCommand(sortMethod);
        expectedModel.sortMenuByName();
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortMenuByPrice() {
        SortMethod sortMethod = SortMethod.PRICE;
        String expectedMessage = String.format(MESSAGE_SORTED, sortMethod.name());
        SortMenuCommand command = new SortMenuCommand(sortMethod);
        expectedModel.sortMenuByPrice();
        expectedModel.commitAddressBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }
}
