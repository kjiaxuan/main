package seedu.finance.model.record;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_CATEGORY_HUSBAND;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.finance.testutil.TypicalRecords.APPLE;
import static seedu.finance.testutil.TypicalRecords.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.finance.testutil.RecordBuilder;

public class RecordTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Record record = new RecordBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        record.getCategories().remove(0);
    }

    @Test
    public void isSameRecord() {
        // same object -> returns true
        assertTrue(APPLE.isSameRecord(APPLE));

        // null -> returns false
        assertFalse(APPLE.isSameRecord(null));

        // different amount and date -> returns false
        Record editedAlice = new RecordBuilder(APPLE).withAmount(VALID_AMOUNT_BOB).withDate(VALID_DATE_BOB).build();
        assertFalse(APPLE.isSameRecord(editedAlice));

        // different name -> returns false
        editedAlice = new RecordBuilder(APPLE).withName(VALID_NAME_BOB).build();
        assertFalse(APPLE.isSameRecord(editedAlice));

        // same name, same amount, different attributes -> returns true
        editedAlice = new RecordBuilder(APPLE).withDate(VALID_DATE_BOB).withCategories(VALID_CATEGORY_HUSBAND).build();
        assertTrue(APPLE.isSameRecord(editedAlice));

        // same name, same date, different attributes -> returns true
        editedAlice = new RecordBuilder(APPLE).withAmount(VALID_AMOUNT_BOB).withCategories(VALID_CATEGORY_HUSBAND)
                .build();
        assertTrue(APPLE.isSameRecord(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Record aliceCopy = new RecordBuilder(APPLE).build();
        assertTrue(APPLE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(APPLE.equals(APPLE));

        // null -> returns false
        assertFalse(APPLE.equals(null));

        // different type -> returns false
        assertFalse(APPLE.equals(5));

        // different record -> returns false
        assertFalse(APPLE.equals(BOB));

        // different name -> returns false
        Record editedAlice = new RecordBuilder(APPLE).withName(VALID_NAME_BOB).build();
        assertFalse(APPLE.equals(editedAlice));

        // different amount -> returns false
        editedAlice = new RecordBuilder(APPLE).withAmount(VALID_AMOUNT_BOB).build();
        assertFalse(APPLE.equals(editedAlice));

        // different date -> returns false
        editedAlice = new RecordBuilder(APPLE).withDate(VALID_DATE_BOB).build();
        assertFalse(APPLE.equals(editedAlice));

        // different categories -> returns false
        editedAlice = new RecordBuilder(APPLE).withCategories(VALID_CATEGORY_HUSBAND).build();
        assertFalse(APPLE.equals(editedAlice));
    }
}
