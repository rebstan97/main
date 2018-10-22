package seedu.address.logic.commands.reservation;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.reservation.DisplayReservationListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reservation.Reservation;

/**
 * Selects a reservation identified using it's displayed index from the address book.
 */
public class SelectReservationCommand extends Command {

    public static final String COMMAND_WORD = "select-reservation";

    public static final String COMMAND_ALIAS = "sr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the reservation identified by the index number used in the displayed reservation list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_RESERVATION_SUCCESS = "Selected Reservation: %1$s";

    private final Index targetIndex;

    public SelectReservationCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Reservation> filteredReservationList = model.getFilteredReservationList();

        if (targetIndex.getZeroBased() >= filteredReservationList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
        }
        EventsCenter.getInstance().post(new DisplayReservationListRequestEvent());
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_RESERVATION_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectReservationCommand // instanceof handles nulls
                    && targetIndex.equals(((SelectReservationCommand) other).targetIndex)); // state check
    }
}
