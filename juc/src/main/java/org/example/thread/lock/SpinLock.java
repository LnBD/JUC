package org.example.thread.lock;

import java.util.concurrent.atomic.AtomicReference;

//自定义自旋锁，实现加锁解锁
public class SpinLock {

    AtomicReference<Thread> reference=new AtomicReference<>();

    public void myLock(){
        Thread thread = Thread.currentThread();
        //System.out.println(Thread.currentThread().getName()+"=>myLock");
        //自旋锁
        while(!reference.compareAndSet(null,thread)){

        }
        System.out.println(Thread.currentThread().getName()+"已经加上锁，自旋中，其他线程不可拿到锁");
    }

    public void myUnLock(){
        Thread thread = Thread.currentThread();
        reference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+"已经释放锁，其他线程可以重新获取锁");
    }

}
