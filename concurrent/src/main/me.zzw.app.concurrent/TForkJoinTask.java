package me.zzw.app.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by infosea on 2016-09-09.
 */
public class TForkJoinTask {
    public static String splie(String s){
        return s;
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String s = "abcdefg";
        ForkJoinTask<String> task = new RecursiveTask<String>() {
            @Override
            protected String compute() {
                if(s.length()>6){
                    //split(s);
                }
                return s;
            }
        };
        task.invoke();
        String ss = task.get();
        System.out.println(ss);
    }
}
