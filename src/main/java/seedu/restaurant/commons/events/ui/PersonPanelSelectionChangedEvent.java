package seedu.restaurant.commons.events.ui;

import seedu.restaurant.commons.events.BaseEvent;
import seedu.restaurant.model.person.Person;

/**
 * Represents a selection change in the Person List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final Person newSelection;

    public PersonPanelSelectionChangedEvent(Person newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Person getNewSelection() {
        return newSelection;
    }
}
