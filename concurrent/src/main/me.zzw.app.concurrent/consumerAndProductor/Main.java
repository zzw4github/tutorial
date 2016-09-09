package main.me.zzw.app.concurrent.consumerAndProductor;

import java.util.Stack;

/**
 * Created by infosea on 2016-09-09.
 */
class Consumer extends Thread{
    Base base;

    public Consumer(Base base) {
        this.base = base;
    }

    public  void consume(){
        base.pop();

    }

    @Override
    public void run() {
            while (true) {
                    consume();
            }
    }
}

class Producer extends Thread{
    public static int item = 0;
    Base base;

    public Producer(Base base) {
        this.base = base;
    }

    public  void product(){
        while(true) {
            base.push(new Item("item" + ++item));
        }
    }

    @Override
    public void run() {
          product();
        }
}

class Item {
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

class Base {
    private Stack<Item> items;

    public Base(Stack<Item> items) {
        this.items = items;
    }

    public Stack<Item> getItems() {
        return items;
    }

    public void pop() {
        synchronized (items) {
            if (items.size() !=0) {
                Item item = items.pop();
                System.out.println("consume item : " + item.getName());
                items.notifyAll();

            } else {
                try {
                    System.out.println(items.size());
                    items.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public  void push(Item item){
        synchronized (items) {
            if(items.size() ==0) {
                items.push(item);
                System.out.println("product item : item" + item);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                items.notifyAll();
            }else{
                try {
                    System.out.println(items.size());
                    items.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

public class Main {
    public static void main(String[] args){

    Base base = new Base(new Stack<Item>());
    Consumer consumer1 = new Consumer(base);
    Producer producer1 = new Producer(base);
    consumer1.start();
    producer1.start();
     Consumer consumer2 = new Consumer(base);
     Producer producer2 = new Producer(base);
        consumer2.start();
        producer2.start();
    }

}
