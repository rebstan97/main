package seedu.address.logic.commands.menu;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearMenuCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyMenu_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitAddressBook();

        assertCommandSuccess(new ClearMenuCommand(), model, commandHistory, ClearMenuCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyMenu_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.resetMenuData(new AddressBook());
        expectedModel.commitAddressBook();

        assertCommandSuccess(new ClearMenuCommand(), model, commandHistory, ClearMenuCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

}
