package seedu.address.logic.parser.accounts;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_PASSWORD;

import seedu.address.commons.core.session.UserSession;
import seedu.address.logic.commands.accounts.ChangePasswordCommand;
import seedu.address.logic.commands.accounts.ChangePasswordCommand.EditAccountDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.accounts.Account;

/**
 * Parses input arguments and creates a new ChangePasswordCommand object
 */
public class ChangePasswordCommandParser implements Parser<ChangePasswordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ChangePasswordCommand and returns an
     * ChangePasswordCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangePasswordCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NEW_PASSWORD);

        EditAccountDescriptor editAccountDescriptor = new EditAccountDescriptor();
        if (argMultimap.getValue(PREFIX_NEW_PASSWORD).isPresent()) {
            editAccountDescriptor.setPassword(ParserUtil.parsePassword(argMultimap.getValue(PREFIX_NEW_PASSWORD)
                    .get()));
        }

        if (!editAccountDescriptor.isAnyFieldEdited()) {
            throw new ParseException(ChangePasswordCommand.MESSAGE_NOT_EDITED);
        }

        Account currentAccount = new Account(UserSession.getUsername(), UserSession.getPassword());

        return new ChangePasswordCommand(currentAccount, editAccountDescriptor);
    }
}
