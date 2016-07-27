package me.zzw.app.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;  
import java.net.SocketAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.CancelledKeyException;  
import java.nio.channels.ClosedChannelException;  
import java.nio.channels.ClosedSelectorException;  
import java.nio.channels.SelectionKey;  
import java.nio.channels.Selector;  
import java.nio.channels.ServerSocketChannel;  
import java.nio.channels.SocketChannel;  
import java.util.Iterator;  
import java.util.LinkedList;  
import java.util.List;  
import java.util.Set;  
  
public class Server {  
  
    private static List<SocketChannel> clientList = new LinkedList<SocketChannel>();// 客户端列表  
    private static Selector clientManager = null;// 通道管理器  
    private static ServerSocketChannel server = null;// 服务器通道  
    private static ByteBuffer buff = ByteBuffer.allocate(1500);// 缓冲器  
    private static int port = 3333;  
  
    public static void main(String[] args) {  
    if (args.length > 0) {  
        try {  
        port = Integer.parseInt(args[0]);  
        } catch (NumberFormatException e) {  
        System.out.println("端口号只能为数字");  
        return;  
        }  
    }  
  
    try {  
        // 初始化失败直接退出  
        if (!init())  
        return;  
  
        while (clientManager.isOpen()) {  
        select();  
  
        // 获取就绪的key列表  
        Set<SelectionKey> keys = clientManager.selectedKeys();  
  
        // 遍历事件并处理  
        Iterator<SelectionKey> it = keys.iterator();  
        while (it.hasNext()) {  
            SelectionKey key = it.next();  
            // 判断key是否有效  
            if (!key.isValid()) {  
            it.remove();// 要移除  
            continue;  
            }  
  
            if (key.isAcceptable()) {// 有请求  
            accept(key);  
            } else if (key.isReadable()) {// 有数据  
            broadcast(key);  
            }  
  
            it.remove();  
        }  
        }  
    } catch (ClosedSelectorException | CancelledKeyException e) {// 一定是其他线程关闭了管理器  
    } finally {  
        try {  
        if (clientManager != null)  
            clientManager.close();  
        } catch (IOException e) {  
        }  
  
        try {  
        if (server != null)  
            server.close();  
        } catch (IOException e) {  
        }  
  
        closeAll();  
        System.out.println("服务器已停止");  
    }  
    }  
  
    // 初始化  
  
    private static boolean init() {  
    System.out.println("服务器启动中...");  
  
    try {  
        // 获取管理器  
        clientManager = Selector.open();  
    } catch (IOException e) {  
        System.out.println("服务器启动失败，原因：通道管理器无法获取");  
        return false;  
    }  
  
    try {  
        // 打开通道  
        server = ServerSocketChannel.open();  
    } catch (IOException e) {  
        System.out.println("服务器启动失败，原因：socket通道无法打开");  
        return false;  
    }  
  
    try {  
        // 绑定端口  
        server.socket().bind(new InetSocketAddress(port));  
    } catch (IOException e) {  
        System.out.println("服务器启动失败，原因：端口号不可用");  
        return false;  
    }  
  
    try {  
        // 设置成非阻塞模式  
        server.configureBlocking(false);  
    } catch (IOException e) {  
        System.out.println("服务器启动失败，原因：非阻塞模式切换失败");  
        return false;  
    }  
  
    try {  
        // 注册到管理器中,只监听接受连接事件  
        server.register(clientManager, SelectionKey.OP_ACCEPT);  
    } catch (ClosedChannelException e) {  
        System.out.println("服务器启动失败，原因：服务器通道已关闭");  
        return false;  
    }  
  
    Thread service = new Thread(new ServerService(clientManager));// 提供管理员指令服务线程  
    service.setDaemon(true);// 设置为后台线程  
    service.start();  
  
    System.out.println("服务器启动成功");  
    return true;  
    }  
  
    // 等待事件  
    private static void select() {  
    try {  
        // 等待事件  
        clientManager.select();  
    } catch (IOException e) {  
        // 忽略未知的异常  
    }  
    }  
  
    // 此方法获取请求的socket通道并添加到客户端列表中,当然还要注册到管理器中  
    private static void accept(SelectionKey key) {  
    SocketChannel socket = null;  
  
    try {  
        // 接受请求的连接  
        socket = ((ServerSocketChannel) key.channel()).accept();  
    } catch (IOException e) {// 连接失败  
    }  
  
    if (socket == null)  
        return;  
  
    SocketAddress address = null;  
  
    try {  
        address = socket.getRemoteAddress();  
        // 注册  
        socket.configureBlocking(false);  
        socket.register(clientManager, SelectionKey.OP_READ);  
    } catch (ClosedChannelException e) {// 注册失败  
        try {  
        if (socket != null)  
            socket.close();  
        } catch (IOException e1) {  
        }  
        return;  
    } catch (IOException e) {  
        try {  
        if (socket != null)  
            socket.close();  
        } catch (IOException e1) {  
        }  
        return;  
    }  
    // 添加到客户端列表中  
    clientList.add(socket);  
    System.out.println("主机" + address + "连接到服务器");  
    }  
  
    // 此方法接收数据并发送个客户端列表的每一个人  
    private static void broadcast(SelectionKey key) {  
    SocketChannel sender = (SocketChannel) key.channel();  
    // 方法结束不清理  
    buff.clear();  
  
    int status = -1;  
    try {  
        // 读取数据  
        status = sender.read(buff);  
    } catch (IOException e) {// 未知的io异常  
        status = -1;  
    }  
  
    if (status <= 0) {// 异常断开连接，并移除此客户端  
        remove(sender);  
        return;  
    }  
  
    // 发送给每一个人  
    for (SocketChannel client : clientList) {  
        // 除了他或她自己  
        if (client == sender)  
        continue;  
        buff.flip();  
        try {  
        client.write(buff);  
        } catch (IOException e) {// 发送失败，移除此客户端  
        remove(client);  
        }  
    }  
    }  
  
    private static void remove(SocketChannel client) {  
    SocketAddress address = null;// 存储主机地址信息  
  
    clientList.remove(client);// 从列表中移除  
      
    try {  
        address = client.getRemoteAddress();//获取客户端地址信息  
    } catch (IOException e1) {  
    }  
      
    try {  
        client.close();// 关闭连接  
    } catch (IOException e1) {  
    }  
    client.keyFor(clientManager).cancel();// 反注册  
    System.out.println("与主机" + address + "断开连接");  
    }  
  
    // 关闭列表中全部通道  
    private static void closeAll() {  
    for (SocketChannel client : clientList) {  
        try {  
        if (client != null)  
            client.close();  
        } catch (IOException e) {  
        }  
    }  
    }  
}  