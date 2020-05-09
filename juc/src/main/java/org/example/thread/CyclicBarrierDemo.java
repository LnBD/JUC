package org.example.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

//加法计数器
public class CyclicBarrierDemo {
    //收集七颗龙珠召唤神龙
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier=new CyclicBarrier(7,()->{
            System.out.println("集齐龙珠召唤神龙");
        });

        for (int i = 1; i <= 7; i++) {
            //匿名内部类要求调用的外部成员必须是最终的finall.否则访问不到。对于Lambda表达式也一样
            final int temp=i;
            new Thread(()->{
                System.out.println("收集到龙珠"+temp);
                try {
                    //等待（+1）。直至数量到7，执行召唤龙珠的线程
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
