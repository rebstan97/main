package seedu.address.logic.parser.reservation;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.address.logic.commands.reservation.SelectReservationCommand;

/**
 * Test scope: similar to {@code DeleteReservationCommandParserTest}.
 * @see DeleteReservationCommandParserTest
 */
public class SelectReservationCommandParserTest {

    private SelectReservationCommandParser parser = new SelectReservationCommandParser();

    @Test
    public void parse_validArgs_returnsSelectReservationCommand() {
        assertParseSuccess(parser, "1", new SelectReservationCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectReservationCommand.MESSAGE_USAGE));
    }
}
