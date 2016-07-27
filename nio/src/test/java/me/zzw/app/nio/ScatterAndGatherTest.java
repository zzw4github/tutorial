package me.zzw.app.nio;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by infosea on 2016-07-27.
 */
public class ScatterAndGatherTest {
    @Test
    public void testScatter() throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("data/nio-data.txt", "rw");
        FileChannel channel = aFile.getChannel();

        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body   = ByteBuffer.allocate(1024);
        ByteBuffer[] bufferArray = { header, body };
        channel.read(bufferArray);

        for(byte b :header.array()){
            System.out.print((char)b); // read 1 byte at a time
        }

        header.clear(); //make buffer ready for writing
       if(body.hasRemaining()){
           for(byte b :body.array()){
               System.out.print((char)b); // read 1 byte at a time
           }
       }

        header.clear();
        body.clear();
         header = ByteBuffer.allocate(128);
         body   = ByteBuffer.allocate(1024);
        channel.write(bufferArray);
        System.out.println(header.hasRemaining());
        aFile.close();
    }
@Test
    public void testTransferFrom() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile("data/fromFile.txt", "rw");
        FileChannel      fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("data/toFile.txt", "rw");
        FileChannel      toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();

        toChannel.transferFrom(fromChannel, position, count );
    }
    @Test
    public void testTransferTo() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile("data/fromFile.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("data/toFile.txt", "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();

        fromChannel.transferTo(position, count, toChannel);
    }
}
