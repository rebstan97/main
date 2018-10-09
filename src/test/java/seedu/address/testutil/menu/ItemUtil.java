package seedu.address.testutil.menu;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.menu.AddItemCommand;
import seedu.address.model.menu.Item;

/**
 * A utility class for Item.
 */
public class ItemUtil {

    /**
     * Returns an add command string for adding the {@code item}.
     */
    public static String getAddItemCommand(Item item) {
        return AddItemCommand.COMMAND_WORD + " " + getItemDetails(item);
    }

    /**
     * Returns the part of command string for the given {@code item}'s details.
     */
    public static String getItemDetails(Item item) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + item.getName().fullName + " ");
        sb.append(PREFIX_PRICE + item.getPrice().value + " ");
        item.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
