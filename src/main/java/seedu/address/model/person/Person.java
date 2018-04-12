package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person implements Comparable<Person> {

    private final Name name;
    private final MatriculationNumber matricNumber;
    private final Phone phone;
    private final Email email;
    private final Address address;
    private final DisplayPic displayPic;
    private final Participation participation;
    private final UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, MatriculationNumber matricNum, Phone phone, Email email,
                  Address address, DisplayPic displayPic, Participation participation, Set<Tag> tags) {
        requireAllNonNull(name, matricNum, phone, email, address, tags);
        this.name = name;
        this.matricNumber = matricNum;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.displayPic = displayPic;
        this.participation = participation;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
    }

    /**
     * Participation is not an essential field if one is creating a new Person object.
     */
    public Person(Name name, MatriculationNumber matricNum, Phone phone, Email email,
                  Address address, DisplayPic displayPic, Set<Tag> tags) {
        requireAllNonNull(name, matricNum, phone, email, address, tags);
        this.name = name;
        this.matricNumber = matricNum;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.displayPic = displayPic;
        this.participation = new Participation();
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
    }

    public Name getName() {
        return name;
    }

    public MatriculationNumber getMatricNumber() {
        return matricNumber;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public DisplayPic getDisplayPic() {
        return displayPic;
    }

    public Participation getParticipation() {
        return participation;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    public String getDetails() {
        return name.toString() + phone.toString() + email.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(this.getName())
                && otherPerson.getMatricNumber().equals(this.getMatricNumber())
                && otherPerson.getPhone().equals(this.getPhone())
                && otherPerson.getEmail().equals(this.getEmail())
                && otherPerson.getAddress().equals(this.getAddress())
                && otherPerson.getParticipation().equals(this.getParticipation());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, matricNumber, phone, email, address, displayPic, participation, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Matriculation Number: ")
                .append(getMatricNumber())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Participation marks: ")
                .append(getParticipation())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    @Override
    public int compareTo(Person otherPerson) {
        return this.name.toString().toLowerCase().compareTo(otherPerson.name.toString().toLowerCase());
    }

}
