package seedu.address.logic.commands.sales;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_RECORD_TWO;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.sales.RecordSalesCommand.MESSAGE_INGREDIENT_NOT_ENOUGH;
import static seedu.address.logic.commands.sales.RecordSalesCommand.MESSAGE_INGREDIENT_NOT_FOUND;
import static seedu.address.logic.commands.sales.RecordSalesCommand.MESSAGE_INGREDIENT_UPDATE_SUCCESS;
import static seedu.address.logic.commands.sales.RecordSalesCommand.MESSAGE_ITEM_NOT_FOUND;
import static seedu.address.logic.commands.sales.RecordSalesCommand.MESSAGE_REQUIRED_INGREDIENTS_NOT_FOUND;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.salesrecords.TypicalRecords.RECORD_ONE;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.sales.RecordSalesCommand.RequiredIngredientsNotFoundException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.menu.Item;
import seedu.address.model.menu.Name;
import seedu.address.model.salesrecord.SalesRecord;
import seedu.address.testutil.salesrecords.RecordBuilder;
import seedu.address.testutil.salesrecords.RecordSalesCommandUtil;

/**
 * Contains integration tests (interaction with the Model) for {@code RecordSalesCommand}.
 */
public class RecordSalesCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newRecord_success() {
        SalesRecord validRecord = new RecordBuilder(RECORD_ONE).withDate(VALID_DATE_RECORD_TWO).build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addRecord(validRecord);
        expectedModel.commitAddressBook();

        String ingredientsUpdateStatus = RecordSalesCommandUtil.getIngredientUpdateStatus(model, validRecord);

        assertCommandSuccess(new RecordSalesCommand(validRecord), model, commandHistory,
                String.format(RecordSalesCommand.MESSAGE_RECORD_SALES_SUCCESS, validRecord) +
                        "\n" + ingredientsUpdateStatus, expectedModel);
    }

    @Test
    public void execute_duplicateRecord_throwsCommandException() {
        SalesRecord recordInList = model.getAddressBook().getRecordList().get(0);
        assertCommandFailure(new RecordSalesCommand(recordInList), model, commandHistory,
                String.format(RecordSalesCommand.MESSAGE_DUPLICATE_SALES_RECORD, recordInList.getName()));
    }
}
