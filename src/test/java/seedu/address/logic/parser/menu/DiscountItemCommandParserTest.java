package seedu.address.logic.parser.menu;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ITEM_PERCENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_PERCENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_PERCENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.menu.DiscountItemCommand;

public class DiscountItemCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DiscountItemCommand.MESSAGE_USAGE);

    private DiscountItemCommandParser parser = new DiscountItemCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, ITEM_PERCENT_DESC, MESSAGE_INVALID_FORMAT);

        // no percent specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no percent specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + ITEM_PERCENT_DESC, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + ITEM_PERCENT_DESC, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPercent_failure() {
        // invalid percent
        assertParseFailure(parser, "1" + INVALID_ITEM_PERCENT_DESC,
                DiscountItemCommandParser.MESSAGE_PERCENT_CONSTRAINTS);
    }

    @Test
    public void parse_validIndexFollowedByInvalidPercent_failure() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_ITEM_PERCENT_DESC;
        assertParseFailure(parser, userInput, DiscountItemCommandParser.MESSAGE_PERCENT_CONSTRAINTS);
    }

    @Test
    public void parse_validIndexFollowedByValidPercent_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + ITEM_PERCENT_DESC;

        DiscountItemCommand expectedCommand = new DiscountItemCommand(targetIndex,
                Double.parseDouble(VALID_ITEM_PERCENT));

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
