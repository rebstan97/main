package seedu.address.logic.parser.menu;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.util.ParserUtil.arePrefixesPresent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import seedu.address.logic.commands.menu.AddItemCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.util.ArgumentMultimap;
import seedu.address.logic.parser.util.ArgumentTokenizer;
import seedu.address.logic.parser.util.ParserUtil;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.menu.Item;
import seedu.address.model.menu.Name;
import seedu.address.model.menu.Price;
import seedu.address.model.menu.Recipe;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddItemCommand object
 */
public class AddItemCommandParser implements Parser<AddItemCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddItemCommand
     * and returns an AddItemCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddItemCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PRICE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PRICE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddItemCommand.MESSAGE_USAGE));
        }

        Name name = ItemParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Price price = ItemParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get());
        Recipe recipe = new Recipe("");
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Map<IngredientName, Integer> requiredIngredients = new HashMap<>();

        Item item = new Item(name, price, recipe, tagList, requiredIngredients);

        return new AddItemCommand(item);
    }
}
