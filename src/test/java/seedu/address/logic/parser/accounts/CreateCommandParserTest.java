package seedu.address.logic.parser.accounts;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_USERNAME;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.accounts.AccountBuilder.DEFAULT_PASSWORD;
import static seedu.address.testutil.accounts.AccountBuilder.DEFAULT_USERNAME;
import static seedu.address.testutil.accounts.TypicalAccounts.DEFAULT_ADMIN_ACCOUNT;

import org.junit.Test;

import seedu.address.logic.commands.accounts.CreateCommand;
import seedu.address.model.accounts.Account;
import seedu.address.model.accounts.Password;
import seedu.address.model.accounts.Username;
import seedu.address.testutil.accounts.AccountBuilder;

public class CreateCommandParserTest {

    private CreateCommandParser parser = new CreateCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Account expectedAccount = new AccountBuilder(DEFAULT_ADMIN_ACCOUNT).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_USERNAME + VALID_PASSWORD,
                new CreateCommand(expectedAccount));

        // whitespace only
        assertParseSuccess(parser, VALID_USERNAME + VALID_PASSWORD, new CreateCommand(expectedAccount));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCommand.MESSAGE_USAGE);

        // missing username
        assertParseFailure(parser, VALID_PASSWORD, expectedMessage);

        // missing password
        assertParseFailure(parser, VALID_USERNAME, expectedMessage);

        // empty input
        assertParseFailure(parser, "", expectedMessage);

        // missing 1 prefix
        assertParseFailure(parser, VALID_USERNAME + DEFAULT_PASSWORD, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, DEFAULT_USERNAME + DEFAULT_PASSWORD, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid username
        assertParseFailure(parser, INVALID_USERNAME + VALID_PASSWORD, Username.MESSAGE_USERNAME_CONSTRAINT);

        // invalid password
        assertParseFailure(parser, VALID_USERNAME + INVALID_PASSWORD, Password.MESSAGE_PASSWORD_CONSTRAINT);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_USERNAME + INVALID_PASSWORD, Username.MESSAGE_USERNAME_CONSTRAINT);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_USERNAME + VALID_PASSWORD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCommand.MESSAGE_USAGE));
    }
}
