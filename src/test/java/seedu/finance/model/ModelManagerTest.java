package seedu.finance.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.finance.model.Model.PREDICATE_SHOW_ALL_RECORD;
import static seedu.finance.testutil.TypicalRecords.APPLE;
import static seedu.finance.testutil.TypicalRecords.BANANA;
import static seedu.finance.testutil.TypicalRecords.BOB;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.finance.commons.core.GuiSettings;
import seedu.finance.model.record.NameContainsKeywordsPredicate;
import seedu.finance.model.record.Record;
import seedu.finance.model.record.exceptions.RecordNotFoundException;
import seedu.finance.testutil.FinanceTrackerBuilder;
import seedu.finance.testutil.RecordBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new FinanceTracker(), new FinanceTracker(modelManager.getFinanceTracker()));
        assertEquals(null, modelManager.getSelectedRecord());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setFinanceTrackerFilePath(Paths.get("finance/tracker/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setFinanceTrackerFilePath(Paths.get("new/finance/tracker/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setFinanceTrackerFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setFinanceTrackerFilePath(null);
    }

    @Test
    public void setFinanceTrackerFilePath_validPath_setsFinanceTrackerFilePath() {
        Path path = Paths.get("finance/tracker/file/path");
        modelManager.setFinanceTrackerFilePath(path);
        assertEquals(path, modelManager.getFinanceTrackerFilePath());
    }

    @Test
    public void hasRecordn_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasRecord(null);
    }

    @Test
    public void hasRecord_recordNotInFinanceTracker_returnsFalse() {
        assertFalse(modelManager.hasRecord(APPLE));
    }

    @Test
    public void hasRecord_recordInFinanceTracker_returnsTrue() {
        modelManager.addRecord(APPLE);
        assertTrue(modelManager.hasRecord(APPLE));
    }

    @Test
    public void deleteRecord_recordIsSelectedAndFirstRecordInFilteredRecordList_selectionCleared() {
        modelManager.addRecord(APPLE);
        modelManager.setSelectedRecord(APPLE);
        modelManager.deleteRecord(APPLE);
        assertEquals(null, modelManager.getSelectedRecord());
    }

    @Test
    public void deleteRecord_recordIsSelectedAndSecondRecordInFilteredRecordList_firstRecordSelected() {
        modelManager.addRecord(APPLE);
        modelManager.addRecord(BOB);
        assertEquals(Arrays.asList(APPLE, BOB), modelManager.getFilteredRecordList());
        modelManager.setSelectedRecord(BOB);
        modelManager.deleteRecord(BOB);
        assertEquals(APPLE, modelManager.getSelectedRecord());
    }

    @Test
    public void setRecord_recordIsSelected_selectedRecordUpdated() {
        modelManager.addRecord(APPLE);
        modelManager.setSelectedRecord(APPLE);
        Record updatedAlice = new RecordBuilder(APPLE).withAmount(VALID_AMOUNT_BOB).build();
        modelManager.setRecord(APPLE, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedRecord());
    }

    @Test
    public void getFilteredRecordList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredRecordList().remove(0);
    }

    @Test
    public void setSelectedRecord_recordNotInFilteredRecordList_throwsRecordNotFoundException() {
        thrown.expect(RecordNotFoundException.class);
        modelManager.setSelectedRecord(APPLE);
    }

    @Test
    public void setSelectedRecord_recordInFilteredRecordList_setsSelectedRecord() {
        modelManager.addRecord(APPLE);
        assertEquals(Collections.singletonList(APPLE), modelManager.getFilteredRecordList());
        modelManager.setSelectedRecord(APPLE);
        assertEquals(APPLE, modelManager.getSelectedRecord());
    }

    @Test
    public void equals() {
        FinanceTracker financeTracker = new FinanceTrackerBuilder().withRecord(APPLE).withRecord(BANANA).build();
        FinanceTracker differentFinanceTracker = new FinanceTracker();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(financeTracker, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(financeTracker, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different financeTracker -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentFinanceTracker, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = APPLE.getName().fullName.split("\\s+");
        modelManager.updateFilteredRecordList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(financeTracker, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORD);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setFinanceTrackerFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(financeTracker, differentUserPrefs)));
    }
}
