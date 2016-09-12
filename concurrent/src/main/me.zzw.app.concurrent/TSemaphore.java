package me.zzw.app.concurrent;

import java.util.Objects;
import java.util.concurrent.Semaphore;

/**
 * Created by infosea on 2016-09-09.
 */
class Pool{
    private static final int MAX_VAILABLE = 100;
    private final Semaphore available = new Semaphore(MAX_VAILABLE, true);

    public Object getItem() throws InterruptedException {
        available.acquire();
        return getNextAvailableItem();
    }

    public void putItem(Object x) {
        if (markAsUnused(x))
            available.release();
    }

    // Not a particularly efficient data structure; just for demo

    protected Object[] items = new Integer[MAX_VAILABLE];

    public Pool(){
        for (int i=0;i<MAX_VAILABLE;i++){
            items[i] = i;
        }
    }

    protected boolean[] used = new boolean[MAX_VAILABLE];

    protected synchronized Object getNextAvailableItem() {
        for (int i = 0; i < MAX_VAILABLE; ++i){
            if ( !used[i]){
                used[i] = true;
                return items[i];
            }
        }
        return null; // not reached
    }

    protected synchronized boolean markAsUnused(Object item) {
        for (int i = 0; i< MAX_VAILABLE; ++i){
            if (item == items[i]) {
                if (used[i]){
                    used[i] = false;
                    return true;
                }else
                    return false;
            }
        }
        return false;
    }
}
public class TSemaphore {
    public static void main(String[] args) {
        Pool pool = new Pool();
        Object object = new Integer(1);
        pool.putItem(object);
        Object obj  = null;
        try {
            obj = pool.getItem();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(obj);
    }
}
