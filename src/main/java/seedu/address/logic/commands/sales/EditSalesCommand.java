package seedu.address.logic.commands.sales;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY_SOLD;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECORDS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.DisplayRecordListRequestEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.salesrecord.Date;
import seedu.address.model.salesrecord.ItemName;
import seedu.address.model.salesrecord.Price;
import seedu.address.model.salesrecord.QuantitySold;
import seedu.address.model.salesrecord.SalesRecord;

/**
 * Edits the details of an existing sales record in the address book.
 */
public class EditSalesCommand extends Command {

    public static final String COMMAND_WORD = "edit-sales";

    public static final String COMMAND_ALIAS = "es";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the sales record identified "
            + "by the index number used in the displayed sales list.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_ITEM_NAME + "NAME] "
            + "[" + PREFIX_QUANTITY_SOLD + "QUANTITY_SOLD] "
            + "[" + PREFIX_ITEM_PRICE + "PRICE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "25-12-2018 "
            + PREFIX_QUANTITY_SOLD + "80";

    public static final String MESSAGE_EDIT_RECORD_SUCCESS = "Edited record: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RECORD = "This record already exists in the address book.";

    private final Index index;
    private final EditRecordDescriptor editRecordDescriptor;

    /**
     * @param index of the record in the filtered sales record list to edit
     * @param editRecordDescriptor details to edit the record with
     */
    public EditSalesCommand(Index index, EditRecordDescriptor editRecordDescriptor) {
        requireNonNull(index);
        requireNonNull(editRecordDescriptor);

        this.index = index;
        this.editRecordDescriptor = new EditRecordDescriptor(editRecordDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<SalesRecord> lastShownList = model.getFilteredRecordList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
        }

        SalesRecord recordToEdit = lastShownList.get(index.getZeroBased());
        SalesRecord editedRecord = createEditedRecord(recordToEdit, editRecordDescriptor);

        if (!recordToEdit.isSameRecord(editedRecord) && model.hasRecord(editedRecord)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECORD);
        }

        model.updateRecord(recordToEdit, editedRecord);
        model.updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORDS);
        model.commitAddressBook();
        EventsCenter.getInstance().post(new DisplayRecordListRequestEvent());
        return new CommandResult(String.format(MESSAGE_EDIT_RECORD_SUCCESS, editedRecord));
    }

    /**
     * Creates and returns a {@code SalesRecord} with the details of {@code recordToEdit}
     * edited with {@code editRecordDescriptor}.
     */
    private static SalesRecord createEditedRecord(SalesRecord recordToEdit, EditRecordDescriptor editRecordDescriptor) {
        assert recordToEdit != null;

        Date updatedDate = editRecordDescriptor.getDate().orElse(recordToEdit.getDate());
        ItemName updatedName = editRecordDescriptor.getName().orElse(recordToEdit.getName());
        Price updatedPrice = editRecordDescriptor.getPrice().orElse(recordToEdit.getPrice());
        QuantitySold updatedQuantitySold = editRecordDescriptor.getQuantitySold()
                .orElse(recordToEdit.getQuantitySold());

        return new SalesRecord(updatedDate, updatedName, updatedQuantitySold, updatedPrice);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditSalesCommand)) {
            return false;
        }

        // state check
        EditSalesCommand e = (EditSalesCommand) other;
        return index.equals(e.index)
                && editRecordDescriptor.equals(e.editRecordDescriptor);
    }

    /**
     * Stores the details to edit the sales record with. Each non-empty field value will replace the
     * corresponding field value of the sales record.
     */
    public static class EditRecordDescriptor {
        private Date date;
        private ItemName name;
        private QuantitySold quantitySold;
        private Price price;

        public EditRecordDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditRecordDescriptor(EditRecordDescriptor toCopy) {
            setDate(toCopy.date);
            setName(toCopy.name);
            setQuantitySold(toCopy.quantitySold);
            setPrice(toCopy.price);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(date, name, quantitySold, price);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }


        public void setName(ItemName name) {
            this.name = name;
        }

        public Optional<ItemName> getName() {
            return Optional.ofNullable(name);
        }

        public void setQuantitySold(QuantitySold quantitySold) {
            this.quantitySold = quantitySold;
        }

        public Optional<QuantitySold> getQuantitySold() {
            return Optional.ofNullable(quantitySold);
        }

        public void setPrice(Price price) {
            this.price = price;
        }

        public Optional<Price> getPrice() {
            return Optional.ofNullable(price);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditRecordDescriptor)) {
                return false;
            }

            // state check
            EditRecordDescriptor e = (EditRecordDescriptor) other;

            return getDate().equals(e.getDate())
                    && getName().equals(e.getName())
                    && getQuantitySold().equals(e.getQuantitySold())
                    && getPrice().equals(e.getPrice());
        }
    }
}
