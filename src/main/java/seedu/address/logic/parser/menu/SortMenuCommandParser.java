package seedu.address.logic.parser.menu;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.menu.SortMenuCommand;
import seedu.address.logic.commands.menu.SortMenuCommand.SortMethod;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindItemCommand object
 */
public class SortMenuCommandParser implements Parser<SortMenuCommand> {

    private static final String SORT_METHOD_NAME = "NAME";
    private static final String SORT_METHOD_PRICE = "PRICE";

    /**
     * Parses the given {@code String} of arguments in the context of the SortMenuCommand
     * and returns an SortMenuCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortMenuCommand parse(String args) throws ParseException {
        requireNonNull(args);
        SortMethod sortMethod = parseSortMethod(args);
        return new SortMenuCommand(sortMethod);
    }

    private SortMethod parseSortMethod(String userInput) throws ParseException {
        String input = userInput.toUpperCase().trim();
        switch (input) {
        case SORT_METHOD_NAME:
            return SortMethod.NAME;
        case SORT_METHOD_PRICE:
            return SortMethod.PRICE;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortMenuCommand.MESSAGE_USAGE));
        }
    }


}
