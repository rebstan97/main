package seedu.restaurant.logic.parser.account;

import static org.junit.Assert.assertEquals;
import static seedu.restaurant.testutil.Assert.assertThrows;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.model.account.Password;
import seedu.restaurant.model.account.Username;

public class AccountParserUtilTest {

    private static final String VALID_USERNAME = "azhikai";
    private static final String VALID_PASSWORD = "1122qq";
    private static final String INVALID_USERNAME = "a zhikai";
    private static final String INVALID_PASSWORD = "1 122qq";

    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseUsername_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        AccountParserUtil.parseUsername(INVALID_USERNAME);
    }

    @Test
    public void parsePassword_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        AccountParserUtil.parsePassword(INVALID_PASSWORD);
    }

    @Test
    public void parseUsername_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AccountParserUtil.parseUsername(null));
    }

    @Test
    public void parsePassword_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AccountParserUtil.parsePassword(null));
    }

    @Test
    public void parseUsername_validValueWithoutWhitespace_returnsName() throws Exception {
        Username expectedUsername = new Username(VALID_USERNAME);
        assertEquals(expectedUsername, AccountParserUtil.parseUsername(VALID_USERNAME));
    }

    @Test
    public void parseUsername_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String usernameWithWhitespace = WHITESPACE + VALID_USERNAME + WHITESPACE;
        Username expectedUsername = new Username(VALID_USERNAME);
        assertEquals(expectedUsername, AccountParserUtil.parseUsername(usernameWithWhitespace));
    }

    @Test
    public void parsePassword_validValueWithoutWhitespace_returnsName() throws Exception {
        Password expectedPassword = new Password(VALID_PASSWORD);
        assertEquals(expectedPassword, AccountParserUtil.parsePassword(VALID_PASSWORD));
    }

    @Test
    public void parsePassword_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String passwordWithWhitespace = WHITESPACE + VALID_PASSWORD + WHITESPACE;
        Password expectedPassword = new Password(VALID_PASSWORD);
        assertEquals(expectedPassword, AccountParserUtil.parsePassword(passwordWithWhitespace));
    }
}
