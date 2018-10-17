package seedu.address.logic.parser.menu;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.menu.FindItemCommand;
import seedu.address.model.menu.NameContainsKeywordsPredicate;

public class FindItemCommandParserTest {

    private FindItemCommandParser parser = new FindItemCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindItemCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindItemCommand() {
        // no leading and trailing whitespaces
        FindItemCommand expectedFindItemCommand =
                new FindItemCommand(new NameContainsKeywordsPredicate(Arrays.asList("Apple", "Burger")));
        assertParseSuccess(parser, "Apple Burger", expectedFindItemCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Apple \n \t Burger  \t", expectedFindItemCommand);
    }

}
