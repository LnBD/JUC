package org.example.thread;

import java.util.concurrent.atomic.AtomicInteger;

//volatile不保证原子性
public class VolatileDemo2 {

    //private static int num=0;

    //这些类的底层都直接和操作系统挂钩！在内存中修改值！Unsafe类是一个很特殊的存在！
    // 原子类的 Integer
    private static AtomicInteger num=new AtomicInteger();

    public /*synchronized*/ static void add(){
        /*num++;*/
        num.getAndIncrement();// AtomicInteger：自增+ 1 方法， 底层CAS

    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int i1 = 0; i1 < 1000; i1++) {
                    add();
                }
            }).start();
        }
        while (Thread.activeCount()>2){//剩下的两个线程：main,gc
            Thread.yield();//线程礼让
        }
        System.out.println(Thread.currentThread().getName()+""+num);
    }
}
