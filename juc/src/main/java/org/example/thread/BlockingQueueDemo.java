package org.example.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {
    public static void main(String[] args) {
        //test1();
        //test2();
        //test3();
        test4();
    }

    //抛出异常方式
    public static void test1(){
        ArrayBlockingQueue<String> queue=new ArrayBlockingQueue<String>(3);

        System.out.println(queue.add("a"));
        System.out.println(queue.add("b"));
        System.out.println(queue.add("c"));
        //System.out.println(queue.add("d"));
        System.out.println("=======================");
        System.out.println(queue.element());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        //System.out.println(queue.remove());
    }

    //有返回值，不抛出异常
    public static void test2(){
        ArrayBlockingQueue<String> queue=new ArrayBlockingQueue<String>(3);
        System.out.println(queue.offer("a"));
        System.out.println(queue.offer("b"));
        System.out.println(queue.offer("c"));
        System.out.println(queue.offer("d"));
        System.out.println("==================");
        System.out.println(queue.poll());
        //检测队首元素
        System.out.println(queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }

    //阻塞等待
    public static void test3() {
        ArrayBlockingQueue<String> queue=new ArrayBlockingQueue<>(3);
        try {
            queue.put("a");
            queue.put("b");
            queue.put("c");
            //queue.put("d");
            System.out.println(queue.take());
            System.out.println(queue.take());
            System.out.println(queue.take());
            //System.out.println(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //超时等待,使用的还是不抛出的异常的API，用的是他们的重载方法
    public static void test4(){
        ArrayBlockingQueue<String> queue=new ArrayBlockingQueue<>(3);
        try {
            queue.offer("a");
            queue.offer("b");
            queue.offer("c");
            //queue.offer("d",2, TimeUnit.SECONDS);
            System.out.println(queue.poll());
            System.out.println(queue.poll());
            System.out.println(queue.poll());
            System.out.println(queue.poll(2, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
