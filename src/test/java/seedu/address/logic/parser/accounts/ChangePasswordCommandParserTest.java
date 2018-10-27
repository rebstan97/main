package seedu.address.logic.parser.accounts;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREFIX_WITH_INVALID_NEW_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_NEW_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_DEMO_ONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_PASSWORD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.accounts.ChangePasswordCommand;
import seedu.address.logic.commands.accounts.ChangePasswordCommand.EditAccountDescriptor;
import seedu.address.model.accounts.Password;
import seedu.address.testutil.accounts.EditAccountDescriptorBuilder;

public class ChangePasswordCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            ChangePasswordCommand.MESSAGE_USAGE);

    private ChangePasswordCommandParser parser = new ChangePasswordCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid password
        assertParseFailure(parser, "random " + PREFIX_WITH_VALID_NEW_PASSWORD, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 " + PREFIX_WITH_VALID_NEW_PASSWORD, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, " " + PREFIX_WITH_INVALID_NEW_PASSWORD,
                Password.MESSAGE_PASSWORD_CONSTRAINT); // invalid password
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        EditAccountDescriptor descriptor = new EditAccountDescriptorBuilder().withPassword(VALID_PASSWORD_DEMO_ONE)
                .build();
        ChangePasswordCommand expectedCommand = new ChangePasswordCommand(descriptor);

        assertParseSuccess(parser, " " + PREFIX_NEW_PASSWORD + VALID_PASSWORD_DEMO_ONE, expectedCommand);
    }
}
