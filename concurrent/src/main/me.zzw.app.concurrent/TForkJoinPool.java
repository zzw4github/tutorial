package me.zzw.app.concurrent;

import java.io.File;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by infosea on 2016-09-12.
 */
class DocumentTask extends ForkJoinTask<File> {
    File file;

    public DocumentTask(File file) {
        this.file = file;
    }

    @Override
    public File getRawResult() {
        return file;
    }

    @Override
    protected void setRawResult(File value) {
        file = file;
    }

    @Override
    protected boolean exec() {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files)
                new DocumentTask(f).fork();
            return true;
        } else {
            System.out.println(file.getName());
            return true;
        }

    }
}
class ListFile {
    public void listFile(File file) {
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f : files){
                listFile(f);
            }
        }else {
            System.out.println(file.getName());
        }
    }
}
public class TForkJoinPool {

    public static void main(String[] args){
//       long begin  =System.currentTimeMillis();
//        ForkJoinPool pool = new  ForkJoinPool(4);
//        pool.submit( new DocumentTask(new File("F:\\ilasLogRead")));
//        try {
//            pool.awaitTermination(2, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        long end = System.currentTimeMillis();
//        System.out.println(end - begin);



        long begin2 = System.currentTimeMillis();
        ListFile listFile = new ListFile();
        listFile.listFile(new File("F:\\ilasLogRead"));
        long end2 = System.currentTimeMillis();
        System.out.println(end2 - begin2);
    }
}
