package seedu.finance.logic.parser;

import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.finance.logic.commands.SearchCommand;
import seedu.finance.logic.parser.exceptions.ParseException;
import seedu.finance.model.record.CategoryContainsKeywordsPredicate;
import seedu.finance.model.record.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        String[] argsWithFlag = trimmedArgs.split("\\s+");
        String[] nameKeywords = Arrays.copyOfRange(argsWithFlag, 1, argsWithFlag.length);

        switch (argsWithFlag[0]) {
        case "-name" :
            return new SearchCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        case "-cat":
            return new SearchCommand(new CategoryContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.INVALID_FLAG));
        }
    }

}
