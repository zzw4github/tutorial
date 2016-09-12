package me.zzw.app.concurrent.consumerAndProductor;

/**
 * Created by infosea on 2016-09-09.
 */
import java.util.LinkedList;

/**
 * 仓库类Storage实现缓冲区
 *
 * Email:530025983@qq.com
 *
 * @author MONKEY.D.MENG 2011-03-15
 *
 */
 class Storage
{
    // 仓库最大存储量
    private final int MAX_SIZE = 100;

    // 仓库存储的载体
    private LinkedList<Object> list = new LinkedList<Object>();

    // 生产num个产品
    public void produce(int num)
    {
        // 同步代码段
        synchronized (list)
        {
            // 如果仓库剩余容量不足
            while (list.size() + num > MAX_SIZE)
            {
                System.out.println("【要生产的产品数量】:" + num + "/t【库存量】:"
                        + list.size() + "/t暂时不能执行生产任务!");
                try
                {
                    // 由于条件不满足，生产阻塞
                    list.wait();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            // 生产条件满足情况下，生产num个产品
            for (int i = 1; i <= num; ++i)
            {
                list.add(new Object());
            }

            System.out.println("【已经生产产品数】:" + num + "/t【现仓储量为】:" + list.size());

            list.notifyAll();
        }
    }

    // 消费num个产品
    public void consume(int num)
    {
        // 同步代码段
        synchronized (list)
        {
            // 如果仓库存储量不足
            while (list.size() < num)
            {
                System.out.println("【要消费的产品数量】:" + num + "/t【库存量】:"
                        + list.size() + "/t暂时不能执行生产任务!");
                try
                {
                    // 由于条件不满足，消费阻塞
                    list.wait();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            // 消费条件满足情况下，消费num个产品
            for (int i = 1; i <= num; ++i)
            {
                list.remove();
            }

            System.out.println("【已经消费产品数】:" + num + "/t【现仓储量为】:" + list.size());

            list.notifyAll();
        }
    }

    // get/set方法
    public LinkedList<Object> getList()
    {
        return list;
    }

    public void setList(LinkedList<Object> list)
    {
        this.list = list;
    }

    public int getMAX_SIZE()
    {
        return MAX_SIZE;
    }
}
/**
 * 生产者类Producer继承线程类Thread
 *
 * Email:530025983@qq.com
 *
 * @author MONKEY.D.MENG 2011-03-15
 *
 */
 class Producer1 extends Thread
{
    // 每次生产的产品数量
    private int num;

    // 所在放置的仓库
    private Storage storage;

    // 构造函数，设置仓库
    public Producer1(Storage storage)
    {
        this.storage = storage;
    }

    // 线程run函数
    public void run()
    {
        produce(num);
    }

    // 调用仓库Storage的生产函数
    public void produce(int num)
    {
        storage.produce(num);
    }

    // get/set方法
    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

    public Storage getStorage()
    {
        return storage;
    }

    public void setStorage(Storage storage)
    {
        this.storage = storage;
    }
}
/**
 * 消费者类Consumer继承线程类Thread
 *
 * Email:530025983@qq.com
 *
 * @author MONKEY.D.MENG 2011-03-15
 *
 */
 class Consumer1 extends Thread
{
    // 每次消费的产品数量
    private int num;

    // 所在放置的仓库
    private Storage storage;

    // 构造函数，设置仓库
    public Consumer1(Storage storage)
    {
        this.storage = storage;
    }

    // 线程run函数
    public void run()
    {
        consume(num);
    }

    // 调用仓库Storage的生产函数
    public void consume(int num)
    {
        storage.consume(num);
    }

    // get/set方法
    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

    public Storage getStorage()
    {
        return storage;
    }

    public void setStorage(Storage storage)
    {
        this.storage = storage;
    }
}
/**
 * 测试类Test
 *
 * Email:530025983@qq.com
 *
 * @author MONKEY.D.MENG 2011-03-15
 *
 */
public class Test
{
    public static void main(String[] args)
    {
        // 仓库对象
        Storage storage = new Storage();

        // 生产者对象
        Producer1 p1 = new Producer1(storage);
        Producer1 p2 = new Producer1(storage);
        Producer1 p3 = new Producer1(storage);
        Producer1 p4 = new Producer1(storage);
        Producer1 p5 = new Producer1(storage);
        Producer1 p6 = new Producer1(storage);
        Producer1 p7 = new Producer1(storage);

        // 消费者对象
        Consumer1 c1 = new Consumer1(storage);
        Consumer1 c2 = new Consumer1(storage);
        Consumer1 c3 = new Consumer1(storage);

        // 设置生产者产品生产数量
        p1.setNum(10);
        p2.setNum(10);
        p3.setNum(10);
        p4.setNum(10);
        p5.setNum(10);
        p6.setNum(10);
        p7.setNum(80);

        // 设置消费者产品消费数量
        c1.setNum(50);
        c2.setNum(20);
        c3.setNum(30);

        // 线程开始执行
        c1.start();
        c2.start();
        c3.start();
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
        p6.start();
        p7.start();
    }
}
