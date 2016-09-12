package me.zzw.app.concurrent;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by infosea on 2016-09-12.
 */
public class IAtomicInteger {
    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger();

        Executor executor = new ThreadPoolExecutor(3, 5, 100, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
        for (int j = 0; j < 100; j++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
//                        System.out.println(i.getAndIncrement());
                    System.out.println(i.getAndIncrement());
                }
            });
        }

    }
}
