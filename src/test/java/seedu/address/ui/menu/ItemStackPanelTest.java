package seedu.address.ui.menu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertStackPanelDisplaysItem;

import org.junit.Test;

import guitests.guihandles.menu.ItemStackPanelHandle;
import seedu.address.model.menu.Item;
import seedu.address.testutil.menu.ItemBuilder;
import seedu.address.ui.GuiUnitTest;

public class ItemStackPanelTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Item itemWithNoTags = new ItemBuilder().withTags(new String[0]).build();
        ItemStackPanel itemStackPanel = new ItemStackPanel(itemWithNoTags);
        uiPartRule.setUiPart(itemStackPanel);
        assertStackPanelDisplay(itemStackPanel, itemWithNoTags);

        // with tags
        Item itemWithTags = new ItemBuilder().build();
        itemStackPanel = new ItemStackPanel(itemWithTags);
        uiPartRule.setUiPart(itemStackPanel);
        assertStackPanelDisplay(itemStackPanel, itemWithTags);
    }

    @Test
    public void equals() {
        Item item = new ItemBuilder().build();
        ItemStackPanel itemStackPanel = new ItemStackPanel(item);

        // same item, same index -> returns true
        ItemStackPanel copy = new ItemStackPanel(item);
        assertTrue(itemStackPanel.equals(copy));

        // same object -> returns true
        assertTrue(itemStackPanel.equals(itemStackPanel));

        // null -> returns false
        assertFalse(itemStackPanel.equals(null));

        // different types -> returns false
        assertFalse(itemStackPanel.equals(0));

        // different item, same index -> returns false
        Item differentItem = new ItemBuilder().withName("differentName").build();
        assertFalse(itemStackPanel.equals(new ItemStackPanel(differentItem)));
    }

    /**
     * Asserts that {@code itemStackPanel} displays the details of {@code expectedItem} correctly.
     */
    private void assertStackPanelDisplay(ItemStackPanel itemStackPanel, Item expectedItem) {
        guiRobot.pauseForHuman();

        ItemStackPanelHandle itemStackPanelHandle = new ItemStackPanelHandle(itemStackPanel.getRoot());

        // verify item details are displayed correctly
        assertStackPanelDisplaysItem(expectedItem, itemStackPanelHandle);
    }
}
