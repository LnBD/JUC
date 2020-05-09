package org.example.thread;

import java.util.concurrent.TimeUnit;

public class VolatileDemo1 {
    //不加volatile关键字，线程A就不知道主存中的num已经被修改为1，依旧保持的0的状态下死循环
    //这里加volatile保证了可见性
    private volatile static int num=0;
    public static void main(String[] args) {
        new Thread(()->{
            while(num==0){

            }
        },"A").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        num=1;
        System.out.println(num);
    }
}
