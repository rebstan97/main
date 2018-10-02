package seedu.address.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private Path addressBookFilePath = Paths.get("data" , "addressbook.xml");
    private Path salesRecordFilePath = Paths.get("data", "salesrecord.xml");

    public UserPrefs() {
        setGuiSettings(500, 500, 0, 0);
    }

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public Path getSalesRecordFilePath() {
        return salesRecordFilePath;
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        this.addressBookFilePath = addressBookFilePath;
    }

    public void setSalesRecordFilePath(Path salesRecordFilePath) {
        this.salesRecordFilePath = salesRecordFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return Objects.equals(guiSettings, o.guiSettings)
                && Objects.equals(addressBookFilePath, o.addressBookFilePath)
                && Objects.equals(salesRecordFilePath, o.salesRecordFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath, salesRecordFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + addressBookFilePath);
        sb.append("\nLocal sales records file location : " + salesRecordFilePath);
        return sb.toString();
    }
}
