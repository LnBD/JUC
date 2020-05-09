package org.example.thread.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {
    public static void main(String[] args) {
        //现在内存的值2020
        AtomicInteger integer=new AtomicInteger(2020);
        // 期望、更新
        // public final boolean compareAndSet(int expect, int update)
        // 如果我期望的值达到了2020，那么就更新2021,返回true。否则，就不更新, 返回false。CAS是CPU的并发原语！
        System.out.println(integer.compareAndSet(2020, 2021));
        System.out.println(integer.get());
        System.out.println(integer.compareAndSet(2020, 2021));
        System.out.println(integer.get());
        integer.getAndIncrement();
    }
}
