package seedu.address.testutil.salesrecords;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_DEMO_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_DEMO_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_DEMO_THREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_NAME_DEMO_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_NAME_DEMO_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_NAME_DEMO_THREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_SOLD_DEMO_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_SOLD_DEMO_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_SOLD_DEMO_THREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_DEMO_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_DEMO_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_DEMO_THREE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.salesrecord.SalesRecord;

/**
 * A utility class containing a list of {@code SalesRecord} objects to be used in tests.
 */
public class TypicalRecords {
    public static final SalesRecord DEMO_DEFAULT = new RecordBuilder().build();
    public static final SalesRecord DEMO_ONE = new RecordBuilder().withDate(VALID_DATE_DEMO_ONE)
            .withName(VALID_ITEM_NAME_DEMO_ONE).withQuantitySold(VALID_QUANTITY_SOLD_DEMO_ONE).
                    withPrice(VALID_PRICE_DEMO_ONE).build();
    public static final SalesRecord DEMO_TWO = new RecordBuilder().withDate(VALID_DATE_DEMO_TWO)
            .withName(VALID_ITEM_NAME_DEMO_TWO).withQuantitySold(VALID_QUANTITY_SOLD_DEMO_TWO).
                    withPrice(VALID_PRICE_DEMO_TWO).build();
    public static final SalesRecord DEMO_THREE = new RecordBuilder().withDate(VALID_DATE_DEMO_THREE)
            .withName(VALID_ITEM_NAME_DEMO_THREE).withQuantitySold(VALID_QUANTITY_SOLD_DEMO_THREE).
                    withPrice(VALID_PRICE_DEMO_THREE).build();

    private TypicalRecords() {} // prevents instantiation
    public static List<SalesRecord> getTypicalRecords() {
        return new ArrayList<>(Arrays.asList(DEMO_DEFAULT, DEMO_ONE, DEMO_TWO));
    }
}