package me.zzw.lambda.methodReferences;

import me.zzw.lambda.person.Person;

import java.util.Comparator;

/**
 * Created by infosea on 2016-07-21.
 */
public class PersonAgeComparator implements Comparator<Person> {
    public int compare(Person a, Person b) {
        return a.getBirthday().compareTo(b.getBirthday());
    }
}
