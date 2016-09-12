package me.zzw.app.concurrent;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by infosea on 2016-09-12.
 */
class NetworkService implements Runnable {
    private final ServerSocket serverSocket;
    private final ExecutorService pool;

    public NetworkService(int  port, int poolSize) throws IOException{
        serverSocket = new ServerSocket(port);
        pool = Executors.newFixedThreadPool(poolSize);
    }

    @Override
    public void run() {
        try{
            for(;;){
                pool.execute(new Handler(serverSocket.accept()));
            }
        }catch (IOException ex){
            pool.shutdown();;
        }
    }
}
class Handler implements Runnable {
    private final Socket socket;
    private final BufferedReader in;
    private String line = "";
    public Handler(Socket socket) throws IOException {
        this.socket = socket;
       this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }

    @Override
    public void run()  {
        try {
            while ((line = in.readLine()) != null)
                System.out.println(line);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
public class TExecutorService {
    public static void main(String[] args){
        try {
            NetworkService service = new NetworkService(9999, 20);
            new Thread(service).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
