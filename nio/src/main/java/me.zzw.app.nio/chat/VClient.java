package me.zzw.app.nio.chat;

import java.io.IOException;
import java.net.InetAddress;  
import java.net.InetSocketAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.SocketChannel;  
import java.util.Random;  
import java.util.concurrent.TimeUnit;  
  
public class VClient implements Runnable {  
  
    private String ip = null;  
    private String name = null;  
    private String serverHost = "127.0.0.1";// 服务器ip地址  
    private int port = 3333;// 服务器端口号  
    private SocketChannel socket = null;// 与服务器连接通道  
    private static String[] msgs = {  
        "大家好",  
        "好困啊",  
        "今天该干什么啊",  
        "我这任务好多，先不聊了",  
        "jQuery是继prototype之后又一个优秀的Javascript库。它是轻量级的js库 ，它兼容CSS3，还兼容各种浏览器（IE 6.0+, FF 1.5+, Safari 2.0+, Opera 9.0+），jQuery2.0及后续版本将不再支持IE6/7/8浏览器。jQuery使用户能更方便地处理HTML（标准通用标记语言下的一个应用）、events、实现动画效果，并且方便地为网站提供AJAX交互。jQuery还有一个比较大的优势是，它的文档说明很全，而且各种应用也说得很详细，同时还有许多成熟的插件可供选择。jQuery能够使用户的html页面保持代码和html内容分离，也就是说，不用再在html里面插入一堆js来调用命令了，只需要定义id即可[8]。",  
        "谁那有咖啡", "谁有时间帮我去取个快递", ".exit" };  
    private Random random=new Random();  
  
    public VClient(String serverHost, int port) {  
    this.serverHost = serverHost;  
    this.port = port;  
    }  
  
    public void run() {  
    try {  
        // 初始化失败退出程序  
        if (!init())  
        return;  
  
        ByteBuffer buff = ByteBuffer.allocate(1500);// 字节缓冲器  
  
        while (!Thread.interrupted()&&socket.read(buff) != -1) {// 读取信息，中断退出  
        String msg = new String(buff.array(), 0, buff.position());// 转成字符串  
        buff.clear();// 清理  
        System.out.println(msg);  
        }  
  
    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("与服务器断开连接");  
    } finally {  
        close();  
        System.out.println("程序已退出");  
    }  
    }  
  
    // 初始化程序  
    private boolean init() {  
    System.out.println("正在连接至服务器...");  
    try {  
        socket = SocketChannel  
            .open(new InetSocketAddress(serverHost, port));// 打开通道  
    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("无法连接到服务器");  
        return false;  
    }  
    System.out.println("已连接至服务器");  
  
    try {  
        InetAddress address = InetAddress.getLocalHost();// 获取本机网络信息  
        ip = address.getHostAddress();// 本机ip  
        name = address.getHostName();// 主机名  
        socket.write(ByteBuffer.wrap((name + "-" + ip + "上线了").getBytes()));// 发送上线信息  
    } catch (IOException e) {  
        System.out.println("网络异常");  
        return false;  
    }  
  
    Thread thread = new Thread(new Daemon());// 私有内部类  
    thread.setDaemon(true);// 设置后台线程  
    thread.start();  
    return true;  
    }  
  
    // 关闭通道  
    private void close() {  
    try {  
        if (socket != null)  
        socket.close();  
    } catch (IOException e) {  
    }  
    }  
  
    // 私有内部类、守护线程、输出用  
    private class Daemon implements Runnable {  
  
    public void run() {  
        try {  
        // 自动循环发送消息  
        while (true) {  
            String msg = msgs[random.nextInt(msgs.length)];// 随便拿一个写好的信息  
  
            if (".exit".equals(msg)) {  
            socket.write(ByteBuffer.wrap((name + "-" + ip + "下线了")  
                .getBytes()));// 发送下线信息  
            break;  
            }  
  
            msg = name + "-" + ip + ":" + msg;  
            socket.write(ByteBuffer.wrap(msg.getBytes()));  
  
            TimeUnit.SECONDS.sleep(random.nextInt(9) + 2);//模拟用户输入过程，2-10秒，平均6秒发一次  
        }  
        } catch (IOException e) {  
        System.out.println("与服务器断开连接");  
        } catch (InterruptedException e) {  
        System.out.println("与服务器断开连接");  
        } finally {  
        close();  
        }  
    }  
    }  

    public static void main(String... args){
        VClient vClient = new VClient("127.0.0.1", 3333);
        vClient.run();
    }
}  