package seedu.finance.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.finance.commons.core.Messages;
import seedu.finance.logic.CommandHistory;
import seedu.finance.model.Model;
import seedu.finance.model.record.CategoryContainsKeywordsPredicate;
import seedu.finance.model.record.NameContainsKeywordsPredicate;
import seedu.finance.model.record.Record;

/**
 * Finds and lists all records in finance tracker whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";
    public static final String COMMAND_ALIAS = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all records which contain any of "
            + "the specified keywords (case-insensitive) based on the selected flag"
            + "and displays them as a list with index numbers.\n"
            + "Parameters: FLAG KEYWORD [MORE_KEYWORDS]...\n"
            + "Flags: -name search for names, -cat search based on categories\n"
            + "Example: " + COMMAND_WORD + " -name" + " fries chicken bus fare";

    public static final String INVALID_FLAG = "Flag not recognised. Valid flags:\n"
            + "-name: Find all records that contains specified keywords in name\n"
            + "-cat: Find all records that has specified keywords in category.\n"
            + "Example: " + COMMAND_WORD + " -name" + " fries chicken bus fare";

    private final Predicate<Record> predicate;

    public SearchCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    public SearchCommand(CategoryContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredRecordList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_RECORDS_LISTED_OVERVIEW, model.getFilteredRecordList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCommand // instanceof handles nulls
                && predicate.equals(((SearchCommand) other).predicate)); // state check
    }
}
