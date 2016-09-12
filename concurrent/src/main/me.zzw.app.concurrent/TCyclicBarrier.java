package me.zzw.app.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by infosea on 2016-09-09.
 */
class Solver {
    final int N;
    final float[][] data;
    final CyclicBarrier barrier;

    class Worker implements Runnable {
        int myRow;

        public Worker(int myRow) {
            this.myRow = myRow;
        }

        @Override
        public void run() {
           // while( !done()){
                processRow(myRow);

                try{
                    barrier.await();
                }catch (InterruptedException e){
                    return;
                }catch (BrokenBarrierException e){
                    return;
                }
           // }
        }

        private boolean done() {
            return true;
        }

        private void processRow(int myRow) {
            System.out.println("is processing row " + myRow);
        }
    }

    public Solver(float[][] matrix) {
        this.data = matrix;
        this.N = matrix.length;
        Runnable barrierAction = () ->mergeRows(data);
        barrier = new CyclicBarrier(N, barrierAction);

        List<Thread> threads = new ArrayList<>(N);
        for (int i =0; i< N ;i++){
            Thread thread = new Thread(new Worker(i));
            threads.add(thread);
            thread.start();
        }

        // wait until done
        for ( Thread thread : threads)
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    private void mergeRows(float[][] data) {
        System.out.println( "has finished " + data);
    }
}
public class TCyclicBarrier {
  public static void main(String[] args){
      float[][] matrix = new float[][]{{1.2f,1,3f},{2.2f,2.3f}};
      Solver solver = new Solver(matrix);
  }
}
