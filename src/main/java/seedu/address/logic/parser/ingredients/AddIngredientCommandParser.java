package seedu.address.logic.parser.ingredients;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_MINIMUM;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_PRICE;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_UNIT;
import static seedu.address.logic.parser.util.ParserUtil.arePrefixesPresent;

import seedu.address.logic.commands.ingredients.AddIngredientCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.util.ArgumentMultimap;
import seedu.address.logic.parser.util.ArgumentTokenizer;
import seedu.address.logic.parser.util.ParserUtil;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientPrice;
import seedu.address.model.ingredient.IngredientUnit;
import seedu.address.model.ingredient.MinimumUnit;
import seedu.address.model.ingredient.NumUnits;

/**
 * Parses input arguments and creates a new AddIngredientCommand object
 */
public class AddIngredientCommandParser implements Parser<AddIngredientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddIngredientCommand
     * and returns an AddIngredientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddIngredientCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INGREDIENT_NAME, PREFIX_INGREDIENT_UNIT,
                        PREFIX_INGREDIENT_PRICE, PREFIX_INGREDIENT_MINIMUM);

        if (!arePrefixesPresent(argMultimap, PREFIX_INGREDIENT_NAME, PREFIX_INGREDIENT_UNIT, PREFIX_INGREDIENT_PRICE,
                PREFIX_INGREDIENT_MINIMUM)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddIngredientCommand.MESSAGE_USAGE));
        }

        IngredientName name = ParserUtil.parseIngredientName(argMultimap.getValue(PREFIX_INGREDIENT_NAME).get());
        IngredientUnit unit = ParserUtil.parseIngredientUnit(argMultimap.getValue(PREFIX_INGREDIENT_UNIT).get());
        IngredientPrice price = ParserUtil.parseIngredientPrice(argMultimap.getValue(PREFIX_INGREDIENT_PRICE).get());
        MinimumUnit minimum = ParserUtil.parseMinimumUnit(argMultimap.getValue(PREFIX_INGREDIENT_MINIMUM).get());

        Ingredient ingredient = new Ingredient(name, unit, price, minimum, new NumUnits(0));

        return new AddIngredientCommand(ingredient);
    }
}
