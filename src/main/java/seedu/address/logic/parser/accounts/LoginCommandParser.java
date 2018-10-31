package seedu.address.logic.parser.accounts;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.util.ParserUtil.arePrefixesPresent;

import seedu.address.logic.commands.accounts.LoginCommand;
import seedu.address.logic.parser.util.ArgumentMultimap;
import seedu.address.logic.parser.util.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.accounts.Account;
import seedu.address.model.accounts.Password;
import seedu.address.model.accounts.Username;

/**
 * Parses input arguments and creates a new LoginCommand object
 */
public class LoginCommandParser implements Parser<LoginCommand> {
    //TODO: This class is very similar to RegisterCommandParser. Perhaps can unify it.

    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand and returns a LoginCommand object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ID, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_ID, PREFIX_PASSWORD) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }

        Username username = AccountParserUtil.parseUsername(argMultimap.getValue(PREFIX_ID).get());
        Password password = AccountParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get());

        Account account = new Account(username, password);

        return new LoginCommand(account);
    }
}
