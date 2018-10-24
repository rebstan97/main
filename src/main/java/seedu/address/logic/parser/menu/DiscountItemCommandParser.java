package seedu.address.logic.parser.menu;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDINGINDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERCENT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.menu.DiscountItemCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DiscountItemCommand object
 */
public class DiscountItemCommandParser implements Parser<DiscountItemCommand> {
    public static final String MESSAGE_PERCENT_CONSTRAINTS =
            "Percent should only contain numbers, and it should be at most 2 digits";
    public static final String PERCENT_VALIDATION_REGEX = "\\d{0,2}";
    public static final String ALL_VALIDATION = "ALL";

    private boolean isALL = false;

    /**
     * Parses the given {@code String} of arguments in the context of the DiscountItemCommand
     * and returns an DiscountItemCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DiscountItemCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ENDINGINDEX, PREFIX_PERCENT);

        Index index = Index.fromZeroBased(0); //Dummy value
        if (StringUtil.isNonZeroUnsignedInteger(argMultimap.getPreamble())) {
            index = Index.fromOneBased(Integer.parseInt(argMultimap.getPreamble()));
        } else if (isValidALL(argMultimap.getPreamble())) {
            isALL = true;
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DiscountItemCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getValue(PREFIX_PERCENT).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DiscountItemCommand.MESSAGE_USAGE));
        }
        String trimmedPercent = argMultimap.getValue(PREFIX_PERCENT).get().trim();
        if (!isValidPercent(trimmedPercent)) {
            throw new ParseException(MESSAGE_PERCENT_CONSTRAINTS);
        }
        double percent = Double.parseDouble(trimmedPercent);

        Index endingIndex = index;
        if (argMultimap.getValue(PREFIX_ENDINGINDEX).isPresent()) {
            try {
                 endingIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ENDINGINDEX).get());
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DiscountItemCommand.MESSAGE_USAGE), pe);
            }
        }

        if (endingIndex.getZeroBased() < index.getZeroBased()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DiscountItemCommand.MESSAGE_USAGE));
        }

        return new DiscountItemCommand(index, endingIndex, percent, isALL);
    }

    /**
     * Returns true if a given string is a valid percent.
     */
    private static boolean isValidPercent(String test) {
        return test.matches(PERCENT_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is equals to ALL.
     */
    private static boolean isValidALL(String test) {
        return test.toUpperCase().equals(ALL_VALIDATION);
    }

}
