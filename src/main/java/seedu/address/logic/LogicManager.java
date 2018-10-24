package seedu.address.logic;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.session.UserSession;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.accounts.LoginCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.accounts.Account;
import seedu.address.model.accounts.Password;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.menu.Item;
import seedu.address.model.person.Person;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.salesrecord.SalesRecord;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
    }

    /*
     * We define public commands to be those that can be executed without being logged in.
     */
    private boolean isPublicCommand(Command command) {
        return command instanceof LoginCommand || command instanceof HelpCommand || command instanceof ExitCommand;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        String commandTextToLog = commandText;
        if (commandText.contains(PREFIX_PASSWORD.getPrefix())) {
            commandTextToLog = Password.maskPassword(commandText);
        }
        logger.info("----------------[USER COMMAND][" + commandTextToLog + "]");

        try {
            Command command = addressBookParser.parseCommand(commandText);

            if (!isPublicCommand(command) && !UserSession.isAuthenticated()) {
                throw new CommandException(Messages.MESSAGE_COMMAND_FORBIDDEN);
            }
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Account> getFilteredAccountList() {
        return model.getFilteredAccountList();
    }

    @Override
    public ObservableList<Ingredient> getFilteredIngredientList() {
        return model.getFilteredIngredientList();
    }

    @Override
    public ObservableList<Item> getFilteredItemList() {
        return model.getFilteredItemList();
    }

    @Override
    public ObservableList<Reservation> getFilteredReservationList() {
        return model.getFilteredReservationList();
    }

    @Override
    public ObservableList<SalesRecord> getFilteredRecordList() {
        return model.getFilteredRecordList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
