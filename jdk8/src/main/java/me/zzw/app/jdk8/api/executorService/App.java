package me.zzw.app.jdk8.api.executorService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by infosea on 2016-07-27.
 */
class NetworkService implements Runnable {
    private final ServerSocket serverSocket;
    private final ExecutorService pool;

    public NetworkService(int port, int poolSize)
            throws IOException {
        serverSocket = new ServerSocket(port);
        pool = Executors.newFixedThreadPool(poolSize);
    }

    public void run() { // run the service
        try {
            for (; ; ) {
                pool.execute(new Handler(serverSocket.accept()));
            }
        } catch (IOException ex) {
            pool.shutdown();
        }
    }
}

class Handler implements Runnable {
    private final Socket socket;

    Handler(Socket socket) {
        this.socket = socket;
    }
    byte[] bytes = new byte[128];
    public void run() {
        try {
                socket.getInputStream().read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(new String(bytes));
    }

}


public class App {
    /*
       * The following method shuts down an {@code ExecutorService} in two phases,
       * first by calling {@code shutdown} to reject incoming tasks, and then
       * calling {@code shutdownNow}, if necessary, to cancel any lingering tasks:
       *
       */
    void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String... args) throws IOException {
        NetworkService service = new NetworkService( 8088, 5);
        new Thread(service).start();
    }
}
