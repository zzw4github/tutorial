package me.zzw.app.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by infosea on 2016-07-27.
 */
public class SelectorTest {
    @Test
    public void testSelector() throws IOException, InterruptedException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 80));
        Selector selector = Selector.open();
        socketChannel.configureBlocking(false);
        SelectionKey key = socketChannel.register(selector, SelectionKey.OP_READ );
        while(true) {
            int readyChannels = selector.select();
            if(readyChannels == 0) continue;
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()) {
                 key = keyIterator.next();
                if(key.isAcceptable()) {
                    System.out.println("isAcceptable");
                    // a connection was accepted by a ServerSocketChannel.
                } else if (key.isConnectable()) {
                    System.out.println("is acceptable");
                    // a connection was established with a remote server.
                } else if (key.isReadable()) {
                    System.out.println("is Readable");
                    Thread.sleep(1000);
                    // a channel is ready for reading
                } else if (key.isWritable()) {
                    System.out.println("isWritable");
                    // a channel is ready for writing
                }
                System.out.println(selectedKeys.size() + " size");
                keyIterator.remove();
            }
        }
    }
}
