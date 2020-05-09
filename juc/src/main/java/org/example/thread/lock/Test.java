package org.example.thread.lock;

import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        //自定义的自旋锁，底层使用CAS
        SpinLock lock=new SpinLock();

        new Thread(()->{
            lock.myLock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.myUnLock();
            }
        },"A").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{
            lock.myLock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.myUnLock();
            }
        },"B").start();
    }
}
