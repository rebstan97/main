package seedu.address.logic.commands.menu;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.menu.TagContainsKeywordsPredicate;

/**
 * Finds and lists all items in menu whose tags contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterMenuCommand extends Command {

    public static final String COMMAND_WORD = "filter-menu";

    public static final String COMMAND_ALIAS = "fm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all items whose tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " monday tuesday wednesday";

    private final TagContainsKeywordsPredicate predicate;

    public FilterMenuCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredItemList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW, model.getFilteredItemList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterMenuCommand // instanceof handles nulls
                    && predicate.equals(((FilterMenuCommand) other).predicate)); // state check
    }
}
