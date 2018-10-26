package seedu.address.testutil.accounts;

import seedu.address.logic.commands.accounts.ChangePasswordCommand.EditAccountDescriptor;
import seedu.address.model.accounts.Account;
import seedu.address.model.accounts.Password;
import seedu.address.model.accounts.Username;

/**
 * A utility class to help with building EditAccountDescriptor objects.
 */
public class EditAccountDescriptorBuilder {

    private EditAccountDescriptor descriptor;

    public EditAccountDescriptorBuilder() {
        descriptor = new EditAccountDescriptor();
    }

    public EditAccountDescriptorBuilder(EditAccountDescriptor descriptor) {
        this.descriptor = new EditAccountDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditAccountDescriptor} with fields containing {@code account}'s details
     */
    public EditAccountDescriptorBuilder(Account account) {
        descriptor = new EditAccountDescriptor();
        descriptor.setUsername(account.getUsername());
        descriptor.setPassword(account.getPassword());
    }

    /**
     * Sets the {@code Username} of the {@code EditAccountDescriptor} that we are building.
     */
    public EditAccountDescriptorBuilder withUsername(String username) {
        descriptor.setUsername(new Username(username));
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code EditAccountDescriptor} that we are building.
     */
    public EditAccountDescriptorBuilder withPassword(String password) {
        descriptor.setPassword(new Password(password));
        return this;
    }

    public EditAccountDescriptor build() {
        return descriptor;
    }
}
