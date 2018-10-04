package seedu.address.storage.salesrecords;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.storage.XmlAdaptedRecord.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.salesrecords.TypicalRecords.DEMO_DEFAULT;
import static seedu.address.testutil.salesrecords.TypicalRecords.DEMO_ONE;
import static seedu.address.testutil.salesrecords.TypicalRecords.DEMO_TWO;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.salesrecord.Date;
import seedu.address.model.salesrecord.ItemName;
import seedu.address.model.salesrecord.Price;
import seedu.address.model.salesrecord.QuantitySold;
import seedu.address.storage.XmlAdaptedRecord;
import seedu.address.testutil.Assert;
import seedu.address.testutil.salesrecords.RecordBuilder;

public class XmlAdaptedRecordTest {
    private static final String INVALID_DATE = "32-12-2017";
    private static final String INVALID_ITEM_NAME = "Apple Juice :D";
    private static final String INVALID_QUANTITY_SOLD = "100.5";
    private static final String INVALID_PRICE = "$21";
    private static final String VALID_DATE = DEMO_DEFAULT.getDate().toString();
    private static final String VALID_ITEM_NAME = DEMO_DEFAULT.getName().toString();
    private static final String VALID_QUANTITY_SOLD = DEMO_DEFAULT.getQuantitySold().toString();
    private static final String VALID_PRICE = DEMO_DEFAULT.getPrice().toString();

    private XmlAdaptedRecord record = null;
    @Test
    public void toModelType_validRecordDetails_returnsRecord() throws Exception {
        record = new XmlAdaptedRecord(DEMO_DEFAULT);
        assertEquals(DEMO_DEFAULT, record.toModelType());
    }
    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        record = new XmlAdaptedRecord(null, VALID_ITEM_NAME, VALID_QUANTITY_SOLD, VALID_PRICE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }
    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        record = new XmlAdaptedRecord(INVALID_DATE, VALID_ITEM_NAME, VALID_QUANTITY_SOLD, VALID_PRICE);
        String expectedMessage = Date.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }
    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        record = new XmlAdaptedRecord(VALID_DATE, null, VALID_QUANTITY_SOLD, VALID_PRICE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ItemName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }
    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        record = new XmlAdaptedRecord(VALID_DATE, INVALID_ITEM_NAME, VALID_QUANTITY_SOLD, VALID_PRICE);
        String expectedMessage = ItemName.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }
    @Test
    public void toModelType_nullQuantitySold_throwsIllegalValueException() {
        record = new XmlAdaptedRecord(VALID_DATE, VALID_ITEM_NAME, null, VALID_PRICE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, QuantitySold.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }
    @Test
    public void toModelType_invalidQuantitySold_throwsIllegalValueException() {
        record = new XmlAdaptedRecord(VALID_DATE, VALID_ITEM_NAME, INVALID_QUANTITY_SOLD, VALID_PRICE);
        String expectedMessage = QuantitySold.MESSAGE_QUANTITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }
    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        record = new XmlAdaptedRecord(VALID_DATE, VALID_ITEM_NAME, VALID_QUANTITY_SOLD, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }
    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        record = new XmlAdaptedRecord(VALID_DATE, VALID_ITEM_NAME, VALID_QUANTITY_SOLD, INVALID_PRICE);
        String expectedMessage = Price.MESSAGE_PRICE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void equals() {
        XmlAdaptedRecord recordDemoOne = new XmlAdaptedRecord(new RecordBuilder(DEMO_ONE).build());
        // same object
        assertTrue(recordDemoOne.equals(recordDemoOne));
        XmlAdaptedRecord recordDemoOneDuplicate = new XmlAdaptedRecord(new RecordBuilder(DEMO_ONE).build());
        // different object, same identity and attributes
        assertTrue(recordDemoOne.equals(recordDemoOneDuplicate));
        XmlAdaptedRecord recordDemoTwo = new XmlAdaptedRecord(new RecordBuilder(DEMO_TWO).build());
        assertFalse(recordDemoOne.equals(recordDemoTwo));
        assertFalse(recordDemoOne.equals(null));
        // not same instance
        assertFalse(recordDemoOne.equals(1));
    }
}
