package seedu.finance.logic.parser;

import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.finance.logic.commands.AllocateCommand;
import seedu.finance.logic.commands.ClearCommand;
import seedu.finance.logic.commands.Command;
import seedu.finance.logic.commands.DeleteCommand;
import seedu.finance.logic.commands.DescriptionCommand;
import seedu.finance.logic.commands.EditCommand;
import seedu.finance.logic.commands.ExitCommand;
import seedu.finance.logic.commands.HelpCommand;
import seedu.finance.logic.commands.HistoryCommand;
import seedu.finance.logic.commands.IncreaseCommand;
import seedu.finance.logic.commands.ListCommand;
import seedu.finance.logic.commands.RedoCommand;
import seedu.finance.logic.commands.ReverseCommand;
import seedu.finance.logic.commands.SearchCommand;
import seedu.finance.logic.commands.SelectCommand;
import seedu.finance.logic.commands.SetCommand;
import seedu.finance.logic.commands.SortCommand;
import seedu.finance.logic.commands.SpendCommand;
import seedu.finance.logic.commands.SummaryCommand;
import seedu.finance.logic.commands.ThemeCommand;
import seedu.finance.logic.commands.UndoCommand;
import seedu.finance.logic.parser.exceptions.ParseException;


/**
 * Parses user input.
 */
public class FinanceTrackerParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord.toLowerCase()) {

        case ThemeCommand.COMMAND_WORD:
        case ThemeCommand.COMMAND_ALIAS:
            return new ThemeCommandParser().parse(arguments);

        case SetCommand.COMMAND_WORD:
            return new SetCommandParser().parse(arguments);

        case AllocateCommand.COMMAND_WORD:
            return new AllocateCommandParser().parse(arguments);

        case IncreaseCommand.COMMAND_WORD:
            return new IncreaseCommandParser().parse(arguments);

        case DescriptionCommand.COMMAND_WORD:
        case DescriptionCommand.COMMAND_ALIAS:
            return new DescriptionCommandParser().parse(arguments);

        case SpendCommand.COMMAND_WORD:
        case SpendCommand.COMMAND_ALIAS:
            return new SpendCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
        case SelectCommand.COMMAND_ALIAS2:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
        case DeleteCommand.COMMAND_ALIAS2:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
        case ClearCommand.COMMAND_ALIAS2:
            return new ClearCommand();

        case SearchCommand.COMMAND_WORD:
        case SearchCommand.COMMAND_ALIAS:
            return new SearchCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
        case ListCommand.COMMAND_ALIAS2:
            return new ListCommand();

        case ReverseCommand.COMMAND_WORD:
        case ReverseCommand.COMMAND_ALIAS:
            return new ReverseCommand();


        case HistoryCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_ALIAS:
        case HistoryCommand.COMMAND_ALIAS2:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
        case ExitCommand.COMMAND_ALIAS:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        case SummaryCommand.COMMAND_WORD:
            //Have to add in alias for summary command
            //case SummaryCommand.COMMAND_ALIAS:
            return new SummaryCommand();
            
        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
