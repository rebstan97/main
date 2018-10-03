package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.reservation.UniqueReservationList;
import seedu.address.model.tag.Tag;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueReservationList reservations;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        reservations = new UniqueReservationList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}. {@code persons} must not contain duplicate
     * persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setReservations(newData.getReservationList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book. The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}. {@code target} must exist in the
     * address book. The person identity of {@code editedPerson} must not be the same as another existing person in the
     * address book.
     */
    public void updatePerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}. {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Removes {@code tag} from {@code person} in this {@code AddressBook}.
     *
     * @param person whose tag is being removed.
     * @param tag to be removed.
     */
    private void removeTagForPerson(Person person, Tag tag) {
        Set<Tag> tags = new HashSet<>(person.getTags());

        if (!tags.remove(tag)) {
            return;
        }

        Person newPerson = new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                person.getRemark(), tags);
        updatePerson(person, newPerson);
    }

    /**
     * Removes {@code tag} from all {@code person} in this {@code AddressBook}.
     *
     * @param tag to be removed.
     */
    public void removeTag(Tag tag) {
        persons.forEach(person -> removeTagForPerson(person, tag));
    }

    // Reservation Management

    /**
     * Replaces the contents of the reservation list with {@code reservations}. {@code reservations} must not contain
     * duplicate reservations.
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations.setReservations(reservations);
    }

    /**
     * Returns true if a reservation with the same identity as {@code reservation} exists in the address book.
     */
    public boolean hasReservation(Reservation reservation) {
        requireNonNull(reservation);
        return reservations.contains(reservation);
    }

    /**
     * Adds a reservation to the address book. The reservation must not already exist in the address book.
     */
    public void addReservation(Reservation r) {
        reservations.add(r);
    }

    /**
     * Replaces the given reservation {@code target} in the list with {@code editedReservation}. {@code target} must
     * exist in the address book. The person identity of {@code editedReservation} must not be the same as
     * another existing person in the address book.
     */
    public void updateReservation(Reservation target, Reservation editedReservation) {
        requireNonNull(editedReservation);

        reservations.setReservation(target, editedReservation);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}. {@code key} must exist in the address book.
     */
    public void removeReservation(Reservation key) {
        reservations.remove(key);
    }

    /**
     * Removes {@code tag} from {@code reservation} in this {@code AddressBook}.
     *
     * @param reservation whose tag is being removed.
     * @param tag to be removed.
     */
    private void removeTagForReservation(Reservation reservation, Tag tag) {
        Set<Tag> tags = new HashSet<>(reservation.getTags());

        if (!tags.remove(tag)) {
            return;
        }

        Reservation newReservation = new Reservation(reservation.getName(), reservation.getPax(),
                reservation.getDateTime(), reservation.getRemark(), tags);
        updateReservation(reservation, newReservation);
    }

    @Override
    public ObservableList<Reservation> getReservationList() {
        return reservations.asUnmodifiableObservableList();
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
