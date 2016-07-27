package me.zzw.app.nio;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * Created by infosea on 2016-07-27.
 */
public class Util {
    public static FileChannel fileChannel(String file, String model) throws FileNotFoundException {
        RandomAccessFile aFile = new RandomAccessFile(file, model);
        FileChannel inChannel = aFile.getChannel();
        return inChannel;
    }
}
