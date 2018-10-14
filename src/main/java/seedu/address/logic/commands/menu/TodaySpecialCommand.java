package seedu.address.logic.commands.menu;

import static java.util.Objects.requireNonNull;

import java.util.Calendar;
import java.util.Collections;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.DisplayItemListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.menu.TagContainsKeywordsPredicate;

/**
 * Finds and lists all items in menu whose tags contains the day of the week.
 */
public class TodaySpecialCommand extends Command {

    public static final String COMMAND_WORD = "today-special";

    public static final String COMMAND_ALIAS = "ts";

    private final TagContainsKeywordsPredicate predicate;

    public TodaySpecialCommand() {
        String str;
        switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
        case Calendar.SUNDAY:
            str = "sunday";
            break;
        case Calendar.MONDAY:
            str = "monday";
            break;
        case Calendar.TUESDAY:
            str = "tuesday";
            break;
        case Calendar.WEDNESDAY:
            str = "wednesday";
            break;
        case Calendar.THURSDAY:
            str = "thursday";
            break;
        case Calendar.FRIDAY:
            str = "friday";
            break;
        default:
            str = "saturday";
        }
        this.predicate = new TagContainsKeywordsPredicate(Collections.singletonList(str));
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredItemList(predicate);
        EventsCenter.getInstance().post(new DisplayItemListRequestEvent());
        return new CommandResult(
                String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW, model.getFilteredItemList().size()));
    }
}
