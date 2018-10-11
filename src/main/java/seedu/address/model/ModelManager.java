package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.accounts.Account;
import seedu.address.model.person.Person;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.salesrecord.SalesRecord;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Reservation> filteredReservations;
    private final FilteredList<SalesRecord> filteredRecords;
    private final FilteredList<Account> filteredAccounts;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredReservations = new FilteredList<>(versionedAddressBook.getReservationList());
        filteredRecords = new FilteredList<>(versionedAddressBook.getRecordList());
        filteredAccounts = new FilteredList<>(versionedAddressBook.getAccountList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    @Override
    public void removeTag(Tag tag) {
        versionedAddressBook.removeTag(tag);
    }

    //=========== Filtered Person List Accessors =============================================================

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Sales =================================================================================

    @Override
    public boolean hasRecord(SalesRecord record) {
        requireNonNull(record);
        return versionedAddressBook.hasRecord(record);
    }

    @Override
    public void deleteRecord(SalesRecord target) {
        versionedAddressBook.removeRecord(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addRecord(SalesRecord record) {
        versionedAddressBook.addRecord(record);
        updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORDS);
        indicateAddressBookChanged();
    }

    @Override
    public void updateRecord(SalesRecord target, SalesRecord editedRecord) {
        requireAllNonNull(target, editedRecord);
        versionedAddressBook.updateRecord(target, editedRecord);
        indicateAddressBookChanged();
    }

    //=========== Filtered Sales Record List Accessors =============================================================


    /**
     * Returns an unmodifiable view of the list of {@code SalesRecord} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<SalesRecord> getFilteredRecordList() {
        return FXCollections.unmodifiableObservableList(filteredRecords);
    }

    @Override
    public void updateFilteredRecordList(Predicate<SalesRecord> predicate) {
        requireNonNull(predicate);
        filteredRecords.setPredicate(predicate);
    }

    //=========== Accounts =================================================================================

    @Override
    public void addAccount(Account account) {
        versionedAddressBook.addAccount(account);
        indicateAddressBookChanged();
    }

    @Override
    public boolean hasAccount(Account account) {
        return versionedAddressBook.hasAccount(account);
    }

    @Override
    public void removeAccount(Account account) {
        versionedAddressBook.removeAccount(account);
        indicateAddressBookChanged();
    }

    @Override
    public void updateAccount(Account target, Account editedAccount) {
        versionedAddressBook.updateAccount(target, editedAccount);
        indicateAddressBookChanged();
    }

    //=========== Filtered Account List Accessors =============================================================

    @Override
    public ObservableList<Account> getFilteredAccountList() {
        return FXCollections.unmodifiableObservableList(filteredAccounts);
    }

    @Override
    public void updateFilteredAccountList(Predicate<Account> predicate) {
        requireNonNull(predicate);
        filteredAccounts.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredPersons.equals(other.filteredPersons)
                && filteredAccounts.equals(other.filteredAccounts)
                && filteredReservations.equals(other.filteredReservations)
                && filteredRecords.equals(other.filteredRecords);
    }

    //=========== Reservations =====================================================================================

    @Override
    public boolean hasReservation(Reservation reservation) {
        requireNonNull(reservation);
        return versionedAddressBook.hasReservation(reservation);
    }

    @Override
    public void deleteReservation(Reservation target) {
        versionedAddressBook.removeReservation(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addReservation(Reservation reservation) {
        versionedAddressBook.addReservation(reservation);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_RESERVATIONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updateReservation(Reservation target, Reservation editedReservation) {
        requireAllNonNull(target, editedReservation);

        versionedAddressBook.updateReservation(target, editedReservation);
        indicateAddressBookChanged();
    }

    @Override
    public void removeTagForReservation(Tag tag) {
        versionedAddressBook.removeTag(tag);
    }

    //=========== Filtered Reservation List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Reservation} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Reservation> getFilteredReservationList() {
        return FXCollections.unmodifiableObservableList(filteredReservations);
    }

    @Override
    public void updateFilteredReservationList(Predicate<Reservation> predicate) {
        requireNonNull(predicate);
        filteredReservations.setPredicate(predicate);
    }

}
