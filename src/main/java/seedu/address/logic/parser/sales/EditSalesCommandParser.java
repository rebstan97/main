package seedu.address.logic.parser.sales;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY_SOLD;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.sales.EditSalesCommand;
import seedu.address.logic.commands.sales.EditSalesCommand.EditRecordDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditSalesCommand object
 */
public class EditSalesCommandParser implements Parser<EditSalesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditSalesCommand
     * and returns an EditSalesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditSalesCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_ITEM_NAME, PREFIX_QUANTITY_SOLD,
                        PREFIX_ITEM_PRICE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditSalesCommand.MESSAGE_USAGE), pe);
        }

        EditRecordDescriptor editRecordDescriptor = new EditRecordDescriptor();
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editRecordDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_ITEM_NAME).isPresent()) {
            editRecordDescriptor.setName(ParserUtil.parseItemName(argMultimap.getValue(PREFIX_ITEM_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_QUANTITY_SOLD).isPresent()) {
            editRecordDescriptor.setQuantitySold(ParserUtil.parseQuantitySold(argMultimap.getValue(PREFIX_QUANTITY_SOLD)
                    .get()));
        }
        if (argMultimap.getValue(PREFIX_ITEM_PRICE).isPresent()) {
            editRecordDescriptor.setPrice(ParserUtil.parsePrice(argMultimap.getValue(PREFIX_ITEM_PRICE).get()));
        }

        if (!editRecordDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditSalesCommand.MESSAGE_NOT_EDITED);
        }

        return new EditSalesCommand(index, editRecordDescriptor);
    }
}
