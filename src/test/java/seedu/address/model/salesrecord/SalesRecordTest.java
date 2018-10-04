package seedu.address.model.salesrecord;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_DEMO_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_NAME_DEMO_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_DEMO_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_SOLD_DEMO_TWO;
import static seedu.address.testutil.salesrecords.TypicalRecords.DEMO_ONE;
import static seedu.address.testutil.salesrecords.TypicalRecords.DEMO_THREE;
import static seedu.address.testutil.salesrecords.TypicalRecords.DEMO_TWO;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.salesrecords.RecordBuilder;

public class SalesRecordTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameRecord() {
        // same object -> returns true
        assertTrue(DEMO_THREE.isSameRecord(DEMO_THREE));

        // null -> returns false
        assertFalse(DEMO_THREE.isSameRecord(null));

        // different date and item name -> returns false
        SalesRecord editedDemoOne =
                new RecordBuilder(DEMO_ONE).withDate(VALID_DATE_DEMO_TWO).withName(VALID_ITEM_NAME_DEMO_TWO).build();
        assertFalse(DEMO_ONE.isSameRecord(editedDemoOne));

        // different date, same item name -> returns false
        editedDemoOne = new RecordBuilder(DEMO_ONE).withDate(VALID_DATE_DEMO_TWO).build();
        assertFalse(DEMO_ONE.isSameRecord(editedDemoOne));

        // different name, same date -> returns false
        editedDemoOne = new RecordBuilder(DEMO_ONE).withName(VALID_ITEM_NAME_DEMO_TWO).build();
        assertFalse(DEMO_ONE.isSameRecord(editedDemoOne));

        // same date, same name, different attributes -> returns true
        editedDemoOne =
                new RecordBuilder(DEMO_ONE)
                        .withQuantitySold(VALID_QUANTITY_SOLD_DEMO_TWO)
                        .withPrice(VALID_PRICE_DEMO_TWO)
                        .build();
        assertTrue(DEMO_ONE.isSameRecord(editedDemoOne));

        // same date, same name, same quantity sold, different price -> returns true
        editedDemoOne =
                new RecordBuilder(DEMO_ONE)
                        .withPrice(VALID_PRICE_DEMO_TWO)
                        .build();
        assertTrue(DEMO_ONE.isSameRecord(editedDemoOne));

        // same date, same name, same price, different quantity sold -> returns true
        editedDemoOne =
                new RecordBuilder(DEMO_ONE)
                        .withQuantitySold(VALID_QUANTITY_SOLD_DEMO_TWO)
                        .build();
        assertTrue(DEMO_ONE.isSameRecord(editedDemoOne));
    }

    @Test
    public void equals() {
        // same values -> returns true
        SalesRecord demoThreeCopy= new RecordBuilder(DEMO_THREE).build();
        assertTrue(DEMO_THREE.equals(demoThreeCopy));

        // same object -> returns true
        assertTrue(DEMO_THREE.equals(DEMO_THREE));

        // null -> returns false
        assertFalse(DEMO_THREE.equals(null));

        // different type -> returns false
        assertFalse(DEMO_THREE.equals(5));

        // different person -> returns false
        assertFalse(DEMO_THREE.equals(DEMO_TWO));

        // different date -> returns false
        SalesRecord editedDemoOne = new RecordBuilder(DEMO_ONE).withDate(VALID_DATE_DEMO_TWO).build();
        assertFalse(DEMO_ONE.equals(editedDemoOne));

        // different item name -> returns false
        editedDemoOne = new RecordBuilder(DEMO_ONE).withName(VALID_ITEM_NAME_DEMO_TWO).build();
        assertFalse(DEMO_ONE.equals(editedDemoOne));

        // different quantity sold -> returns false
        editedDemoOne = new RecordBuilder(DEMO_ONE).withQuantitySold(VALID_QUANTITY_SOLD_DEMO_TWO).build();
        assertFalse(DEMO_ONE.equals(editedDemoOne));

        // different price -> returns false
        editedDemoOne = new RecordBuilder(DEMO_ONE).withPrice(VALID_PRICE_DEMO_TWO).build();
        assertFalse(DEMO_ONE.equals(editedDemoOne));
    }
}

