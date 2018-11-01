package seedu.address.logic.parser.ingredient;

import static java.lang.Integer.parseInt;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.util.ParserUtil.MESSAGE_NOT_INDEX_OR_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientPrice;
import seedu.address.model.ingredient.IngredientUnit;
import seedu.address.model.ingredient.MinimumUnit;
import seedu.address.model.ingredient.NumUnits;

/**
 * Contains utility methods used for parsing strings ingredient-related classes.
 */
public class IngredientParserUtil {

    // This class should not be instantiated.
    private IngredientParserUtil() {
        throw new AssertionError("IngredientParserUtil should not be instantiated.");
    }

    /**
     * Parses a {@code String name} into an {@code IngredientName}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static IngredientName parseIngredientName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!IngredientName.isValidName(trimmedName)) {
            throw new ParseException(IngredientName.MESSAGE_NAME_CONSTRAINTS);
        }
        return new IngredientName(trimmedName);
    }

    /**
     * Parses a {@code String indexOrName} into an {@code Index} or {@code IngredientName}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if neither the {@code Index} nor {@code IngredientName} is valid.
     */
    public static Object parseIndexOrIngredientName(String indexOrName) throws ParseException {
        requireNonNull(indexOrName);
        String trimmedIndexOrName = indexOrName.trim();

        if (StringUtil.isNonZeroUnsignedInteger(trimmedIndexOrName)) {
            return Index.fromOneBased(parseInt(trimmedIndexOrName));
        }

        if (IngredientName.isValidName(trimmedIndexOrName)) {
            return new IngredientName(trimmedIndexOrName);
        }

        throw new ParseException(MESSAGE_NOT_INDEX_OR_NAME);
    }


    /**
     * Parses a {@code String unit} into an {@code IngredientUnit}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code IngredientUnit} is invalid.
     */
    public static IngredientUnit parseIngredientUnit(String unit) throws ParseException {
        requireNonNull(unit);
        String trimmedUnit = unit.trim();
        if (!IngredientUnit.isValidUnit(trimmedUnit)) {
            throw new ParseException(IngredientUnit.MESSAGE_UNIT_CONSTRAINTS);
        }
        return new IngredientUnit(trimmedUnit);
    }

    /**
     * Parses a {@code String price} into an {@code IngredientPrice}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code IngredientPrice} is invalid.
     */
    public static IngredientPrice parseIngredientPrice(String price) throws ParseException {
        requireNonNull(price);
        String trimmedPrice = price.trim();
        if (!IngredientPrice.isValidPrice(trimmedPrice)) {
            throw new ParseException(IngredientPrice.MESSAGE_PRICE_CONSTRAINTS);
        }
        return new IngredientPrice(trimmedPrice);
    }

    /**
     * Parses a {@code String minimum} into a {@code MinimumUnit}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code MinimumUnit} is invalid.
     */
    public static MinimumUnit parseMinimumUnit(String minimum) throws ParseException {
        requireNonNull(minimum);
        String trimmedMinimum = minimum.trim();
        if (!StringUtil.isUnsignedInteger(trimmedMinimum)) {
            throw new ParseException(MinimumUnit.MESSAGE_MINIMUM_CONSTRAINTS);
        }
        return new MinimumUnit(parseInt(trimmedMinimum));
    }

    /**
     * Parses a {@code String numUnits} into a {@code NumUnits}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code NumUnit} is invalid.
     */
    public static NumUnits parseNumUnits(String numUnits) throws ParseException {
        requireNonNull(numUnits);
        String trimmedNumUnits = numUnits.trim();
        if (!StringUtil.isUnsignedInteger(trimmedNumUnits)) {
            throw new ParseException(NumUnits.MESSAGE_NUMUNITS_CONSTRAINTS);
        }
        return new NumUnits(parseInt(trimmedNumUnits));
    }
}
