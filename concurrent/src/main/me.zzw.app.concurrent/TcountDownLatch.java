package main.me.zzw.app.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RunnableFuture;

/**
 * Created by infosea on 2016-09-09.
 */
class Driver {
    private final int N = 10;
    void main()  throws InterruptedException{
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(N);

        for(int i=0; i< N; i++){
            new Thread(new Worker(startSignal, doneSignal)).start();
        }

        doSomethingElse();
        startSignal.countDown();
        doSomethingElse();
        doneSignal.await();
    }
    private void doSomethingElse(){
        System.out.println("do something else");
    }
}

class Worker implements Runnable{
    private final CountDownLatch startSignal;
    private final CountDownLatch doneSignal;

    public Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
    }

    @Override
    public void run() {
        try {
            startSignal.await();
            doWork();
            doneSignal.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    void doWork(){
        System.out.println(" dowork");
    }
}
public class TcountDownLatch {
    public static void main(String[] args) {
    Driver driver = new Driver();
        try {
            driver.main();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
