package seedu.address.logic.commands.menu;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DisplayItemListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.RestaurantBook;

/**
 * Clears the menu.
 */
public class ClearMenuCommand extends Command {

    public static final String COMMAND_WORD = "clear-menu";
    public static final String COMMAND_ALIAS = "cm";
    public static final String MESSAGE_SUCCESS = "Menu has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetMenuData(new RestaurantBook());
        model.commitAddressBook();
        EventsCenter.getInstance().post(new DisplayItemListRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
