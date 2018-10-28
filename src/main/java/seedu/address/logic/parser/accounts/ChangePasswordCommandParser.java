package seedu.address.logic.parser.accounts;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_PASSWORD;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.address.logic.commands.accounts.ChangePasswordCommand;
import seedu.address.logic.commands.accounts.ChangePasswordCommand.EditAccountDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

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

        if (!arePrefixesPresent(argMultimap, PREFIX_NEW_PASSWORD) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangePasswordCommand.MESSAGE_USAGE));
        }

        EditAccountDescriptor editAccountDescriptor = new EditAccountDescriptor();
        if (argMultimap.getValue(PREFIX_NEW_PASSWORD).isPresent()) {
            editAccountDescriptor.setPassword(ParserUtil.parsePassword(argMultimap.getValue(PREFIX_NEW_PASSWORD)
                    .get()));
        }

        return new ChangePasswordCommand(editAccountDescriptor);
    }
}
