package org.example.thread;

import java.util.concurrent.*;

public class ThreadPool {
    public static void main(String[] args) {
        /** 最大线程到底该如何定义
         1、CPU 密集型，几核，就是几，可以保持CPu的效率最高！
         2、IO 密集型 > 判断你程序中十分耗IO的线程，
            程序 15个大型任务 io十分占用资源！*/
        //获取CPU的核数
        System.out.println(Runtime.getRuntime().availableProcessors());
        /** Executors 工具类、3大方法。但是使用Executors创建线程池的方式是不推荐的（不安全，可能会OOM）
        //ExecutorService service= Executors.newSingleThreadExecutor(); //单个线程
        //ExecutorService service = Executors.newFixedThreadPool(5); //创建一个固定大小的线程
        //ExecutorService service = Executors.newCachedThreadPool(); //可伸缩的，越强则强，越弱则弱*/

        //通过原生的ThreadPoolExecutor去自定义线程池（企业常用方式）
        ExecutorService service=new ThreadPoolExecutor(
                2,//核心线程数
                Runtime.getRuntime().availableProcessors(),/*最大线程数。（超过核心线程数+阻塞队列长度（这里为5），开启新的线程，最多开到8个，
                                                            如果超过最大线程数+阻塞队列长度（这里为11），执行拒绝策略）*/
                3,//超过3秒，关闭临时开启的3个线程，只留下两个核心线程
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),//阻塞队列===>候客区
                Executors.defaultThreadFactory(),//不要动
                /**
                 RejectedExecutionHandler：拒绝策略（有四种）   超过最大线程数+阻塞队列长度（这里为11），执行拒绝策略
                 new ThreadPoolExecutor.AbortPolicy() // 银行满了，还有人进来，不处理这个人的，抛出异常
                 new ThreadPoolExecutor.CallerRunsPolicy() // 哪来的去哪里！（main线程执行）
                 new ThreadPoolExecutor.DiscardPolicy() //队列满了，丢掉任务，不会抛出异常！
                 new ThreadPoolExecutor.DiscardOldestPolicy() //队列满了，尝试去和最早的竞争，也不会抛出异常！
                 */
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        try {
            for (int i = 1; i <= 13; i++) {
                // 使用了线程池之后，使用线程池来创建线程
                service.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"=>OK");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 线程池用完，程序结束，关闭线程池
            service.shutdown();
        }
    }
}
