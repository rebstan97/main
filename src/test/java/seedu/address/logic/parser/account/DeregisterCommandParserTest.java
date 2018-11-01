package seedu.address.logic.parser.account;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PREFIX_WITH_INVALID_USERNAME;
import static seedu.address.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_USERNAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_DEMO_ONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.account.AccountBuilder.DEFAULT_USERNAME;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.account.DeregisterCommand;
import seedu.address.model.account.Account;
import seedu.address.model.account.Username;
import seedu.address.testutil.account.AccountBuilder;

public class DeregisterCommandParserTest {

    private DeregisterCommandParser parser = new DeregisterCommandParser();
    private Account expectedAccount;

    @Before
    public void setUp() {
        expectedAccount = new AccountBuilder().withUsername(VALID_USERNAME_DEMO_ONE).build();
    }

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PREFIX_WITH_VALID_USERNAME,
                new DeregisterCommand(expectedAccount));

        // whitespace only
        assertParseSuccess(parser, PREFIX_WITH_VALID_USERNAME, new DeregisterCommand(expectedAccount));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeregisterCommand.MESSAGE_USAGE);

        // empty input
        assertParseFailure(parser, "", expectedMessage);

        // prefix missing
        assertParseFailure(parser, DEFAULT_USERNAME, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid username
        assertParseFailure(parser, PREFIX_WITH_INVALID_USERNAME, Username.MESSAGE_USERNAME_CONSTRAINT);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PREFIX_WITH_VALID_USERNAME,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeregisterCommand.MESSAGE_USAGE));
    }
}
