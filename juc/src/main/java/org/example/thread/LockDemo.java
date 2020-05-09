package org.example.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
    public static void main(String[] args) {
        DataLock dataLock=new DataLock();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                dataLock.increment();
            }
        },"A").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                dataLock.decrement();
            }
        },"B").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                dataLock.increment();
            }
        },"C").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                dataLock.decrement();
            }
        },"D").start();
    }
}

class DataLock{
    private int num=0;
    private Lock lock=new ReentrantLock();
    Condition condition=lock.newCondition();

    public void increment(){
        lock.lock();
        try {
            //防止虚假唤醒
            while(num!=0){
                condition.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName()+"=>"+num);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement(){
        lock.lock();
        try {
            while(num==0){
                condition.await();
            }
            num--;
            System.out.println(Thread.currentThread().getName()+"=>"+num);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
