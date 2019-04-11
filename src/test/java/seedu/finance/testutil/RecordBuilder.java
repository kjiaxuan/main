package seedu.finance.testutil;

import seedu.finance.model.category.Category;
import seedu.finance.model.record.Amount;
import seedu.finance.model.record.Date;
import seedu.finance.model.record.Description;
import seedu.finance.model.record.Name;
import seedu.finance.model.record.Record;
import seedu.finance.model.util.SampleDataUtil;

/**
 * A utility class to help with building Record objects.
 */
public class RecordBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_AMOUNT = "12";
    public static final String DEFAULT_DATE = "3/2/2019";
    public static final String DEFAULT_DESCRIPTION = "";
    public static final String DEFAULT_CATEGORY = "friend";

    private Name name;
    private Amount amount;
    private Date date;
    private Description description;
    private Category category;

    public RecordBuilder() {
        name = new Name(DEFAULT_NAME);
        amount = new Amount(DEFAULT_AMOUNT);
        date = new Date(DEFAULT_DATE);
        description = new Description(DEFAULT_DESCRIPTION);
        category = new Category(DEFAULT_CATEGORY);
    }

    /**
     * Initializes the RecordBuilder with the data of {@code recordToCopy}.
     * @param recordToCopy
     */
    public RecordBuilder(Record recordToCopy) {
        name = recordToCopy.getName();
        amount = recordToCopy.getAmount();
        date = recordToCopy.getDate();
        description = recordToCopy.getDescription();
        category = recordToCopy.getCategory();
    }

    /**
     * Sets the {@code Name} of the {@code Record} that we are building.
     */
    public RecordBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code category} into a {@code Category} and set it to the {@code Record} that we are building.
     */
    public RecordBuilder withCategory(String category) {
        this.category = SampleDataUtil.getCategory(category);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Record} that we are building.
     */
    public RecordBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Record} that we are building.
     */
    public RecordBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Record} that we are building.
     */
    public RecordBuilder withDescription(Description description) {
        this.description = new Description(description.value);
        return this;
    }

    /**
     * Creates a {@code Record} based on the variables specified.
     * @return Record with fields specified by Class
     */
    public Record build() {
        return new Record(name, amount, date, description, category);
    }

}
