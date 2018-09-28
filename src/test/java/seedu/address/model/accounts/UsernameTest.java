package seedu.address.model.accounts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class UsernameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Username(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidUsername = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Username(invalidUsername));
    }

    @Test
    public void isValidUsername() {
        // null username
        Assert.assertThrows(NullPointerException.class, () -> Username.isValidUsername(null));

        // invalid username
        assertFalse(Username.isValidUsername("")); // empty string
        assertFalse(Username.isValidUsername(" ")); // spaces only
        assertFalse(Username.isValidUsername("^")); // only non-alphanumeric characters
        assertFalse(Username.isValidUsername("peter*")); // contains non-alphanumeric characters
        assertFalse(Username.isValidUsername("azhi kai")); // contains space

        // valid username
        assertTrue(Username.isValidUsername("azhikai")); // alphabets only
        assertTrue(Username.isValidUsername("12345")); // numbers only
        assertTrue(Username.isValidUsername("peter2")); // alphanumeric characters
        assertTrue(Username.isValidUsername("CapitalAng")); // with capital letters
        assertTrue(Username.isValidUsername("ThisIsALongUsernameAlthoughWhoWouldDoThisRight")); // long names
    }
}
