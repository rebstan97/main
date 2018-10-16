package seedu.address.ui.accounts;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.LoginEvent;
import seedu.address.commons.events.ui.LogoutEvent;
import seedu.address.ui.UiPart;

/**
 * A ui for the Username label that is displayed at the header of the application.
 */
public class UsernameDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(UsernameDisplay.class);
    private static final String FXML = "UsernameDisplay.fxml";

    private static final String ACCOUNT_STATUS_GUEST = "Guest";
    private static final String ACCOUNT_STATUS = "Welcome, %s";

    private final StringProperty displayed = new SimpleStringProperty(ACCOUNT_STATUS_GUEST);

    @FXML
    private Label usernameDisplay;

    public UsernameDisplay() {
        super(FXML);
        usernameDisplay.setText(ACCOUNT_STATUS_GUEST);
        registerAsAnEventHandler(this); // To trigger @Subscribe
    }

    private void setUsername(String message) {
        usernameDisplay.setText(message);
    }

    @Subscribe
    private void handleLoginEvent(LoginEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setUsername(String.format(ACCOUNT_STATUS, event.username.toString().toUpperCase()));
    }

    @Subscribe
    private void handleLogoutEvent(LogoutEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setUsername(ACCOUNT_STATUS_GUEST);
    }

 /*   @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }*/

}
