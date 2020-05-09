package org.example.thread;

import java.util.concurrent.CountDownLatch;

//减法计数器
public class CountDownLatchDemo {
    //所有学生出教室后，管理员关门
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch=new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"出教室");
                countDownLatch.countDown();//减少锁存器的计数，如果计数达到零，释放所有等待的线程。
            },String.valueOf(i)).start();
        }
        //每次有线程调用 countDown() 数量-1，假设计数器变为0，countDownLatch.await() 就会被唤醒，继续执行！
        countDownLatch.await();// 等待计数器归零，然后再向下执行
        System.out.println("管理员关门");
    }
}
