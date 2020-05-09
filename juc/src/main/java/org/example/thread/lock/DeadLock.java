package org.example.thread.lock;

import java.util.concurrent.TimeUnit;
/**
 * 如何进行死锁排查：
 *     1.使用 jps -l 定位进程号
 *     2.使用 jstack 进程号 查看堆栈信息，找到死锁问题。查看日志也行
 *     Java stack information for the threads listed above:
 * ===================================================
 * 堆栈信息：
 *      "T2":
 *              at org.example.thread.lock.MyThread.run(DeadLock.java:37)
 *              - waiting to lock <0x000000076b9a4348> (a java.lang.String)
 *              - locked <0x000000076b9a4380> (a java.lang.String)
 *              at java.lang.Thread.run(Thread.java:748)
 *      "T1":
 *              at org.example.thread.lock.MyThread.run(DeadLock.java:37)
 *              - waiting to lock <0x000000076b9a4380> (a java.lang.String)
 *              - locked <0x000000076b9a4348> (a java.lang.String)
 *              at java.lang.Thread.run(Thread.java:748)
 *
 *      Found 1 deadlock.
 * **/
public class DeadLock {
    public static void main(String[] args) {
        String lockA="lockA";
        String lockB="lockB";
        new Thread(new MyThread(lockA,lockB),"T1").start();
        new Thread(new MyThread(lockB,lockA),"T2").start();
    }
}

class MyThread implements Runnable{
    private String lockA;
    private String lockB;

    public MyThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"=>lock:"+lockA+"  get:"+lockB);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName()+"=>lock:"+lockB+"  get:"+lockA);
            }
        }
    }
}