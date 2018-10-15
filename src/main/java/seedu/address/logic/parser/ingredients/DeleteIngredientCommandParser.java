package seedu.address.logic.parser.ingredients;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INDEX_OR_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ingredients.DeleteIngredientCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.IngredientName;

/**
 * Parses input arguments and creates a new DeleteIngredientCommand object
 */
public class DeleteIngredientCommandParser implements Parser<DeleteIngredientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteIngredientCommand
     * and returns an DeleteIngredientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteIngredientCommand parse(String args) throws ParseException {
        try {
            Object indexOrName = ParserUtil.parseIndexOrIngredientName(args);
            if (indexOrName instanceof Index) {
                return new DeleteIngredientCommand((Index)indexOrName);
            }
            if (indexOrName instanceof IngredientName) {
                return new DeleteIngredientCommand((IngredientName)indexOrName);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_INDEX_OR_NAME));
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            DeleteIngredientCommand.MESSAGE_USAGE), pe);
        }
    }

}
