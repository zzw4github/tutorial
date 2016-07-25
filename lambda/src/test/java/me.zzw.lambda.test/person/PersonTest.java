package me.zzw.lambda.test.person;

import me.zzw.lambda.methodReferences.PersonAgeComparator;
import me.zzw.lambda.person.CheckPerson;
import me.zzw.lambda.person.Person;
import me.zzw.lambda.person.Util;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by infosea on 2016-07-21.
 */
public class PersonTest {
    List<Person> roster = new ArrayList<>();
    Person[] rosterArray ;
    @Before
    public void setUp() throws Exception {
        roster.add(new Person("person1", LocalDate.of(1988, 7, 23), Person.Sex.MALE, "111@sohu.com"));
        roster.add(new Person("person1", LocalDate.of(1999, 7, 23), Person.Sex.MALE, "111@sohu.com"));
        roster.add(new Person("person1", LocalDate.of(1995, 7, 23), Person.Sex.MALE, "111@sohu.com"));
        roster.add(new Person("person1", LocalDate.of(2000, 7, 23), Person.Sex.MALE, "111@sohu.com"));

        rosterArray =  roster.toArray(new Person[roster.size()]);

    }

    //      Approach 4: Specify Search Criteria Code in an Anonymous Class
    @Test
    public void printPersonsWithAnonymousClassTest(){

        Util.printPersons(
                roster,
                new CheckPerson() {
                    public boolean test(Person p) {
                        return p.getGender() == Person.Sex.MALE
                                && p.getAge() >= 18
                                && p.getAge() <= 25;
                    }
        });
    }

    //        Approach 5: Specify Search Criteria Code with a Lambda Expression
    @Test
    public void printPersonsWithLambdaTest(){
        Util.printPersons(
                roster,
                (Person p) -> p.getGender() == Person.Sex.MALE
                        && p.getAge() >= 18
                        && p.getAge() <= 25
        );
    }

    //    Approach 6: Use Standard Functional Interfaces with Lambda Expressions
    @Test
    public void printPersonsPredicateTest(){
        Util.printPersonsWithPredicate(roster,
                p -> p.getGender() == Person.Sex.MALE
                        && p.getAge() >= 18
                        && p.getAge() <= 25);
    }

    //Approach 6: Use Standard Functional Interfaces with Lambda Expressions
    @Test
    public void processPersonsTest(){
        Util.processPersons(
                roster,
                p -> p.getGender() == Person.Sex.MALE
                        && p.getAge() >= 18
                        && p.getAge() <= 25,
                p -> p.printPerson()
        );
    }
    @Test
    public void processPersonsWithFunctionTest() {
        Util.processPersonsWithFunction(
                roster,
                p -> p.getGender() == Person.Sex.MALE
                        && p.getAge() >= 18
                        && p.getAge() <= 25,
                p -> p.getEmailAddress(),
                email -> System.out.println(email)
        );
    }

    //Approach 8: Use Generics More Extensively
    @Test
    public void processElementsTest(){
        Util.processElements(
                roster,
                p -> p.getGender() == Person.Sex.MALE
                        && p.getAge() >= 18
                        && p.getAge() <= 25,
                p -> p.getEmailAddress(),
                email -> System.out.println(email)
        );
    }

    @Test
    public void processPersonsWithAggregateOPeratrionsTest(){
        Util.processPersons(roster);
    }

    @Test
    public void sort(){
        Util.sort(Util.toArray(roster), new PersonAgeComparator());
    }

    /*
     the interface Comparator is a functional interface. Therefore,
     you could use a lambda expression instead of defining and then creating a new instance of a class that implements Comparator:
     */
    @Test
    public void sort2(){
        Arrays.sort(rosterArray,
                (Person a, Person b) -> {
                    return a.getBirthday().compareTo(b.getBirthday());
                }
        );
    }

    /**
     *     this method to compare the birth dates of two Person instances already exists as Person.compareByAge.
     *     You can invoke this method instead in the body of the lambda expression:
     */
    @Test
    public void sort3(){
        Arrays.sort(rosterArray,
                (a, b) -> Person.compareByAge(a, b)
        );
    }

    /*this lambda expression invokes an existing method, you can use a method reference instead of a lambda expression
    The method reference Person::compareByAge is semantically the same as the lambda expression (a, b) -> Person.compareByAge(a, b).
    Each has the following characteristics:
        Its formal parameter list is copied from Comparator<Person>.compare, which is (Person, Person).
        Its body calls the method Person.compareByAge.
    method reference Person::compareByAge is a reference to a static method.
     */
    @Test
    public void sort4(){
        Arrays.sort(rosterArray, Person::compareByAge);
    }
}
