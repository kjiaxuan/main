package seedu.finance.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.finance.logic.parser.CliSyntax.FLAG_NAME;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.finance.testutil.TypicalIndexes.INDEX_FIRST_RECORD;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.finance.logic.commands.AllocateCommand;
import seedu.finance.logic.commands.ClearCommand;
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
import seedu.finance.logic.commands.SortCommand;
import seedu.finance.logic.commands.SpendCommand;
import seedu.finance.logic.commands.SummaryCommand;
import seedu.finance.logic.commands.UndoCommand;
import seedu.finance.logic.parser.exceptions.ParseException;
import seedu.finance.model.record.Description;
import seedu.finance.model.record.NameContainsKeywordsPredicate;
import seedu.finance.model.record.Record;
import seedu.finance.testutil.EditRecordDescriptorBuilder;
import seedu.finance.testutil.RecordBuilder;
import seedu.finance.testutil.RecordUtil;

public class FinanceTrackerParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final FinanceTrackerParser parser = new FinanceTrackerParser();

    @Test
    public void parseCommand_add() throws Exception {
        Record record = new RecordBuilder().build();
        SpendCommand command = (SpendCommand) parser.parseCommand(RecordUtil.getSpendCommand(record));
        assertEquals(new SpendCommand(record), command);
    }

    @Test
    public void parseCommand_addAlias() throws Exception {
        Record record = new RecordBuilder().build();
        SpendCommand command = (SpendCommand) parser.parseCommand(
                SpendCommand.COMMAND_ALIAS + " " + RecordUtil.getRecordDetails(record));
        assertEquals(new SpendCommand(record), command);
    }

    @Test
    public void parseCommand_allocate() throws Exception {
        assertTrue((parser.parseCommand(AllocateCommand.COMMAND_WORD + " " + PREFIX_AMOUNT + "123.00 "
                + PREFIX_CATEGORY + "Friends") instanceof AllocateCommand));
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clearAlias() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
    }
    @Test
    public void parseCommand_clearAlias2() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS2) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS2 + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_RECORD.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_RECORD), command);
    }

    @Test
    public void parseCommand_deleteAlias() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_RECORD.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_RECORD), command);
    }

    @Test
    public void parseCommand_deleteAlias2() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS2 + " " + INDEX_FIRST_RECORD.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_RECORD), command);
    }

    @Test
    public void parseCommand_description() throws Exception {
        final Description description = new Description("Some description.");
        DescriptionCommand command = (DescriptionCommand) parser.parseCommand(DescriptionCommand.COMMAND_WORD + " "
                + INDEX_FIRST_RECORD.getOneBased() + " " + PREFIX_DESCRIPTION + description.value);
        assertEquals(new DescriptionCommand(INDEX_FIRST_RECORD, description), command);
    }

    @Test
    public void parseCommand_descriptionAlias() throws Exception {
        final Description description = new Description("Some description.");
        DescriptionCommand command = (DescriptionCommand) parser.parseCommand(DescriptionCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_RECORD.getOneBased() + " " + PREFIX_DESCRIPTION + description.value);
        assertEquals(new DescriptionCommand(INDEX_FIRST_RECORD, description), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Record record = new RecordBuilder().build();
        EditCommand.EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder(record).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_RECORD.getOneBased() + " " + RecordUtil.getEditRecordDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_RECORD, descriptor), command);
    }

    @Test
    public void parseCommand_editAlias() throws Exception {
        Record record = new RecordBuilder().build();
        EditCommand.EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder(record).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_RECORD.getOneBased() + " " + RecordUtil.getEditRecordDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_RECORD, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_exitAlias() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_historyAlias() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS + " 3") instanceof HistoryCommand);
    }

    @Test
    public void parseCommand_historyAlias2() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS2) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS2 + " 3") instanceof HistoryCommand);
    }

    @Test
    public void parseCommand_increase() throws Exception {
        assertTrue((parser.parseCommand(IncreaseCommand.COMMAND_WORD + " " + PREFIX_AMOUNT + "123")
                instanceof IncreaseCommand));
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_listAlias() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_listAlias2() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS2) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS2 + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_reverse() throws Exception {
        assertTrue(parser.parseCommand(ReverseCommand.COMMAND_WORD) instanceof ReverseCommand);
        assertTrue(parser.parseCommand(ReverseCommand.COMMAND_WORD + " 3") instanceof ReverseCommand);
    }

    @Test
    public void parseCommand_reverseAlias() throws Exception {
        assertTrue(parser.parseCommand(ReverseCommand.COMMAND_ALIAS) instanceof ReverseCommand);
        assertTrue(parser.parseCommand(ReverseCommand.COMMAND_ALIAS + " 3") instanceof ReverseCommand);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_redoCommandAlias_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS + " 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_RECORD.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_RECORD), command);
    }

    @Test
    public void parseCommand_selectAlias() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_ALIAS + " " + INDEX_FIRST_RECORD.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_RECORD), command);
    }

    @Test
    public void parseCommand_selectAlias2() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_ALIAS2 + " " + INDEX_FIRST_RECORD.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_RECORD), command);
    }

    @Test
    public void parseCommand_search() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        SearchCommand command = (SearchCommand) parser.parseCommand(
                SearchCommand.COMMAND_WORD + " -name " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_searchAlias() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        SearchCommand command = (SearchCommand) parser.parseCommand(
                SearchCommand.COMMAND_ALIAS + " -name " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    //KIV: May want to add in alias as well
    @Test
    public void parseCommand_summary() throws Exception {
        assertTrue(parser.parseCommand(SummaryCommand.COMMAND_WORD) instanceof SummaryCommand);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        SortCommand command = (SortCommand) parser.parseCommand(
                SortCommand.COMMAND_WORD + " " + FLAG_NAME);
        assertEquals(new SortCommand(new RecordNameComparator()), command);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_undoCommandAlias_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS + " 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
