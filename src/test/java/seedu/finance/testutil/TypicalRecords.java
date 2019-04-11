package seedu.finance.testutil;

import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_CATEGORY_FRIEND;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_CATEGORY_HUSBAND;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.finance.model.FinanceTracker;
import seedu.finance.model.budget.TotalBudget;
import seedu.finance.model.record.Description;
import seedu.finance.model.record.Record;

/**
 * A utility class containing a list of {@code Record} objects to be used in tests.
 */
public class TypicalRecords {

    public static final Record APPLE = new RecordBuilder().withName("Apple")
            .withAmount("1.00").withDate("12/02/2017").withDescription(new Description(""))
            .withCategory("groceries").build();
    public static final Record BANANA = new RecordBuilder().withName("Banana Donut")
            .withAmount("2.50").withDate("12/02/2015").withDescription(new Description(""))
            .withCategory("food").build();
    public static final Record CAP = new RecordBuilder().withName("Cap")
            .withAmount("15.00").withDate("12/05/2017").withDescription(new Description(""))
            .withCategory("clothes").build();
    public static final Record DONUT = new RecordBuilder().withName("Chocolate Donut")
            .withAmount("0.90").withDate("12/02/2007").withDescription(new Description(""))
            .withCategory("food").build();
    public static final Record EARRINGS = new RecordBuilder().withName("Earrings")
            .withAmount("12.99").withDate("12/12/2017").withDescription(new Description(""))
            .withCategory("accessories").build();
    public static final Record FRUITS = new RecordBuilder().withName("Fruits")
            .withAmount("20.00").withDate("02/02/2017").withDescription(new Description(""))
            .withCategory("food").build();
    public static final Record GIFT = new RecordBuilder().withName("Gift")
            .withAmount("24.90").withDate("12/02/2019").withDescription(new Description(""))
            .withCategory("gift").build();

    // Manually added
    public static final Record HAMBURGER = new RecordBuilder().withName("Hamburger")
            .withAmount("5.00").withDate("12/07/2017").build();
    public static final Record IPHONE = new RecordBuilder().withName("Iphone Charger")
            .withAmount("11.99").withDate("12/01/2017").build();

    // Manually added - Record's details found in {@code CommandTestUtil}
    public static final Record AMY = new RecordBuilder().withName(VALID_NAME_AMY).withAmount(VALID_AMOUNT_AMY)
            .withDate(VALID_DATE_AMY).withCategory(VALID_CATEGORY_FRIEND).build();
    public static final Record BOB = new RecordBuilder().withName(VALID_NAME_BOB).withAmount(VALID_AMOUNT_BOB)
            .withDate(VALID_DATE_BOB).withCategory(VALID_CATEGORY_HUSBAND)
            .build();

    public static final String KEYWORD_MATCHING_DONUT = "Donut"; // A keyword that matches DONUT

    private TypicalRecords() {} // prevents instantiation

    /**
     * Returns an {@code FinanceTracker} with all the typical records.
     */
    public static FinanceTracker getTypicalFinanceTracker() {
        FinanceTracker ft = new FinanceTracker();
        ft.addBudget(new TotalBudget(500.00));
        for (Record record : getTypicalRecords()) {
            ft.addRecord(record);
        }
        return ft;
    }


    /**
     * Returns an {@code FinanceTracker} with all the typical records.
     */
    /*
    public static FinanceTracker getTypicalFinanceTrackerWithBudget() {
        FinanceTracker ft = new FinanceTracker();
        ft.addBudget(new TotalBudget(500.00));
        for (Record record : getTypicalRecords()) {
            ft.addRecord(record);
        }
        return ft;
    }*/

    public static List<Record> getTypicalRecords() {
        return new ArrayList<>(Arrays.asList(APPLE, BANANA, CAP, DONUT, EARRINGS, FRUITS, GIFT));
    }
}
