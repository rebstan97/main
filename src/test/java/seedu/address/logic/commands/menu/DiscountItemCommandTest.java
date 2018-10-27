package seedu.address.logic.commands.menu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.menu.DiscountItemCommand.createDiscountedItem;
import static seedu.address.logic.commands.menu.MenuCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.menu.MenuCommandTestUtil.showItemAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.address.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.menu.Item;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for {@code
 * DiscountItemCommand}.
 */
public class DiscountItemCommandTest {

    private Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Item itemToDiscount = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        DiscountItemCommand discountItemCommand = new DiscountItemCommand(INDEX_FIRST, INDEX_FIRST, 20, false);

        ModelManager expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());

        Item discountedItem = createDiscountedItem(itemToDiscount, 20);

        String expectedMessage = String.format(DiscountItemCommand.MESSAGE_DISCOUNT_ITEM_SUCCESS, 1);

        expectedModel.updateItem(itemToDiscount, discountedItem);
        expectedModel.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(discountItemCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);
        DiscountItemCommand discountItemCommand = new DiscountItemCommand(outOfBoundIndex, outOfBoundIndex, 0, false);

        assertCommandFailure(discountItemCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexUnfilteredListMultiple_success() {
        DiscountItemCommand discountItemCommand = new DiscountItemCommand(INDEX_FIRST, INDEX_THIRD, 0, false);
        String expectedMessage = String.format(DiscountItemCommand.MESSAGE_REVERT_ITEM_SUCCESS, 3);

        ModelManager expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());

        for (int i = INDEX_FIRST.getZeroBased(); i < INDEX_THIRD.getOneBased(); i++) {
            Item itemToDiscount = model.getFilteredItemList().get(i);
            Item discountedItem = createDiscountedItem(itemToDiscount, 0);

            expectedModel.updateItem(itemToDiscount, discountedItem);
        }
        expectedModel.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(discountItemCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredListAll_success() {
        DiscountItemCommand discountItemCommand = new DiscountItemCommand(INDEX_FIRST, INDEX_FIRST, 20, true);
        String expectedMessage = String.format(DiscountItemCommand.MESSAGE_DISCOUNT_ITEM_SUCCESS, 7);

        ModelManager expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());

        for (int i = 0; i < 7; i++) {
            Item itemToDiscount = model.getFilteredItemList().get(i);
            Item discountedItem = createDiscountedItem(itemToDiscount, 20);

            expectedModel.updateItem(itemToDiscount, discountedItem);
        }
        expectedModel.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(discountItemCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showItemAtIndex(model, INDEX_FIRST);

        Item itemToDiscount = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        DiscountItemCommand discountItemCommand = new DiscountItemCommand(INDEX_FIRST, INDEX_FIRST, 75, false);

        Item discountedItem = createDiscountedItem(itemToDiscount, 75);

        String expectedMessage = String.format(DiscountItemCommand.MESSAGE_DISCOUNT_ITEM_SUCCESS, 1);

        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.updateItem(itemToDiscount, discountedItem);
        expectedModel.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(discountItemCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showItemAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of restaurant book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRestaurantBook().getItemList().size());

        DiscountItemCommand discountItemCommand = new DiscountItemCommand(outOfBoundIndex, outOfBoundIndex, 0, false);

        assertCommandFailure(discountItemCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Item itemToDiscount = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        DiscountItemCommand discountItemCommand = new DiscountItemCommand(INDEX_FIRST, INDEX_FIRST, 20, false);
        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());

        Item discountedItem = createDiscountedItem(itemToDiscount, 20);

        expectedModel.updateItem(itemToDiscount, discountedItem);
        expectedModel.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        expectedModel.commitRestaurantBook();

        // delete -> first item deleted
        discountItemCommand.execute(model, commandHistory);

        // undo -> reverts restaurant back to previous state and filtered item list to show all items
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first item deleted again
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList().size() + 1);
        DiscountItemCommand discountItemCommand = new DiscountItemCommand(outOfBoundIndex, outOfBoundIndex, 0, false);

        // execution failed -> restaurant book state not added into model
        assertCommandFailure(discountItemCommand, model, commandHistory, Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);

        // single restaurant book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Discounts a {@code Item} from a filtered list. 2. Undo the discount. 3. The unfiltered list should be shown
     * now. Verify that the index of the previously discounted item in the unfiltered list is different from the
     * index at the filtered list. 4. Redo the discount. This ensures {@code RedoCommand} discounts the item object
     * regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameItemDiscounted() throws Exception {
        DiscountItemCommand discountItemCommand = new DiscountItemCommand(INDEX_FIRST, INDEX_FIRST, 65, false);
        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());

        showItemAtIndex(model, INDEX_SECOND);
        Item itemToDiscount = model.getFilteredItemList().get(INDEX_FIRST.getZeroBased());
        Item discountedItem = createDiscountedItem(itemToDiscount, 65);

        expectedModel.updateItem(itemToDiscount, discountedItem);
        expectedModel.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        expectedModel.commitRestaurantBook();

        // delete -> deletes second item in unfiltered item list / first item in filtered item list
        discountItemCommand.execute(model, commandHistory);

        // undo -> reverts restaurant back to previous state and filtered item list to show all items
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(itemToDiscount, model.getFilteredItemList().get(INDEX_FIRST.getZeroBased()));
        // redo -> deletes same second item in unfiltered item list
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DiscountItemCommand discountFirstCommand = new DiscountItemCommand(INDEX_FIRST, INDEX_FIRST, 1, false);
        DiscountItemCommand discountSecondCommand = new DiscountItemCommand(INDEX_SECOND, INDEX_SECOND, 2, false);

        // same object -> returns true
        assertTrue(discountFirstCommand.equals(discountFirstCommand));

        // same values -> returns true
        DiscountItemCommand discountFirstCommandCopy = new DiscountItemCommand(INDEX_FIRST, INDEX_FIRST, 1, false);
        assertTrue(discountFirstCommand.equals(discountFirstCommandCopy));

        // different types -> returns false
        assertFalse(discountFirstCommand.equals(1));

        // null -> returns false
        assertFalse(discountFirstCommand.equals(null));

        // different item -> returns false
        assertFalse(discountFirstCommand.equals(discountSecondCommand));
    }
}
