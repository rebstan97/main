package seedu.address.logic.commands.reservation;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reservation.Reservation;

/**
 * Deletes a reservation identified using it's displayed index from the restaurant book.
 */
public class DeleteReservationCommand extends Command {

    public static final String COMMAND_WORD = "delete-reservation";

    public static final String COMMAND_ALIAS = "dr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the reservation identified by the index number used in the displayed reservation list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_RESERVATION_SUCCESS = "Deleted Reservation: %1$s";

    private final Index targetIndex;

    public DeleteReservationCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Reservation> lastShownList = model.getFilteredReservationList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
        }

        Reservation reservationToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteReservation(reservationToDelete);
        model.commitRestaurantBook();
        return new CommandResult(String.format(MESSAGE_DELETE_RESERVATION_SUCCESS, reservationToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteReservationCommand // instanceof handles nulls
                    && targetIndex.equals(((DeleteReservationCommand) other).targetIndex)); // state check
    }
}
