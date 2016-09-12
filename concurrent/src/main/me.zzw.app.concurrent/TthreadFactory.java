package me.zzw.app.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by infosea on 2016-09-09.
 */
public class TthreadFactory {
    public static void main(String[] args){
        ThreadFactory factory = Executors.defaultThreadFactory();
    }
}
