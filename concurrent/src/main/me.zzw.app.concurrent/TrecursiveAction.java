package me.zzw.app.concurrent;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

/**
 * Created by infosea on 2016-09-09.
 */

public class TrecursiveAction {
    static class SortTask extends RecursiveAction{
        final long[] array; final int lo, hi;

        public SortTask(long[] array, int lo, int hi) {
            this.array = array;
            this.lo = lo;
            this.hi = hi;
        }

        public SortTask(long[] array) {
            this(array,0,array.length);
        }

        @Override
        protected void compute() {
            if (hi - lo < THRESHOLD)
              sortSquentially(lo, hi);
            else{
                int mid = (lo + hi) >>> 1;
                invokeAll(new SortTask(array, lo, mid),
                          new SortTask(array, mid, hi));
                merge(lo, mid, hi);
            }
        }
        // implementation details follow;
        static final int THRESHOLD = 1000;
        void sortSquentially(int lo,  int hi) {
            Arrays.sort(array, lo, hi);
        }
        void merge(int lo, int mid, int hi) {
            long[] buf = Arrays.copyOfRange(array, lo, mid);
            for(int i = 0, j = lo, k = mid; i < buf.length; j++)
                array[j] = (k == hi || buf[i] < array[k]) ?
                        buf[i++] : array[k++];
        }
    }

    public static void main(String[] args){
        long[] sortingLongs = new long[100000] ;
        for(int i = 0; i< 100000; i++){
            sortingLongs[i] = Long.valueOf(i).longValue();
        }
       TrecursiveAction.SortTask sortTask = new TrecursiveAction.SortTask(sortingLongs);
        sortTask.compute();
        for(int i = 0; i < sortTask.array.length; i++){
            System.out.println(sortTask.array[i]);
        }
    }
}
