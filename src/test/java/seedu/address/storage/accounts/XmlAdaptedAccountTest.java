package seedu.address.storage.accounts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.storage.accounts.XmlAdaptedAccount.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_ADMIN;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_ONE;
import static seedu.address.testutil.accounts.TypicalAccounts.DEMO_TWO;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.accounts.Password;
import seedu.address.model.accounts.Username;
import seedu.address.testutil.Assert;
import seedu.address.testutil.accounts.AccountBuilder;

public class XmlAdaptedAccountTest {

    private static final String INVALID_USERNAME = "a zhikai";
    private static final String INVALID_PASSWORD = "11 22qq";

    private static final String VALID_USERNAME = DEMO_ADMIN.getUsername().toString();
    private static final String VALID_PASSWORD = DEMO_ADMIN.getPassword().toString();

    private XmlAdaptedAccount account = null;

    @Test
    public void toModelType_validAccountDetails_returnsAccount() throws Exception {
        account = new XmlAdaptedAccount(DEMO_ADMIN);
        assertEquals(DEMO_ADMIN, account.toModelType());
    }

    @Test
    public void toModelType_nullUsername_throwsIllegalValueException() {
        account = new XmlAdaptedAccount(null, VALID_PASSWORD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Username.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }

    @Test
    public void toModelType_invalidUsername_throwsIllegalValueException() {
        account = new XmlAdaptedAccount(INVALID_USERNAME, VALID_PASSWORD);
        String expectedMessage = Username.MESSAGE_USERNAME_CONSTRAINT;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }

    @Test
    public void toModelType_nullPassword_throwsIllegalValueException() {
        account = new XmlAdaptedAccount(VALID_USERNAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Password.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }

    @Test
    public void toModelType_invalidPassword_throwsIllegalValueException() {
        account = new XmlAdaptedAccount(VALID_USERNAME, INVALID_PASSWORD);
        String expectedMessage = Password.MESSAGE_PASSWORD_CONSTRAINT;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }

    @Test
    public void equals() throws Exception {
        XmlAdaptedAccount accountDemoOne = new XmlAdaptedAccount(new AccountBuilder(DEMO_ONE).build());

        // same object
        assertEquals(accountDemoOne, accountDemoOne);

        XmlAdaptedAccount accountDemoOneDuplicate = new XmlAdaptedAccount(new AccountBuilder(DEMO_ONE).build());

        // different object, same state
        assertEquals(accountDemoOne, accountDemoOneDuplicate);

        XmlAdaptedAccount accountDemoTwo = new XmlAdaptedAccount(new AccountBuilder(DEMO_TWO).build());

        assertNotEquals(accountDemoOne, accountDemoTwo);

        // not same instance
        assertNotEquals(1, accountDemoOne);

        // not same instance
        assertNotEquals(null, accountDemoOne);
    }
}
