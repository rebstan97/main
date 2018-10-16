package seedu.address.model.menu;

import static java.util.Objects.requireNonNull;

/**
 * Represents an Item's remark in the menu.
 * Guarantees: immutable; Always valid
 */
public class Remark {
    private final String value;

    /**
     * Constructs a remark.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                    && value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
