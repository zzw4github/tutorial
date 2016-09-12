package me.zzw.app.concurrent;

import java.util.concurrent.CountedCompleter;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by infosea on 2016-09-09.
 */
class MyOperation<E> {
    void apply(E e) {
        System.out.println(e);
    }
}

class ForEach<E> extends CountedCompleter<Void> {

    public static <E> void forEach(E[] array, MyOperation<E> op) {
        new ForEach<E>(null, array, op, 0, array.length).invoke();
    }

    final E[] array; final MyOperation<E> op; final int lo, hi;
    ForEach(CountedCompleter<?> p, E[] array, MyOperation<E> op, int lo, int hi){
        super(p);
        this.array = array;
        this.op = op;
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    public void compute() {
        if(hi -lo >=2 ){
            int mid = (lo + hi) >>> 1;
            setPendingCount(2); // must set pending count before fork
            new ForEach(this, array, op, mid, hi).fork(); // right child;
            new ForEach(this, array, op, lo, mid).fork(); // left child
        }else if (hi > lo)
            op.apply(array[lo]);
        tryComplete();
    }
}

class ForEach2<E> extends CountedCompleter<Void>{

    public static <E> void forEach(E[] array, MyOperation<E> op) {
        new ForEach<E>(null, array, op, 0, array.length).invoke();
    }

    final E[] array; final MyOperation<E> op; final int lo, hi;
    ForEach2(CountedCompleter<?> p, E[] array, MyOperation<E> op, int lo, int hi){
        super(p);
        this.array = array;
        this.op = op;
        this.lo = lo;
        this.hi = hi;
    }

    public void compute() { // version 2
        if (hi - lo >= 2) {
            int mid = (lo + hi) >>> 1;
            setPendingCount(1); // only one pending
            new ForEach2(this, array, op, mid, hi).fork(); // right child
            new ForEach2(this, array, op, lo, mid).compute(); // direct invoke
        } else {
            if (hi > lo)
                op.apply(array[lo]);
            tryComplete();
        }
    }
}

class ForEach3<E>  extends CountedCompleter<Void> {

    public static <E> void forEach(E[] array, MyOperation<E> op) {
        new ForEach3<E>(null, array, op, 0, array.length).invoke();
    }

    final E[] array;
    final MyOperation<E> op;
    final int lo, hi;

    ForEach3(CountedCompleter<?> p, E[] array, MyOperation<E> op, int lo, int hi) {
        super(p);
        this.array = array;
        this.op = op;
        this.lo = lo;
        this.hi = hi;
    }

    public void compute() { // version 3
        int l = lo, h = hi;
        while (h - l >= 2) {
            int mid = (l + h) >>> 1;
            addToPendingCount(1);
            new ForEach3(this, array, op, mid, h).fork(); // right child
            h = mid;
        }
        if (h > l)
            op.apply(array[l]);
        propagateCompletion();
    }
}

class Searcher<E> extends CountedCompleter<E> {

    final E[] array; final AtomicReference<E> result; final int lo, hi;

    Searcher(CountedCompleter<?> p, E[] array, AtomicReference<E> result, int lo, int hi) {
        super(p);
        this.array = array; this.result = result; this.lo = lo; this.hi = hi;
    }

    public E getRawResult() { return result.get(); }

    public void compute() { // similar to ForEach version 3
        int l = lo,  h = hi;
        while (result.get() == null && h >= l) {
            if (h - l >= 2) {
                int mid = (l + h) >>> 1;
                addToPendingCount(1);
                new Searcher(this, array, result, mid, h).fork();
                h = mid;
            }
            else {
                E x = array[l];
                if (matches(x) && result.compareAndSet(null, x))
                    quietlyCompleteRoot(); // root task is now joinable
                break;
            }
        }
        tryComplete(); // normally complete whether or not found
    }

    boolean matches(E e) {
        return true;
    } // return true if found


    public static <E> E search(E[] array) {
        return new Searcher<E>(null, array, new AtomicReference<E>(), 0, array.length).invoke();
    }

}

class MyMapper<E> { E apply(E v) {  return v;  } }
class MyReducer<E> { E apply(E x, E y) {  return x;  } }
class MapReducer<E> extends CountedCompleter<E> {
    final E[] array; final MyMapper<E> mapper;
    final MyReducer<E> reducer; final int lo, hi;
    MapReducer<E> sibling;
    E result;
    MapReducer(CountedCompleter<?> p, E[] array, MyMapper<E> mapper,
               MyReducer<E> reducer, int lo, int hi) {
        super(p);
        this.array = array; this.mapper = mapper;
        this.reducer = reducer; this.lo = lo; this.hi = hi;
    }
    public void compute() {
        if (hi - lo >= 2) {
            int mid = (lo + hi) >>> 1;
            MapReducer<E> left = new MapReducer(this, array, mapper, reducer, lo, mid);
            MapReducer<E> right = new MapReducer(this, array, mapper, reducer, mid, hi);
            left.sibling = right;
            right.sibling = left;
            setPendingCount(1); // only right is pending
            right.fork();
            left.compute();     // directly execute left
        }
        else {
            if (hi > lo)
                result = mapper.apply(array[lo]);
            tryComplete();
        }
    }
    public void onCompletion(CountedCompleter<?> caller) {
        if (caller != this) {
            MapReducer<E> child = (MapReducer<E>)caller;
            MapReducer<E> sib = child.sibling;
            if (sib == null || sib.result == null)
                result = child.result;
            else
                result = reducer.apply(child.result, sib.result);
        }
    }
    public E getRawResult() { return result; }

    public static <E> E mapReduce(E[] array, MyMapper<E> mapper, MyReducer<E> reducer) {
        return new MapReducer<E>(null, array, mapper, reducer,
                0, array.length).invoke();
    }
}



public class TCountedCompleter {
    public static void main(String[] args) {
        ForEach.forEach(new Integer[]{2,6,4,3,6,7,8,4,3,6,8,9,0,5,4,3,2}, new MyOperation<Integer>());
        System.out.println("-----------");
        ForEach2.forEach(new Integer[]{2,6,4,3,6,7,8,4,3,6,8,9,0,5,4,3,2}, new MyOperation<Integer>());
        System.out.println("-----------");
        ForEach3.forEach(new Integer[]{2,6,4,3,6,7,8,4,3,6,8,9,0,5,4,3,2}, new MyOperation<Integer>());

    }
}
