package org.example.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 同步队列
 * 和其他的BlockingQueue 不一样， SynchronousQueue 不存储元素
 * put了一个元素，必须从里面先take取出来，否则不能在put进去值！
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> queue=new SynchronousQueue<>();

        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+": put 1");
                queue.put("1");
                System.out.println(Thread.currentThread().getName()+": put 2");
                queue.put("2");
                System.out.println(Thread.currentThread().getName()+": put 3");
                queue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"T1").start();

        new Thread(()->{
            try {
                //延时两秒便于观察同步队列存取数据特征
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName()+"=>"+queue.take());
                System.out.println("=====================");
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName()+"=>"+queue.take());
                System.out.println("=====================");
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName()+"=>"+queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"T2").start();

    }
}
