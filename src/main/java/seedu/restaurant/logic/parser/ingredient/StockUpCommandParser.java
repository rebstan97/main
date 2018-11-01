package seedu.restaurant.logic.parser.ingredient;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_NUM;

import java.util.ArrayList;
import java.util.List;

import seedu.restaurant.logic.commands.ingredient.StockUpCommand;
import seedu.restaurant.logic.commands.ingredient.StockUpCommand.ChangeStockDescriptor;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ArgumentPairMultimap;
import seedu.restaurant.logic.parser.util.ArgumentTokenizer;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.ingredient.NumUnits;

/**
 * Parses input arguments and creates a new StockUpCommand object
 */
public class StockUpCommandParser implements Parser<StockUpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StockupCommand and returns a StockUpCommand
     * object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public StockUpCommand parse(String args) throws ParseException {

        if (args.trim().equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StockUpCommand.MESSAGE_USAGE));
        }

        ArgumentPairMultimap argMultimap =
                ArgumentTokenizer.tokenizeToPair(args, PREFIX_INGREDIENT_NAME, PREFIX_INGREDIENT_NUM);

        int index = 1;
        List<ChangeStockDescriptor> stockDescriptorList = new ArrayList<>();
        while (argMultimap.contains(index)) {
            IngredientName name = IngredientParserUtil
                    .parseIngredientName(argMultimap.getValue(index).getFirstString());
            NumUnits numUnits = IngredientParserUtil.parseNumUnits(argMultimap.getValue(index).getSecondString());
            ChangeStockDescriptor descriptor = new ChangeStockDescriptor(name, numUnits);
            stockDescriptorList.add(descriptor);
            index++;
        }

        return new StockUpCommand(stockDescriptorList);
    }
}
