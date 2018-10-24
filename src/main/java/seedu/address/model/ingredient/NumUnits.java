package seedu.address.model.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the number of available units of an Ingredient.
 * Guarantees: immutable; is valid as declared in {@link #isValidNumUnits(String)}
 */
public class NumUnits {

    public static final String MESSAGE_NUMUNITS_CONSTRAINTS =
            "Number of units should only contain numeric characters, and it should not be blank";

    /*
     * The first character of the price must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NUMUNITS_VALIDATION_REGEX = "^\\d+$";

    private String numberOfUnits;

    /**
     * Constructs a {@code NumUnits}.
     *
     * @param numUnits A valid number of available units.
     */
    public NumUnits(String numUnits) {
        requireNonNull(numUnits);
        checkArgument(isValidNumUnits(numUnits), MESSAGE_NUMUNITS_CONSTRAINTS);
        numberOfUnits = numUnits;
    }

    /**
     * Returns true if a given string is a valid number of available units.
     */
    public static boolean isValidNumUnits(String test) {
        return test.matches(NUMUNITS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return numberOfUnits;
    }

    /**
     * Adds the values of two {@code NumUnits} objects and updates the value of the current object to be this sum.
     * @return The updated {@NumUnits} object
     */
    public NumUnits add(NumUnits toAdd) {
        Integer currentNum = Integer.parseInt(numberOfUnits);
        Integer numToAdd = Integer.parseInt(toAdd.toString());
        Integer updatedNum = Integer.sum(currentNum, numToAdd);
        numberOfUnits = updatedNum.toString();
        return this;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NumUnits // instanceof handles nulls
                && numberOfUnits.equals(((NumUnits) other).numberOfUnits)); // state check
    }

    @Override
    public int hashCode() {
        return numberOfUnits.hashCode();
    }
}
