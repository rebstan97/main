package seedu.address.logic.commands.menu;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Sorts and lists all items in menu either by name or price.
 */
public class SortMenuCommand extends Command {

    public static final String COMMAND_WORD = "sort-menu";

    public static final String COMMAND_ALIAS = "sm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all items by name in alphabetical order or "
            + "by price in ascending order "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: SORTING METHOD\n"
            + "Example: " + COMMAND_WORD + " name";

    public static final String MESSAGE_SORTED = "Menu has been sorted by %1$s";

    public enum SortMethod {
        NAME, PRICE
    }

    private final SortMethod sortMethod;

    public SortMenuCommand(SortMethod sortMethod) {
        this.sortMethod = sortMethod;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        switch (sortMethod) {
        case NAME:
            model.sortMenuByName();
            break;
        case PRICE:
            model.sortMenuByPrice();
            break;
        default:
        }
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SORTED, sortMethod.name()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortMenuCommand // instanceof handles nulls
                    && sortMethod.equals(((SortMenuCommand) other).sortMethod)); // state check
    }
}
