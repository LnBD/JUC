package org.example.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//lock版本的可重入锁
public class Demo2 {
    public static void main(String[] args) {
        Phone2 phone2=new Phone2();
        new Thread(() -> {
            phone2.sms();
        }, "T1").start();

        new Thread(() -> {
            phone2.sms();
        }, "T2").start();
    }

}
class Phone2{
    Lock lock=new ReentrantLock();
    public void sms(){
        lock.lock();
        lock.lock();// 细节问题：lock.lock(); lock.unlock(); // lock 锁必须配对，否 则就会死在里面
        try {
            System.out.println(Thread.currentThread().getName()+"=>sms");
            call();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }

    public void call(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"=>call");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
