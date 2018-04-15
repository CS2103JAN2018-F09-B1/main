package seedu.address.model.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.DisplayPic;
import seedu.address.model.person.Email;
import seedu.address.model.person.MatriculationNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Participation;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDescription;
import seedu.address.model.task.Title;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data persons.
 */
public class SampleDataUtil {

    private static LocalDate now = LocalDate.now();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static String tutorialDeadline = now.plusDays(5).format(formatter);
    private static String assignmentDeadline = now.plusDays(6).format(formatter);
    private static String reviseDeadline = now.plusDays(20).format(formatter);
    private static String biddingDeadline = now.plusMonths(1).format(formatter);

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new MatriculationNumber("A1234567X"),
                new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                new DisplayPic(), new Participation("13"),
                getTagSet("1")),
            new Person(new Name("Bernice Yu"), new MatriculationNumber("A2234567Y"),
                new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                new DisplayPic(), new Participation("52"),
                getTagSet("2")),
            new Person(new Name("Charlotte Oliveiro"), new MatriculationNumber("A1234567X"),
                new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new DisplayPic(), new Participation("77"),
                getTagSet("exchange")),
            new Person(new Name("David Li"), new MatriculationNumber("A3234567J"),
                new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                new DisplayPic(), new Participation("44"),
                getTagSet("retaking")),
            new Person(new Name("Irfan Ibrahim"), new MatriculationNumber("A4234567K"),
                new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                new DisplayPic(), new Participation("30"),
                getTagSet("friend")),
            new Person(new Name("Roy Balakrishnan"), new MatriculationNumber("A5234567G"),
                new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                new DisplayPic(), new Participation("78"),
                getTagSet("brother"))
        };
    }

    /**
     * Contains utility methods for populating {@code AddressBook} with sample data tasks.
     */
    public static Task[] getSampleTasks() {
        return new Task[] {
            new Task(new Title("Prepare Tut"), new TaskDescription("Prepare tutorial contents for friday Tutorial"),
                new Deadline(tutorialDeadline), new Priority("1")),
            new Task(new Title("2106 assignment"), new TaskDescription("Start doing CS2106 term assignment"),
                new Deadline(assignmentDeadline), new Priority("3")),
            new Task(new Title("Sem report"), new TaskDescription("Prepare for end of semester report"),
                new Deadline(tutorialDeadline), new Priority("3")),
            new Task(new Title("Bidding"), new TaskDescription("Prepare for bidding modules for the coming semester"),
                new Deadline(biddingDeadline), new Priority("3")),
            new Task(new Title("Revise 2010"), new TaskDescription("Revise the contents for CS2010"),
                new Deadline(reviseDeadline), new Priority("2"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            for (Task sampleTask : getSampleTasks()) {
                sampleAb.addTask(sampleTask);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
