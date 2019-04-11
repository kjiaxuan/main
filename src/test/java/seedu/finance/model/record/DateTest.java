package seedu.finance.model.record;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import seedu.finance.testutil.Assert;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        String test = null;
        Assert.assertThrows(NullPointerException.class, () -> new Date(test));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "1/22/3";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // blank date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only

        // missing parts
        assertFalse(Date.isValidDate("12/20/")); // missing year
        assertFalse(Date.isValidDate("30/2009")); // missing day
        assertFalse(Date.isValidDate("30/2008")); // missing month

        // invalid parts
        assertFalse(Date.isValidDate("99/01/2008")); // invalid date
        assertFalse(Date.isValidDate("03/30/2008")); // invalid month
        assertFalse(Date.isValidDate("30-03-2009")); // dashes instead of backslash

        // Future dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate futureDate = LocalDate.now().plusDays(1);
        assertFalse(Date.isValidDate(futureDate.format(formatter)));


        // valid date
        assertTrue(Date.isValidDate("30/03/2009"));
    }
}
