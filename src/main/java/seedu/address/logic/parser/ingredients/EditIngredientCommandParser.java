package seedu.address.logic.parser.ingredients;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_MINIMUM;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_PRICE;
import static seedu.address.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_UNIT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ingredients.EditIngredientByIndexCommand;
import seedu.address.logic.commands.ingredients.EditIngredientByNameCommand;
import seedu.address.logic.commands.ingredients.EditIngredientCommand;
import seedu.address.logic.commands.ingredients.EditIngredientCommand.EditIngredientDescriptor;
import seedu.address.logic.parser.util.ArgumentMultimap;
import seedu.address.logic.parser.util.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.util.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.IngredientName;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditIngredientCommandParser implements Parser<EditIngredientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditIngredientCommand
     * and returns an EditIngredientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditIngredientCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INGREDIENT_NAME, PREFIX_INGREDIENT_UNIT,
                        PREFIX_INGREDIENT_PRICE, PREFIX_INGREDIENT_MINIMUM);

        String indexOrNameArg = argMultimap.getPreamble();

        if (indexOrNameArg.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditIngredientCommand.MESSAGE_USAGE));
        }

        Object indexOrName = ParserUtil.parseIndexOrIngredientName(indexOrNameArg);

        EditIngredientDescriptor editIngredientDescriptor = new EditIngredientDescriptor();
        setNameDescriptor(argMultimap, editIngredientDescriptor);
        setUnitDescriptor(argMultimap, editIngredientDescriptor);
        setPriceDescriptor(argMultimap, editIngredientDescriptor);
        setMinDescriptor(argMultimap, editIngredientDescriptor);

        if (!editIngredientDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditIngredientCommand.MESSAGE_NOT_EDITED);
        }

        EditIngredientCommand editCommand = null;
        Index index;
        IngredientName name;

        if (indexOrName instanceof Index) {
            index = (Index) indexOrName;
            editCommand = new EditIngredientByIndexCommand(index, editIngredientDescriptor);
        }
        if (indexOrName instanceof IngredientName) {
            name = (IngredientName) indexOrName;
            editCommand = new EditIngredientByNameCommand(name, editIngredientDescriptor);
        }

        return editCommand;
    }

    private void setMinDescriptor(ArgumentMultimap argMultimap, EditIngredientDescriptor editIngredientDescriptor)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_INGREDIENT_MINIMUM).isPresent()) {
            editIngredientDescriptor.setMinimum(
                    ParserUtil.parseMinimumUnit(argMultimap.getValue(PREFIX_INGREDIENT_MINIMUM).get()));
        }
    }

    private void setPriceDescriptor(ArgumentMultimap argMultimap, EditIngredientDescriptor editIngredientDescriptor)
            throws ParseException {
        setUnitDescriptor(argMultimap, editIngredientDescriptor);
        if (argMultimap.getValue(PREFIX_INGREDIENT_PRICE).isPresent()) {
            editIngredientDescriptor.setPrice(
                    ParserUtil.parseIngredientPrice(argMultimap.getValue(PREFIX_INGREDIENT_PRICE).get()));
        }
    }

    private void setUnitDescriptor(ArgumentMultimap argMultimap, EditIngredientDescriptor editIngredientDescriptor)
            throws ParseException {
        setNameDescriptor(argMultimap, editIngredientDescriptor);
        if (argMultimap.getValue(PREFIX_INGREDIENT_UNIT).isPresent()) {
            editIngredientDescriptor.setUnit(
                    ParserUtil.parseIngredientUnit(argMultimap.getValue(PREFIX_INGREDIENT_UNIT).get()));
        }
    }

    private void setNameDescriptor(ArgumentMultimap argMultimap, EditIngredientDescriptor editIngredientDescriptor)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_INGREDIENT_NAME).isPresent()) {
            editIngredientDescriptor.setName(
                    ParserUtil.parseIngredientName(argMultimap.getValue(PREFIX_INGREDIENT_NAME).get()));
        }
    }

}
