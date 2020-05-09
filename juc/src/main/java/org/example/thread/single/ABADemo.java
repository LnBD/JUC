package org.example.thread.single;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {
    public static void main(String[] args) {

        //public AtomicStampedReference(V initialRef, int initialStamp)
        // initialRef：初始值    initialStamp：初始时间戳（可理解为版本号）
        //坑： 注意，如果泛型是一个包装类，注意对象的引用问题
        AtomicStampedReference<Integer> reference=new AtomicStampedReference<>(1,1);

        new Thread(()->{
            int stamp = reference.getStamp();
            System.out.println("a1_version=>"+stamp);
            System.out.println("内存中的值"+reference.getReference());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //compareAndSet方法只有在预期值和预期版本号都达到后才能修改成功返回true，否则修改失败返回false。
            System.out.println("a1_cas=>"+reference.compareAndSet(1, 2, reference.getStamp(), reference.getStamp() + 1));
            System.out.println("a2_version=>"+reference.getStamp());
            System.out.println("内存中的值"+reference.getReference());
            System.out.println("a2_cas=>"+reference.compareAndSet(2, 1, reference.getStamp(), reference.getStamp() + 1));
            System.out.println("a3_version=>"+reference.getStamp());
            System.out.println("内存中的值"+reference.getReference());
        },"a").start();

        new Thread(()->{
            int stamp=reference.getStamp();
            System.out.println("b1_version=>"+stamp);
            System.out.println("内存中的值"+reference.getReference());
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("b1_cas=>"+reference.compareAndSet(1, 6, stamp, stamp + 1));
            System.out.println("b2_version=>"+reference.getStamp());
            System.out.println("内存中的值"+reference.getReference());
        },"b").start();

    }
}
