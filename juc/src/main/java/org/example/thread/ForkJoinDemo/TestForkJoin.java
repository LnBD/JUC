package org.example.thread.ForkJoinDemo;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class TestForkJoin {

    //普通累加方法  5326ms
    @Test
    public void test1(){
        Long sum=0L;
        long start = System.currentTimeMillis();
        for (Long i=1L;i<=10_0000_0000;i++){
            sum+=i;
        }
        long end =System.currentTimeMillis();
        System.out.println("sum="+sum+",时间："+(end-start));
    }

    //使用forkjoin分治法思想  3777ms
    //  如何使用 forkjoin
    //      1、通过forkjoinPool来执行
    //      2、计算任务：forkjoinPool.execute(ForkJoinTask task) || forkjoinPool.submit(ForkJoinTask task)
    //      3. 计算类要继承ForkJoinTask
    @Test
    public void test2(){
        long start = System.currentTimeMillis();
        ForkJoinPool pool=new ForkJoinPool();
        ForkJoinDemo forkJoin=new ForkJoinDemo(1L,10_0000_0000l);
        ForkJoinTask<Long> submit = pool.submit(forkJoin);//提交任务
        Long sum = null;
        try {
            sum = submit.get();//取得执行，提交的结果
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        long end =System.currentTimeMillis();
        System.out.println("sum="+sum+",时间："+(end-start));
    }

    //使用stream并行流   170ms
    @Test
    public void test3(){
        long start = System.currentTimeMillis();
        //range：()开区间
        //rangeClosed：(]结尾闭区间，给定计算区间
        //parallel()并行计算
        //reduce 调包装类Long的二进制加法运算
        long sum = LongStream.rangeClosed(1L, 10_0000_0000L).parallel().reduce(0, Long::sum);
        long end =System.currentTimeMillis();
        System.out.println("sum="+sum+",时间："+(end-start));
    }
}
