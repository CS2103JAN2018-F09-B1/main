package seedu.address.model.item;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

/**
 * A list of filepaths that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see String#equals(Object)
 */
//@@author Alaru
public class UniqueItemList {

    private final ArrayList<String> internalList = new ArrayList<>();

    /**
     * Returns true if the list contains an equivalent item/filepath as the given argument.
     */
    public boolean contains(String toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a filepath to the list.
     *
     */
    public void add(String toAdd) {
        requireNonNull(toAdd);
        if (!contains(toAdd)) {
            internalList.add(toAdd);
        }
    }

    /**
     * Removes the equivalent item/filepath from the list.
     *
     */
    public void remove(String toRemove) {
        requireNonNull(toRemove);
        internalList.remove(toRemove);
    }

    public void setItemList(List<String> replacement) {
        for (String item : replacement) {
            if (!this.internalList.contains(item)) {
                this.internalList.add(item);
            }
        }
    }

    /**
     * Puts all the display picture paths into the UniqueItemList
     * @param persons is a UniquePersonList which contains all the people in the application
     */
    public void updateItemList(ObservableList<Person> persons) {
        for (Person p : persons) {
            add(p.getDisplayPic().toString());
        }
    }

    public void clear() {
        this.internalList.clear();
    }

    public List<String> getItemList() {
        List<String> toReturn = new ArrayList<>(internalList);
        return Collections.unmodifiableList(toReturn);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueItemList // instanceof handles nulls
                && this.internalList.equals(((UniqueItemList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}

