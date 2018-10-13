package seedu.address.logic.parser.menu;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.menu.SortMenuCommand;
import seedu.address.logic.commands.menu.SortMenuCommand.SortMethod;

public class SortMenuCommandParserTest {

    private SortMenuCommandParser parser = new SortMenuCommandParser();

    @Test
    public void parse_validArgs_returnsSortMenuCommand() {
        // lower case
        assertParseSuccess(parser, "name", new SortMenuCommand(SortMethod.NAME));
        assertParseSuccess(parser, "price", new SortMenuCommand(SortMethod.PRICE));

        // upper case
        assertParseSuccess(parser, "NAME", new SortMenuCommand(SortMethod.NAME));
        assertParseSuccess(parser, "PRICE", new SortMenuCommand(SortMethod.PRICE));

        // camel case
        assertParseSuccess(parser, "nAmE", new SortMenuCommand(SortMethod.NAME));
        assertParseSuccess(parser, "pRiCE", new SortMenuCommand(SortMethod.PRICE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "not name", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortMenuCommand.MESSAGE_USAGE));
    }
}
