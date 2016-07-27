package me.zzw.app.nio.chat;

import java.util.Random;
import java.util.Scanner;  
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
import java.util.concurrent.TimeUnit;  
  
public class VManager implements Runnable {  
  
    private static String serverHost = "127.0.0.1";  
    private static int port = 3333;  
    private static int num = 3;// 初始化虚拟客户端数目  
    private static int min = 5;// 最小虚拟客户端数目  
    private static int max = 15;// 最大虚拟客户端数目  
    private static Random random=new Random();  
  
    @SuppressWarnings("resource")  
    public static void main(String[] args) {  
  
    if (args.length > 0) {  
  
        if (args[0].indexOf(':') == -1) {  
        System.err.println("目标地址格式不正确");  
        return;  
        }  
  
        serverHost = args[0].split(":")[0];  
        try {  
        port = Integer.parseInt(args[0].split(":")[1]);  
        } catch (NumberFormatException e) {  
        System.err.println("端口号只能为数字");  
        return;  
        }  
    }  
  
    if (args.length > 1) {  
        try {  
        num = Integer.parseInt(args[1]);  
        } catch (NumberFormatException e) {  
        System.err.println("初始化数目只能为数字");  
        return;  
        }  
    }  
  
    if (args.length > 2) {  
        try {  
        min = Integer.parseInt(args[2]);  
        } catch (NumberFormatException e) {  
        System.err.println("最小数目只能为数字");  
        return;  
        }  
    }  
  
    if (args.length > 3) {  
        try {  
        max = Integer.parseInt(args[3]);  
        } catch (NumberFormatException e) {  
        System.err.println("最大数目只能为数字");  
        return;  
        }  
    }  
  
    if (max < num) {  
        System.err.println("初始化数量不能大于最大数量");  
        return;  
    }  
      
    if (max < min) {  
        System.err.println("最小数量不能大于最大数量");  
        return;  
    }  
      
    Thread manager = new Thread(new VManager());  
    manager.start();  
  
    Scanner sc = new Scanner(System.in);  
  
    String arg = null;// 指令  
    while (sc.hasNextLine()) {  
        arg = sc.nextLine();// 输入指令  
  
        if ("shutdown".equals(arg)) {  
        manager.interrupt();  
        break;  
        } else {  
        System.out.println("未知的指令");  
        }  
    }  
    }  
  
    public void run() {  
    ExecutorService manager = Executors.newFixedThreadPool(max);// 线程池管理器  
  
    // 初始化几个客户端  
    for (int i = 0; i < num; i++) {  
        manager.execute(new VClient(serverHost, port));  
    }  
  
    try {  
        int interval=(int)(2*48000.0/min)+1;//用户上线间隔时间范围  
        while (!Thread.interrupted()) {  
        TimeUnit.MILLISECONDS.sleep(random.nextInt(interval));// 用户大概48秒会下线，尽量保持最少用户个数  
        manager.execute(new VClient(serverHost, port));  
        }  
    } catch (InterruptedException e) {  
    } finally {  
        System.out.println("正在停止所有客户端...");  
        manager.shutdownNow();// 中断所有客户端  
    }  
    }  
}  