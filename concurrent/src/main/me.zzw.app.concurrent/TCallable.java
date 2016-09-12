package me.zzw.app.concurrent;

import java.util.concurrent.Callable;

/**
 * Created by infosea on 2016-09-12.
 */
public class TCallable implements Callable<Integer> {
    private int i, j = 0;

    public TCallable(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public Integer call() throws Exception {
        return i + j;
    }

    public static void main(String[] args) throws Exception {
        TCallable callable = new TCallable(2,3);
       System.out.println( callable.call());
    }
}
