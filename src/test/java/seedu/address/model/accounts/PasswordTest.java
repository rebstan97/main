package seedu.address.model.accounts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_DEMO_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_DEMO_TWO;

import org.junit.Before;
import org.junit.Test;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Hasher;
import seedu.address.testutil.Assert;

public class PasswordTest {

    private Hasher hasher;

    @Before
    public void setUp() {
        hasher = BCrypt.withDefaults();
    }

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

    @Test
    public void hash_password() {
        // compare the hash of the same password
        Password password = new Password("1122qq");
        char[] hashPassword = hasher.hashToChar(6, password.toString().toCharArray());
        BCrypt.Result resultPassword = BCrypt.verifyer().verify(password.toString().toCharArray(), hashPassword);
        assertTrue(resultPassword.verified);

        // compare the hash of two different passwords
        Password anotherPassword = new Password("1122qq@");
        char[] hashAnotherPassword = hasher.hashToChar(6, anotherPassword.toString().toCharArray());
        BCrypt.Result resultAnotherPassword = BCrypt.verifyer().verify(password.toString().toCharArray(),
                hashAnotherPassword);
        assertFalse(resultAnotherPassword.verified);
    }

    @Test
    public void hash_code() {
        Password passwordOne = new Password(VALID_PASSWORD_DEMO_ONE);

        assertEquals(passwordOne.hashCode(), passwordOne.hashCode());

        Password passwordTwo = new Password(VALID_PASSWORD_DEMO_TWO);

        assertNotEquals(passwordOne.hashCode(), passwordTwo.hashCode());
    }
}
