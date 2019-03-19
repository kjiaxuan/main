package seedu.finance.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.finance.model.record.Record;

/**
 * An UI component that displays information of a {@code Record}.
 */
public class RecordCard extends UiPart<Region> {

    private static final String FXML = "RecordListCard.fxml";
    private static final String[] CATEGORY_COLOR_STYLES =
        { "teal", "red", "yellow", "blue", "orange", "brown", "green", "pink", "black", "grey", "purple",
            "coral", "tan", "turquoise"};

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Record record;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label amount;
    @FXML
    private Label date;
    @FXML
    private Label description;
    @FXML
    private FlowPane categories;

    public RecordCard(Record record, int displayedIndex) {
        super(FXML);
        this.record = record;
        id.setText(displayedIndex + ". ");
        name.setText(record.getName().fullName);
        amount.setText(record.getAmount().value);
        date.setText(record.getDate().value);
        description.setText(record.getDescription().value);
        initCategories(record);
    }

    //@@author geezlouisee-reused
    //Reused from https://github.com/se-edu/addressbook-level4/pull/798/commits/1ac2e7c5597cf328cc9c28d5d8e18db8dc1fc5a0
    // with minor modifications
    /**
     * Returns the color style for {@code categoryName}'s label.
     */
    private String getCategoryColorStyleFor(String categoryName) {
        //Using the hash code of the category name to generate a random color, so color remains consistent
        //between different runs of the program while still making it random enough between Categories
        return CATEGORY_COLOR_STYLES[Math.abs(categoryName.hashCode()) % CATEGORY_COLOR_STYLES.length];
    }

    /**
     * Creates the category labels for {@code record}.
     * @param record
     */
    private void initCategories(Record record) {
        record.getCategories().forEach(tag -> {
            Label tagLabel = new Label(tag.categoryName);

            tagLabel.getStyleClass().add(getCategoryColorStyleFor(tag.categoryName));

            categories.getChildren().add(tagLabel);
        });
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecordCard)) {
            return false;
        }

        // state check
        RecordCard card = (RecordCard) other;
        return id.getText().equals(card.id.getText())
                && record.equals(card.record);
    }
}
