package seedu.address.model.accounts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PasswordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Password(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidPassword = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Password(invalidPassword));
    }

    @Test
    public void isValidPassword() {
        // null password
        Assert.assertThrows(NullPointerException.class, () -> Password.isValidPassword(null));

        // invalid password
        assertFalse(Password.isValidPassword("")); // empty string
        assertFalse(Password.isValidPassword(" ")); // spaces only
        assertFalse(Password.isValidPassword("11 22qq")); // contains space

        // valid password
        assertTrue(Password.isValidPassword("azhikai")); // alphabets only
        assertTrue(Password.isValidPassword("1122qq")); // numbers only
        assertTrue(Password.isValidPassword("peter2")); // alphanumeric characters
        assertTrue(Password.isValidPassword("CapitalAng")); // with capital letters
        assertTrue(Password.isValidPassword("th15isAPassword")); // long alphanumeric password
        assertTrue(Password.isValidPassword("!!@@SGMY3-1")); // alphanumeric and special characters
    }
}
