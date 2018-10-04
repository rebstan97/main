package seedu.address.model.salesrecord;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.salesrecords.TypicalRecords.DEMO_DEFAULT;
import static seedu.address.testutil.salesrecords.TypicalRecords.DEMO_ONE;
import static seedu.address.testutil.salesrecords.TypicalRecords.DEMO_THREE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.salesrecord.exceptions.DuplicateRecordException;
import seedu.address.model.salesrecord.exceptions.RecordNotFoundException;

public class UniqueRecordListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final UniqueRecordList uniqueRecordList = new UniqueRecordList();

    @Test
    public void contains_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.contains(null);
    }
    @Test
    public void contains_Record_NotInList_returnsFalse() {
        assertFalse(uniqueRecordList.contains(DEMO_DEFAULT));
    }
    @Test
    public void contains_recordInList_returnsTrue() {
        uniqueRecordList.add(DEMO_ONE);
        assertTrue(uniqueRecordList.contains(DEMO_ONE));
    }
    @Test
    public void add_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.add(null);
    }
    @Test
    public void add_duplicateRecord_throwsDuplicateRecordException() {
        uniqueRecordList.add(DEMO_ONE);
        thrown.expect(DuplicateRecordException.class);
        uniqueRecordList.add(DEMO_ONE);
    }
    @Test
    public void setRecord_nullTargetAudience_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecord(null, DEMO_DEFAULT);
    }
    @Test
    public void setRecord_nullEditedRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecord(DEMO_ONE, null);
    }
    @Test
    public void setRecord_targetRecordNotInList_throwsRecordNotFoundException() {
        thrown.expect(RecordNotFoundException.class);
        uniqueRecordList.setRecord(DEMO_THREE, DEMO_DEFAULT);
    }
    @Test
    public void setRecord_editedSameRecord_success() {
        uniqueRecordList.add(DEMO_DEFAULT);
        uniqueRecordList.setRecord(DEMO_DEFAULT, DEMO_DEFAULT);
        UniqueRecordList expectedRecordList = new UniqueRecordList();
        expectedRecordList.add(DEMO_DEFAULT);
        assertEquals(expectedRecordList, uniqueRecordList);
    }
    @Test
    public void setRecord_editedRecordAlreadyExists_throwsDuplicateRecordException() {
        uniqueRecordList.add(DEMO_DEFAULT);
        uniqueRecordList.add(DEMO_ONE);
        thrown.expect(DuplicateRecordException.class);
        uniqueRecordList.setRecord(DEMO_DEFAULT, DEMO_ONE);
    }
    @Test
    public void remove_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.remove(null);
    }
    @Test
    public void remove_recordDoesNotExist_throwsRecordNotFoundException() {
        thrown.expect(RecordNotFoundException.class);
        uniqueRecordList.remove(DEMO_DEFAULT);
    }
    @Test
    public void remove_existingRecord_removesRecord() {
        uniqueRecordList.add(DEMO_DEFAULT);
        uniqueRecordList.remove(DEMO_DEFAULT);
        UniqueRecordList expectedRecordList = new UniqueRecordList();
        assertEquals(expectedRecordList, uniqueRecordList);
    }
    @Test
    public void setRecords_nullUniqueRecordList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecords((UniqueRecordList) null);
    }
    @Test
    public void setRecords_uniqueRecordList_replacesOwnListWithProvidedUniqueRecordList() {
        uniqueRecordList.add(DEMO_DEFAULT);
        UniqueRecordList expectedRecordList = new UniqueRecordList();
        expectedRecordList.add(DEMO_ONE);
        uniqueRecordList.setRecords(expectedRecordList);
        assertEquals(expectedRecordList, uniqueRecordList);
    }
    @Test
    public void setRecords_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecords((List<SalesRecord>) null);
    }
    @Test
    public void setRecords_list_replacesOwnListWithProvidedList() {
        uniqueRecordList.add(DEMO_DEFAULT);
        List<SalesRecord> records = Collections.singletonList(DEMO_ONE);
        uniqueRecordList.setRecords(records);
        UniqueRecordList expectedRecordList = new UniqueRecordList();
        expectedRecordList.add(DEMO_ONE);
        assertEquals(expectedRecordList, uniqueRecordList);
    }
    @Test
    public void setRecords_listWithDuplicateRecords_throwsDuplicateRecordException() {
        List<SalesRecord> listWithDuplicateRecords = Arrays.asList(DEMO_DEFAULT, DEMO_DEFAULT);
        thrown.expect(DuplicateRecordException.class);
        uniqueRecordList.setRecords(listWithDuplicateRecords);
    }
    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueRecordList.asUnmodifiableObservableList().remove(0);
    }
    @Test
    public void equals_sameObject_returnsTrue() {
        uniqueRecordList.add(DEMO_DEFAULT);
        assertTrue(uniqueRecordList.equals(uniqueRecordList));
    }
    @Test
    public void equals_sameList_returnsTrue() {
        uniqueRecordList.add(DEMO_DEFAULT);
        uniqueRecordList.add(DEMO_THREE);
        UniqueRecordList uniqueRecordListCopy = new UniqueRecordList();
        uniqueRecordListCopy.add(DEMO_DEFAULT);
        uniqueRecordListCopy.add(DEMO_THREE);
        assertTrue(uniqueRecordList.equals(uniqueRecordListCopy));
    }
    @Test
    public void equals_differentType_returnsFalse() {
        uniqueRecordList.add(DEMO_DEFAULT);
        assertFalse(uniqueRecordList.equals(2));
    }
    @Test
    public void equals_differentList_returnsFalse() {
        uniqueRecordList.add(DEMO_DEFAULT);
        UniqueRecordList uniqueRecordListCopy = new UniqueRecordList();
        uniqueRecordListCopy.add(DEMO_THREE);
        assertFalse(uniqueRecordList.equals(uniqueRecordListCopy));
    }
}
