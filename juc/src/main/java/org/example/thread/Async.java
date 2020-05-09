package org.example.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 异步回调  CompletableFuture   类似于AJAX
 * **/
public class Async {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*// 没有返回值的 runAsync 异步回调
        CompletableFuture<Void> future=CompletableFuture.runAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"=>runAsync");
        });
        System.out.println("111111111");
        try {
            future.get();// 获取阻塞执行结果
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/


        // 有返回值的 supplyAsync 异步回调
        CompletableFuture<Integer> future=CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName()+"=>supplyAsync");
            int i=1/0;
            return 1024;//反正正常操作的信息
        });

        System.out.println(future.whenComplete((t,u)->{
            System.out.println("t=>"+t);//正确返回结果
            System.out.println("u=>"+u);//错误信息
        }).exceptionally((e)->{//exceptionally():异常触发此CompletableFuture的完成特殊功能的给定功能
                    System.out.println(e.getMessage());//打印异常信息
                    return 233;// 可以获取到错误的返回结果
        }).get());//get用于获取正常或失败的返回值
    }
}
