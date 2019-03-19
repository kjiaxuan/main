package seedu.finance.storage;

import static org.junit.Assert.assertEquals;
import static seedu.finance.storage.JsonAdaptedRecord.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.finance.testutil.TypicalRecords.BANANA;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.finance.commons.exceptions.IllegalValueException;
import seedu.finance.model.record.Amount;
import seedu.finance.model.record.Date;
import seedu.finance.model.record.Name;
import seedu.finance.testutil.Assert;

public class JsonAdaptedRecordTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_AMOUNT = "1";
    private static final String INVALID_DATE = "59/59/2109";
    private static final String INVALID_CATEGORY = "#friend";

    private static final String VALID_NAME = BANANA.getName().toString();
    private static final String VALID_AMOUNT = BANANA.getAmount().toString();
    private static final String VALID_DATE = BANANA.getDate().toString();
    private static final String VALID_DESCRIPTION = BANANA.getDescription().toString();
    private static final List<JsonAdaptedCategory> VALID_CATEGORIES = BANANA.getCategories().stream()
            .map(JsonAdaptedCategory::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validRecordDetails_returnsRecord() throws Exception {
        JsonAdaptedRecord record = new JsonAdaptedRecord(BANANA);
        assertEquals(BANANA, record.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedRecord record =
                new JsonAdaptedRecord(INVALID_NAME, VALID_AMOUNT, VALID_DATE, VALID_DESCRIPTION, VALID_CATEGORIES);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedRecord record = new JsonAdaptedRecord(null, VALID_AMOUNT, VALID_DATE,
                VALID_DESCRIPTION, VALID_CATEGORIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedRecord record =
                new JsonAdaptedRecord(VALID_NAME, INVALID_AMOUNT, VALID_DATE, VALID_DESCRIPTION, VALID_CATEGORIES);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedRecord record = new JsonAdaptedRecord(VALID_NAME, null, VALID_DATE,
                VALID_DESCRIPTION, VALID_CATEGORIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedRecord record =
                new JsonAdaptedRecord(VALID_NAME, VALID_AMOUNT, INVALID_DATE, VALID_DESCRIPTION, VALID_CATEGORIES);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedRecord record = new JsonAdaptedRecord(VALID_NAME, VALID_AMOUNT, null,
                VALID_DESCRIPTION, VALID_CATEGORIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidCategories_throwsIllegalValueException() {
        List<JsonAdaptedCategory> invalidCategories = new ArrayList<>(VALID_CATEGORIES);
        invalidCategories.add(new JsonAdaptedCategory(INVALID_CATEGORY));
        JsonAdaptedRecord record =
                new JsonAdaptedRecord(VALID_NAME, VALID_AMOUNT, VALID_DATE, VALID_DESCRIPTION, invalidCategories);
        Assert.assertThrows(IllegalValueException.class, record::toModelType);
    }

}
