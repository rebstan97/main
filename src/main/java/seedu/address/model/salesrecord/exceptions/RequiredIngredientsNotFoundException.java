package seedu.address.model.salesrecord.exceptions;

/**
 * Signal that the ingredients required to make the item has not been specified in menu section.
 */
public class RequiredIngredientsNotFoundException extends RuntimeException {
    public RequiredIngredientsNotFoundException() {
        super("Required ingredients not specified in menu.");
    }
}
