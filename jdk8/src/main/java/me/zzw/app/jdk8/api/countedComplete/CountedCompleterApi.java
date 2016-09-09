package me.zzw.app.jdk8.api.countedComplete;

import java.util.concurrent.CountedCompleter;

/**
 * Created by infosea on 2016-07-28.
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

    final E[] array;
    final MyOperation<E> op;
    final int lo, hi;

    ForEach(CountedCompleter<?> p, E[] array, MyOperation<E> op, int lo, int hi) {
        super(p);
        this.array = array;
        this.op = op;
        this.lo = lo;
        this.hi = hi;
    }

    public void compute() { // version 1
        if (hi - lo >= 2) {
            int mid = (lo + hi) >>> 1;
            setPendingCount(2); // must set pending count before fork
            new ForEach(this, array, op, mid, hi).fork(); // right child
            new ForEach(this, array, op, lo, mid).fork(); // left child
        } else if (hi > lo)
            op.apply(array[lo]);
        tryComplete();
    }
}

public class CountedCompleterApi {
    public static void main(String... args) {
        String[] strings = new String[]{"hello", "world"};
        ForEach.forEach(strings, new MyOperation<String>());

        strings = new String[]{"hello", "world", "this", "is ", "a", "countedCompleter"};
        ForEach<String> forEach = new ForEach<String>(null, strings, new MyOperation<String>(),0, 6);
        forEach.invoke();
    }
}