package main.me.zzw.app.concurrent;

import jdk.nashorn.internal.codegen.CompilerConstants;

import java.io.File;
import java.util.concurrent.*;

/**
 * Created by infosea on 2016-09-09.
 */
interface ArchiveSearcher {
    String search(String target);
}

class App {
    ExecutorService executor = Executors.newFixedThreadPool(5);
    ArchiveSearcher searcher = new ArchiveSearcher() {
        private String[] strings ={ "a","b","c", "a", "e", "f", "g"};
        @Override
        public String search(String target) {
            String strs = "";
            for(String s : strings){
                if(s.equals(target)){
                    strs +=s;
                }
            }
            return strs;
        }
    };
    void showSearch(final String target) throws InterruptedException {
        Future<String> future = executor.submit(() -> searcher.search(target));
        displayOtherThings();
        try {
            displayText(future.get());
        }catch (ExecutionException ex) {cleanup(); return;}
    }

    void showSearch2(final String target) throws InterruptedException {
        FutureTask<String> future =
                new FutureTask<String>( () -> searcher.search(target));
        executor.execute(future);
        displayOtherThings();
        try {
            displayText(future.get());
        }catch (ExecutionException ex) {cleanup(); return;}
    }

    void shutdown(){
        executor.shutdownNow();
    }

    private void displayOtherThings() {
        System.out.println(" display other things");
    }

    private void displayText(String text){
        System.out.println(text);
    }

    private void  cleanup(){
        executor.shutdown();
    }

}
public class TFurture {
    public static void main(String[] args){
    App app = new App();
        try {
            app.showSearch2("a");
            app.showSearch2("b");
            app.showSearch2("c");
            app.showSearch2("e");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        app.shutdown();
    }
}
