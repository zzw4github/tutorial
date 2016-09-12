package me.zzw.app.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by infosea on 2016-09-09.
 */
class Driver2 {
    private final int N = 10;
    private CountDownLatch doneSignal = new CountDownLatch(N);

    void main() throws InterruptedException {
        CountDownLatch doneSignal = new CountDownLatch(N);
        Executor e =  new ScheduledThreadPoolExecutor(5);

        for(int i=0; i< N; ++i)
            e.execute(new WorkRunnable(doneSignal, i));

        doneSignal.await();
    }


}

class WorkRunnable implements Runnable{
    private CountDownLatch doneSignal;
    private int i;

    public WorkRunnable(CountDownLatch doneSignal, int i) {
        this.doneSignal = doneSignal;
        this.i = i;
    }

    @Override
    public void run() {
        doWork(i);
        doneSignal.countDown();
    }

    private void  doWork(int i){
        System.out.println( "do work " + i);
    }
}
public class TcountDownLatch2 {
    public static void main(String[] args){
    Driver2 driver2 = new Driver2();
        try {
            driver2.main();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
