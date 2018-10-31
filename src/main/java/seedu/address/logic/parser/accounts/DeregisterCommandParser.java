package seedu.address.logic.parser.accounts;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.util.ParserUtil.arePrefixesPresent;

import seedu.address.logic.commands.accounts.DeregisterCommand;
import seedu.address.logic.parser.util.ArgumentMultimap;
import seedu.address.logic.parser.util.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
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

        Username username = AccountParserUtil.parseUsername(argMultimap.getValue(PREFIX_ID).get());

        // Password not required since it does not involve a password to deregister the account
        return new DeregisterCommand(new Account(username));
    }
}
