package seedu.address.logic.commands.sales;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_RECORD_TWO;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.salesrecords.TypicalRecords.RECORD_ONE;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.salesrecord.SalesRecord;
import seedu.address.testutil.salesrecords.RecordBuilder;

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

        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.addRecord(validRecord);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(new RecordSalesCommand(validRecord), model, commandHistory,
                String.format(RecordSalesCommand.MESSAGE_RECORD_SALES_SUCCESS, validRecord), expectedModel);
    }

    @Test
    public void execute_duplicateRecord_throwsCommandException() {
        SalesRecord recordInList = model.getRestaurantBook().getRecordList().get(0);
        assertCommandFailure(new RecordSalesCommand(recordInList), model, commandHistory,
                String.format(RecordSalesCommand.MESSAGE_DUPLICATE_SALES_RECORD, recordInList.getName()));
    }
}
