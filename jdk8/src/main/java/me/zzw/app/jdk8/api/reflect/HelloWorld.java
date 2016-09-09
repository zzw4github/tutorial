package me.zzw.app.jdk8.api.reflect;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by infosea on 2016-07-28.
 */
public class HelloWorld {

    public static void main(String[] args){
        Arrays.asList(HelloWorld.class.getMethods()).stream().forEach((Method method) -> System.out.println(method.getName()));
    }
}
