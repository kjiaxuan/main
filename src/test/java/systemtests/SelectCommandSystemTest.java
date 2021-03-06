package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX;
import static seedu.finance.logic.commands.SelectCommand.MESSAGE_SELECT_RECORD_SUCCESS;
import static seedu.finance.testutil.TestUtil.getLastIndex;
import static seedu.finance.testutil.TestUtil.getMidIndex;
import static seedu.finance.testutil.TypicalIndexes.INDEX_FIRST_RECORD;
import static seedu.finance.testutil.TypicalRecords.KEYWORD_MATCHING_DONUT;

import org.junit.Test;

import seedu.finance.commons.core.index.Index;
import seedu.finance.logic.commands.RedoCommand;
import seedu.finance.logic.commands.SelectCommand;
import seedu.finance.logic.commands.UndoCommand;
import seedu.finance.model.Model;

public class SelectCommandSystemTest extends FinanceTrackerSystemTest {
    @Test
    public void select() {

        /* ------------------------ Perform select operations on the shown unfiltered list -------------------------- */

        /* Case: select the first card in the record list, command with leading spaces and trailing spaces
         * -> selected
         */

        String command = "   " + SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_RECORD.getOneBased() + "   ";
        /*assertCommandSuccess(command, INDEX_FIRST_RECORD);*/


        /* Case: select the last card in the record list -> selected */

        Index recordCount = getLastIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " " + recordCount.getOneBased();
        assertCommandSuccess(command, recordCount);


        /* Case: undo previous selection -> rejected */

        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);


        /* Case: redo selecting last card in the list -> rejected */

        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);


        /* Case: select the middle card in the record list -> selected */

        Index middleIndex = getMidIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
        assertCommandSuccess(command, middleIndex);


        /* Case: select the current selected card -> selected */

        assertCommandSuccess(command, middleIndex);


        /* Case: mixed case command word -> selected */

        assertCommandSuccess("SeLeCt 1", INDEX_FIRST_RECORD);

        /* Case: use command alias "sel" -> selected */

        assertCommandSuccess("sel 1", INDEX_FIRST_RECORD);

        /* Case: use command alias "s" -> selected */

        assertCommandSuccess("s 1", INDEX_FIRST_RECORD);


        /* ------------------------ Perform select operations on the shown filtered list ---------------------------- */



        /* Case: filtered record list, select index within bounds of finance tracker but out of bounds of record list
         * -> rejected
         */

        showRecordsWithName(KEYWORD_MATCHING_DONUT);
        int invalidIndex = getModel().getFinanceTracker().getRecordList().size();
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);


        /* Case: filtered record list, select index within bounds of finance tracker and record list -> selected */

        Index validIndex = Index.fromOneBased(1);
        assertTrue(validIndex.getZeroBased() < getModel().getFilteredRecordList().size());
        command = SelectCommand.COMMAND_WORD + " " + validIndex.getOneBased();
        assertCommandSuccess(command, validIndex);


        /* ----------------------------------- Perform invalid select operations ------------------------------------ */



        /* Case: invalid index (0) -> rejected */

        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));


        /* Case: invalid index (-1) -> rejected */

        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));


        /* Case: invalid index (size + 1) -> rejected */

        invalidIndex = getModel().getFilteredRecordList().size() + 1;
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);


        /* Case: invalid arguments (alphabets) -> rejected */

        assertCommandFailure(SelectCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));


        /* Case: invalid arguments (extra argument) -> rejected */

        assertCommandFailure(SelectCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));


        /* Case: select from empty finance tracker -> rejected */

        deleteAllRecords();
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_RECORD.getOneBased(),
                MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    }


    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing select command with the
     * {@code expectedSelectedCardIndex} of the selected record.<br>
     * 4. {@code Storage} and {@code RecordListPanel} remain unchanged.<br>
     * 5. Selected card is at {@code expectedSelectedCardIndex} and the browser url is updated accordingly.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see FinanceTrackerSystemTest#assertSelectedCardChanged(Index)
     */

    private void assertCommandSuccess(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(
                MESSAGE_SELECT_RECORD_SUCCESS, expectedSelectedCardIndex.getOneBased());
        int preExecutionSelectedCardIndex = getRecordListPanel().getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
            assertSelectedCardUnchanged();
        } else {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }


    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code RecordListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */

    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
