package me.zzw.app.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;

/**
 * Created by infosea on 2016-09-09.
 */
public class Tphaser {
    void runTasks(List<Runnable> tasks){
        final Phaser phaser = new Phaser(1); // "1" to register self
        // create and start threads
        for (final Runnable task : tasks){
            phaser.register();
            new Thread() {
                @Override
                public void run() {
                    phaser.arriveAndAwaitAdvance(); // await all creation
                    task.run();
                }
            }.start();
        }

         // allow thread to start and deregister self
        phaser.arriveAndDeregister();
    }
    public static void main(String[] args){
        final int tasksSize = 5;
        List<Runnable> tasks = new ArrayList<>(tasksSize);
        for(int i= 0; i< tasksSize; i++){
            tasks.add(() -> System.out.println("task is running"));
        }
        Tphaser tphaser = new Tphaser();
        tphaser.runTasks(tasks);
    }

}
