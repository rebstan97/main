package seedu.address.model.ingredient;

import static java.lang.Integer.parseInt;
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

    private Integer numberOfUnits;

    /**
     * Constructs a {@code NumUnits}.
     *
     * @param numUnits A valid number of available units.
     */
    public NumUnits(String numUnits) {
        requireNonNull(numUnits);
        checkArgument(isValidNumUnits(numUnits), MESSAGE_NUMUNITS_CONSTRAINTS);
        numberOfUnits = parseInt(numUnits);
    }

    /**
     * Returns true if a given string is a valid number of available units.
     */
    public static boolean isValidNumUnits(String test) {
        return test.matches(NUMUNITS_VALIDATION_REGEX);
    }

    /**
     * Returns the value of {@code numberOfUnits}.
     */
    public Integer getNumberOfUnits() {
        return numberOfUnits;
    }

    @Override
    public String toString() {
        return numberOfUnits.toString();
    }

    /**
     * Increases the number of units by {@code toIncrease}.
     * @return A new {@code NumUnits} object with the updated value.
     */
    public NumUnits increase(Integer toIncrease) {
        requireNonNull(toIncrease);
        Integer updatedNum = numberOfUnits + toIncrease;
        return new NumUnits(updatedNum.toString());
    }

    /**
     * Decreases the number of units by {@code toDecrease}.
     * @return A new {@code NumUnits} object with the updated value.
     */
    public NumUnits decrease(Integer toDecrease) {
        requireNonNull(toDecrease);
        assert(toDecrease <= numberOfUnits);
        Integer updatedNum = numberOfUnits - toDecrease;
        return new NumUnits(updatedNum.toString());
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
