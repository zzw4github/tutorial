package me.zzw.app.concurrent;

import jdk.nashorn.internal.codegen.CompilerConstants;

import java.util.concurrent.*;

/**
 * Created by infosea on 2016-09-12.
 */

 class CustomThreadPoolExecutor extends ThreadPoolExecutor {
//    private  Callable<V> c;
    private Runnable r;

    public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    static class CustomTask<V> implements RunnableFuture<V> {

        @Override
        public void run() {

        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public V get() throws InterruptedException, ExecutionException {
            return null;
        }

        @Override
        public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    }

//    protected <V> RunnableFuture<V> newTaskFor(Callable<V> c) {
//        return new CustomTask<V>(c);
//    }
//    protected <V> RunnableFuture<V> newTaskFor(Runnable r, V v) {
//        return new CustomTask<V>(r, v);
//    }
    // ... add constructors, etc.


}
public class TAbstractExecutorService {
}
