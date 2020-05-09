package org.example.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//A->B->C->A
public class LockDemo1 {
    public static void main(String[] args) {
        DataASC dataASC=new DataASC();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                dataASC.printA();
            }
        },"A").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                dataASC.printB();
            }
        },"B").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                dataASC.printC();
            }
        },"C").start();
    }
}

class DataASC{
    private Lock lock=new ReentrantLock();
    //通过Lock的newCondition()获取Condition，使用Condition类提供的方法await()，signal()来完成Lock方式的线程通信
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();
    private int flag=1;

    public void printA(){
        lock.lock();
        try {
            while(flag!=1){
                condition1.await();
            }
            System.out.println(Thread.currentThread().getName()+"=>AAAAAAAAAAA");
            flag=2;
            // 唤醒，唤醒指定的人，B
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void printB(){
        lock.lock();
        try {
            while(flag!=2){
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName()+"=>BBBBBBBBBBBB");
            flag=3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void printC(){
        lock.lock();
        try {
            while(flag!=3){
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName()+"=>CCCCCCCCCCCC");
            flag=1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
