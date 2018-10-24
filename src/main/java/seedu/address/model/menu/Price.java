package seedu.address.model.menu;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Item's price in the menu.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {

    public static final String MESSAGE_PRICE_CONSTRAINTS =
            "Price should only contain numbers, and it should be at most 2 decimal place";
    private static final String DECIMAL_PLACE_REGEX = "\\d{0,2}";
    public static final String PRICE_VALIDATION_REGEX = "\\d+" + ".?" + DECIMAL_PLACE_REGEX;
    private static final double MAX_PERCENT = 100.0;
    private double value;
    private final double originalValue;

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price.
     */
    public Price(String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_PRICE_CONSTRAINTS);
        originalValue = Double.parseDouble(price);
        value = Double.parseDouble(price);
    }

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price.
     */
    public Price(String price, String originalPrice) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_PRICE_CONSTRAINTS);
        originalValue = Double.parseDouble(originalPrice);
        value = Double.parseDouble(price);
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPrice(String test) {
        return test.matches(PRICE_VALIDATION_REGEX);
    }

    public double getValue() {
        return value;
    }

    public double getOriginalValue() {
        return originalValue;
    }

    public void setValue(double percent) {
        value = originalValue * ((MAX_PERCENT - percent) / MAX_PERCENT);
    }

    @Override
    public String toString() {
        //return valueInString;
        return String.format("%.2f", value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Price // instanceof handles nulls
                    && value == ((Price) other).value // state check
                    && originalValue == ((Price) other).originalValue);
    }

    @Override
    public int hashCode() {
        return Double.valueOf(value).hashCode();
    }

}
