package org.example.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCacheLock lock=new MyCacheLock();
        for (int i = 1; i <= 5; i++) {
            final int temp=i;
            new Thread(()->{
                lock.put(temp+"",temp+"");
            },String.valueOf(i)).start();
        }

        for (int i = 1; i <= 5; i++) {
            final int temp=i;
            new Thread(()->{
                lock.get(temp+"");
            },String.valueOf(i)).start();
        }
    }
}

// 读写锁： 更加细粒度的控制
// 写锁又称为独占锁。读锁又称为共享锁
class MyCacheLock{

    private volatile Map<String,Object> map = new HashMap<>();
    ReadWriteLock lock=new ReentrantReadWriteLock();

    //需要保证写操作是一个原子性操作。不能在一个写操作没有执行完时，被其他线程插队！
    public void put(String key,Object value){
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"存放数据");
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"存放数据OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void get(String key){
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"获取数据");
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName()+"获取数据OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }
}
