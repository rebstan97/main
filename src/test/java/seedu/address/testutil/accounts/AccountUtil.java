package seedu.address.testutil.accounts;

import static seedu.address.logic.parser.util.CliSyntax.PREFIX_NEW_PASSWORD;

import seedu.address.logic.commands.accounts.ChangePasswordCommand.EditAccountDescriptor;

/**
 * A utility class for Account.
 */
public class AccountUtil {

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditAccountDescriptorDetails(EditAccountDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        //descriptor.getUsername().ifPresent(username -> sb.append(PREFIX_ID).append(username.toString()).append(" "));
        descriptor.getPassword().ifPresent(password -> sb.append(PREFIX_NEW_PASSWORD).append(password.toString())
                .append(" "));
        return sb.toString();
    }
}
