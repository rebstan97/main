package seedu.restaurant.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_REMARK;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.commands.RemarkCommand;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ArgumentMultimap;
import seedu.restaurant.logic.parser.util.ArgumentTokenizer;
import seedu.restaurant.logic.parser.util.ParserUtil;
import seedu.restaurant.model.person.Remark;

/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an RemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE), pe);
        }

        String remark = argMultimap.getValue(PREFIX_REMARK).orElse("");
        return new RemarkCommand(index, new Remark(remark));
    }
}
