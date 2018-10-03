package seedu.address.model.accounts;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import at.favre.lib.crypto.bcrypt.BCrypt;
import seedu.address.model.accounts.exceptions.PasswordHashException;

/**
 * Represents an account password. Guarantees: immutable; is valid as declared in {@link #isValidPassword(String)}
 */
public class Password {

    /*
     * In actual practice, it is recommended not to restrict the max password length. In the case
     * of this module, let us assume that the average user's password length of between 6 to 20 characters.
     */
    public static final String MESSAGE_PASSWORD_CONSTRAINT =
            "Password should not contain spaces, and must be at least length of 6.";

    /*
     * The first character of the password must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    private static final String PASSWORD_VALIDATION_REGEX = "[\\p{ASCII}&&[\\S]]{6,}";

    private static final int MAX_SALT_LENGTH = 16;

    private String password;

    /**
     * Constructs a {@code Password}.
     *
     * @param password A valid password.
     */
    public Password(String password) {
        requireNonNull(password);
        checkArgument(isValidPassword(password), MESSAGE_PASSWORD_CONSTRAINT);
        this.password = password;
    }

    /**
     * Returns true if a given string is a valid password, including both raw and hashed password.
     */
    public static boolean isValidPassword(String password) {
        return password.matches(PASSWORD_VALIDATION_REGEX);
    }

    /**
     * Hash the password.
     *
     * @param username The username to generate the salt from.
     */
    public void hash(String username) {
        byte[] salt = generateSalt(username);

        if (salt.length != 16) {
            throw new PasswordHashException();
        }

        byte[] hash = BCrypt.withDefaults().hash(6, salt, password.getBytes());
        BCrypt.Result result = BCrypt.verifyer().verify(password.getBytes(StandardCharsets.UTF_8), hash);
        if (!result.verified || !result.validFormat) {
            throw new PasswordHashException();
        }
        password = new String(hash);
    }

    /**
     * Generates a 16-bytes long salt using the {@code username}. If the {@code username}'s length is more than 16, it
     * will be truncated to use only the first 16 characters to generate the salt. Otherwise, the remaining spaces will
     * be filled up with the ASCII character 'A'.
     *
     * @param username The username to generate the salt from.
     * @return a sequence of bytes whose length is exactly 16.
     */
    private byte[] generateSalt(String username) {
        if (username.length() > MAX_SALT_LENGTH) {
            username = username.substring(0, MAX_SALT_LENGTH + 1);
            assert username.getBytes().length == MAX_SALT_LENGTH;
            return username.getBytes();
        }

        // username is shorter than 16 characters, fill up the remaining gaps
        char[] usernameArray = Arrays.copyOf(username.toCharArray(), MAX_SALT_LENGTH);
        for (int i = username.length(); i < MAX_SALT_LENGTH; i++) {
            usernameArray[i] = '@';
        }

        byte[] result = String.valueOf(usernameArray).getBytes();
        assert result.length == MAX_SALT_LENGTH;
        return result;
    }

    @Override
    public String toString() {
        return password;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Password // instanceof handles nulls
                && password.equals(((Password) other).password)); // state check
    }

    @Override
    public int hashCode() {
        return password.hashCode();
    }
}
