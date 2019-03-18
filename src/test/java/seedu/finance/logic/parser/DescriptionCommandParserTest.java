package seedu.finance.logic.parser;

import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.finance.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.finance.testutil.TypicalIndexes.INDEX_FIRST_RECORD;

import org.junit.Test;

import seedu.finance.commons.core.index.Index;
import seedu.finance.logic.commands.DescriptionCommand;
import seedu.finance.model.record.Description;

public class DescriptionCommandParserTest {
    private DescriptionCommandParser parser = new DescriptionCommandParser();
    private final String nonEmptyDescription = "Some description.";

    @Test
    public void parse_indexSpecified_success() {
        // with remark
        Index targetIndex = INDEX_FIRST_RECORD;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_DESCRIPTION + nonEmptyDescription;
        DescriptionCommand expectedCommand = new DescriptionCommand(INDEX_FIRST_RECORD,
                new Description(nonEmptyDescription));
        assertParseSuccess(parser, userInput, expectedCommand);

        // with no remark
        userInput = targetIndex.getOneBased() + " " + PREFIX_DESCRIPTION;
        expectedCommand = new DescriptionCommand(INDEX_FIRST_RECORD, new Description(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DescriptionCommand.MESSAGE_USAGE);

        // no index
        assertParseFailure(parser, DescriptionCommand.COMMAND_WORD + " " + nonEmptyDescription, expectedMessage);

        // no parameters
        assertParseFailure(parser, DescriptionCommand.COMMAND_WORD, expectedMessage);
    }
}