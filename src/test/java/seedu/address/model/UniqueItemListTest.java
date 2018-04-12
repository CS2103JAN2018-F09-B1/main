package seedu.address.model;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.model.item.UniqueItemList;
import seedu.address.model.person.UniquePersonList;

//@@author Alaru
public class UniqueItemListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asUnmodifiableList_modifyList_throwsUnsupportedOperationException() {
        UniqueItemList uniqueItemList = new UniqueItemList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueItemList.getItemList().remove(0);
    }

    @Test
    public void noDuplicateItems() {
        UniqueItemList uniqueItemList = new UniqueItemList();
        UniqueItemList otherItemList = new UniqueItemList();

        uniqueItemList.add("TEST");
        otherItemList.add("TEST");
        otherItemList.add("TEST");

        assertEquals(uniqueItemList, otherItemList);
    }
}
