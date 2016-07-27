package me.zzw.app.nio.chat;

import java.nio.channels.Selector;

/**
 * Created by infosea on 2016-07-27.
 */
public class ServerService implements Runnable {
    Selector clientManager;

    public ServerService(Selector clientManager) {
        this.clientManager = clientManager;
    }

    @Override
    public void run() {
        System.out.println("xxx ");
    }
}
