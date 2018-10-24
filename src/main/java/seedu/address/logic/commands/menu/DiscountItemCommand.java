package seedu.address.logic.commands.menu;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERCENT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.DisplayItemListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.menu.Item;
import seedu.address.model.menu.Price;

/**
 * Gives an existing item in the menu a discount.
 */
public class DiscountItemCommand extends Command {

    public static final String COMMAND_WORD = "discount-item";

    public static final String COMMAND_ALIAS = "dci";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gives the item identified "
            + "by the index number used in the displayed item list a discount based on the percent. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_PERCENT + "PRICE] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PERCENT + "20";

    public static final String MESSAGE_DISCOUNT_ITEM_SUCCESS = "Discounted Item: %1$s";

    private final Index index;
    private final double percent;

    /**
     * @param index of the item in the filtered item list to edit
     * @param percent the percent of the discount
     */
    public DiscountItemCommand(Index index, double percent) {
        requireNonNull(index);
        requireNonNull(percent);

        this.index = index;
        this.percent = percent;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Item> lastShownList = model.getFilteredItemList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item itemToDiscount = lastShownList.get(index.getZeroBased());
        Item discountedItem = createDiscountedItem(itemToDiscount, percent);

        model.updateItem(itemToDiscount, discountedItem);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitAddressBook();
        EventsCenter.getInstance().post(new DisplayItemListRequestEvent());
        return new CommandResult(String.format(MESSAGE_DISCOUNT_ITEM_SUCCESS, discountedItem));
    }

    /**
     * Creates and returns a {@code Item} with the details of {@code itemToEdit}
     * edited with {@code editItemDescriptor}.
     */
    public static Item createDiscountedItem(Item itemToEdit, double percent) {
        assert itemToEdit != null;
        double originalValue = itemToEdit.getPrice().getOriginalValue();
        Price updatedPrice = new Price(String.format("%.2f", originalValue));
        updatedPrice.setValue(percent);

        return new Item(itemToEdit.getName(), updatedPrice, itemToEdit.getRemark(), itemToEdit.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DiscountItemCommand // instanceof handles nulls
                    && index.equals(((DiscountItemCommand) other).index) // state check
                    && percent == ((DiscountItemCommand) other).percent); // state check
    }
}
