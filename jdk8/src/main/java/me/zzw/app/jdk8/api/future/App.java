package me.zzw.app.jdk8.api.future;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by infosea on 2016-07-27.
 */
class ArchiveSearchImpl implements ArchiveSearch {
    private List<String> lists ;

    public ArchiveSearchImpl(List<String> lists) {
        this.lists = lists;
    }

    @Override
    public String search(String target) {
        StringBuilder sb = new StringBuilder() ;
        return lists.stream().filter(str -> str.equals("target")).reduce("",(String result, String item) ->{ sb.append(" ").append( item);return sb.toString();});
    }
}
public class App {
    List<String> strs = Lists.newArrayList("target", "tar", "get", "target");
    ExecutorService executor =  Executors.newFixedThreadPool(10);
    ArchiveSearch searcher = new ArchiveSearchImpl(strs) ;
    void showSearch(final String target)
            throws InterruptedException {
        Future<String> future
                = executor.submit(new Callable<String>() {
            public String call() {
                return searcher.search(target);
            }});
        displayOtherThings(); // do other things while searching
        try {
            displayText(future.get()); // use future
        } catch (ExecutionException ex) { cleanup(); return; }
    }

    private void displayOtherThings() {
        System.out.println("after search...");
    }

    private void displayText(String text){
        System.out.println(text);
    }

    private void cleanup() {

    }

    public static void main(String[] args) throws InterruptedException {

        App app = new App();
        app.showSearch("target");
    }
}
