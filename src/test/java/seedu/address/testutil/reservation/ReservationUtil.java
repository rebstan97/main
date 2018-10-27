package seedu.address.testutil.reservation;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.reservation.AddReservationCommand;
import seedu.address.logic.commands.reservation.EditReservationCommand.EditReservationDescriptor;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Reservation.
 */
public class ReservationUtil {

    /**
     * Returns an add command string for adding the {@code reservation}.
     */
    public static String getAddReservationCommand(Reservation reservation) {
        return AddReservationCommand.COMMAND_WORD + " " + getReservationDetails(reservation);
    }

    /**
     * Returns the part of command string for the given {@code reservation}'s details.
     */
    public static String getReservationDetails(Reservation reservation) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + reservation.getName().toString() + " ");
        sb.append(PREFIX_PAX + reservation.getPax().toString() + " ");
        sb.append(PREFIX_DATETIME + reservation.getDateTime().toString() + " ");
        reservation.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditReservationDescriptor}'s details.
     */
    public static String getEditReservationDescriptorDetails(EditReservationDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.toString()).append(" "));
        descriptor.getPax().ifPresent(pax -> sb.append(PREFIX_PAX).append(pax.toString()).append(" "));
        descriptor.getDateTime().ifPresent(dateTime -> sb.append(PREFIX_DATETIME).append(dateTime.toString())
                .append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
