package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! A valid index or name "
            + "must be provided. \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";

    // Ingredient Management
    public static final String MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX = "The ingredient index provided is invalid";
    public static final String MESSAGE_INGREDIENT_NAME_NOT_FOUND = "The ingredient name provided does not exist";

    // Menu Management
    public static final String MESSAGE_INVALID_ITEM_DISPLAYED_INDEX = "The item index provided is invalid";
    public static final String MESSAGE_ITEMS_LISTED_OVERVIEW = "%1$d items listed!";

    // Sales Management
    public static final String MESSAGE_INVALID_RECORD_DISPLAYED_INDEX = "The sales record index provided is invalid.";
}
