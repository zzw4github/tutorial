package me.zzw.lambda.methodReferences;

import me.zzw.lambda.person.Person;
import org.junit.Before;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;

/**
 * Created by infosea on 2016-07-21.
 */
public class ComparisonProvider {
    public int compareByName(Person a, Person b) {
        return a.getName().compareTo(b.getName());
    }

    public int compareByAge(Person a, Person b) {
        return a.getBirthday().compareTo(b.getBirthday());
    }

    public static <T, SOURCE extends Collection<T>, DEST extends Collection<T>>
    DEST transferElements(
            SOURCE sourceCollection,
            Supplier<DEST> collectionFactory) {

        DEST result = collectionFactory.get();
        for (T t : sourceCollection) {
            result.add(t);
        }
        return result;
    }

    public static void main(String... args) {
        List<Person> roster = new ArrayList<>();
        roster.add(new Person("person1", LocalDate.of(1988, 7, 23), Person.Sex.MALE, "111@sohu.com"));
        roster.add(new Person("person1", LocalDate.of(1999, 7, 23), Person.Sex.MALE, "111@sohu.com"));
        roster.add(new Person("person1", LocalDate.of(1995, 7, 23), Person.Sex.MALE, "111@sohu.com"));
        roster.add(new Person("person1", LocalDate.of(2000, 7, 23), Person.Sex.MALE, "111@sohu.com"));

        Person[] rosterAsArray = roster.toArray(new Person[roster.size()]);
        ComparisonProvider myComparisonProvider = new ComparisonProvider();
        //      Reference to an Instance Method of a Particular Object
        Arrays.sort(rosterAsArray, myComparisonProvider::compareByName);


       /* Reference to an Instance Method of an Arbitrary Object of a Particular Type
        The following is an example of a reference to an instance method of an arbitrary object of a particular type:
        */

        String[] stringArray = { "Barbara", "James", "Mary", "John",
                "Patricia", "Robert", "Michael", "Linda" };
        Arrays.sort(stringArray, String::compareToIgnoreCase);

        /*Reference to a Constructor
         */
        Set<Person> rosterSetLambda =
                transferElements(roster, () -> { return new HashSet<>(); });

        /*
        You can use a constructor reference in place of the lambda expression as follows:
         */
        Set<Person> rosterSet = transferElements(roster, HashSet::new);

        /*  The Java compiler infers that you want to create a HashSet collection that contains elements of type Person.
        Alternatively, you can specify this as follows:
        */
        Set<Person> rosterSet2 = transferElements(roster, HashSet<Person>::new);
    }
}
