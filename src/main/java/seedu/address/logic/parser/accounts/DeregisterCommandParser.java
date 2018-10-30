package seedu.address.logic.parser.accounts;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.address.logic.commands.accounts.DeregisterCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.accounts.Account;
import seedu.address.model.accounts.Username;

/**
 * Parses input arguments and creates a new RegisterCommand object
 */
public class DeregisterCommandParser implements Parser<DeregisterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeregisterCommand and returns a
     * DeregisterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeregisterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_ID) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeregisterCommand.MESSAGE_USAGE));
        }

        Username username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_ID).get());

        // Password not required since it does not involve a password to deregister the account
        return new DeregisterCommand(new Account(username));
    }
}
