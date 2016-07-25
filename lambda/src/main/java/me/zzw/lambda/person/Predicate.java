package me.zzw.lambda.person;

/**
 * Created by infosea on 2016-07-21.
 */
public interface Predicate<Person> {
    boolean test(Person t);
}