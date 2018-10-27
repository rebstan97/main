package seedu.address.logic.commands.sales;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.salesrecord.SalesRecord;

/**
 * Deletes a sales record identified using its displayed index from the restaurant book.
 */
public class DeleteSalesCommand extends Command {

    public static final String COMMAND_WORD = "delete-sales";

    public static final String COMMAND_ALIAS = "des";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the sales record identified by the index number used in the displayed sales list.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_SALES_SUCCESS = "Deleted Sales record: %1$s";

    private final Index targetIndex;

    public DeleteSalesCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<SalesRecord> lastShownList = model.getFilteredRecordList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
        }

        SalesRecord recordToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteRecord(recordToDelete);
        model.commitRestaurantBook();
        return new CommandResult(String.format(MESSAGE_DELETE_SALES_SUCCESS, recordToDelete));
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteSalesCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteSalesCommand) other).targetIndex)); // state check
    }
}
