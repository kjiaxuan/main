package seedu.finance.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.finance.logic.CommandHistory;
import seedu.finance.logic.commands.exceptions.CommandException;
import seedu.finance.model.Model;
import seedu.finance.model.record.Record;

/**
 * Adds a record to the finance tracker.
 */
public class SpendCommand extends Command {

    public static final String COMMAND_WORD = "spend";
    public static final String COMMAND_ALIAS = "add";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a record to the finance tracker. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_DATE + "DATE "
            + PREFIX_CATEGORY + "CATEGORY\n"
            + PREFIX_DESCRIPTION + "DESCRIPTION\n"
            + "Note that each record should only have one category.\n"
            + "If date is not inputted, local date will be used.\n"
            + "Addition of futures expenses is not allowed.\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_AMOUNT + "123.23 "
            + PREFIX_DATE + "12/02/2002 "
            + PREFIX_CATEGORY + "Food "
            + PREFIX_DESCRIPTION + "Lunch with friends ";

    public static final String MESSAGE_SUCCESS = "New record added: %1$s";
    public static final String MESSAGE_SUCCESS_EXCEED_BUDGET = "Your spending in %s "
            + "category has exceeded the allocated budget!";

    private final Record toSpend;

    /**
     * Creates an AddCommand to add the specified {@code Record}
     */
    public SpendCommand(Record record) {
        requireNonNull(record);
        toSpend = record;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        boolean withinBudget = model.addRecord(toSpend);
        model.commitFinanceTracker();
        if (!withinBudget) {
            return new CommandResult (String.format(MESSAGE_SUCCESS_EXCEED_BUDGET, toSpend.getCategory()),
                    true, false, false);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toSpend), true, false, false);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SpendCommand // instanceof handles nulls
                && toSpend.equals(((SpendCommand) other).toSpend));
    }
}
