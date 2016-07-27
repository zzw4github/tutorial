package me.zzw.app.jdk8;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by infosea on 2016-07-27.
 */
public class FunctionalTest {
    @Test
    public void toLowerCase() {
        List<String> names = new ArrayList<>();
        names.add("TaoBao");
        names.add("ZhiFuBao");
        List<String> lowercaseNames = new ArrayList<>();
        for (String name : names) {
            lowercaseNames.add(name.toLowerCase());
        }
    }

    @Test
    public void toLowerCase2() {
        List<String> names = new ArrayList<>();
        names.add("TaoBao");
        names.add("ZhiFuBao");
         FluentIterable.from(names).transform(new Function<String, String>() {
            @Override
            public String apply(String name) {
                return name.toLowerCase();
            }
        }).toList().stream().forEach(System.out::println);
    }
}
