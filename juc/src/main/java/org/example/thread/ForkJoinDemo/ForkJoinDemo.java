package org.example.thread.ForkJoinDemo;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**分而治之
 * 并行执行任务！提高效率。大数据量！**/
public class ForkJoinDemo extends RecursiveTask<Long> {

    // 如何使用 forkjoin
    // 1、forkjoinPool 通过它来执行
    // 2、计算任务 forkjoinPool.execute(ForkJoinTask task)
    // 3. 计算类要继承RecursiveTask(ForkJoinTask)

    private Long start;
    private Long end;

    // 临界值
    private Long temp = 10000L;

    public ForkJoinDemo(Long start,Long end){
        this.start=start;
        this.end=end;
    }

    //forkjoin执行任务
    @Override
    protected Long compute() {
        Long sum=0L;
        //递归出口
        if ((end-start)<temp){
            for (Long i=start;i<=end;i++){
                sum+=i;
            }
            return sum;
        }else {
            //分治法，一分为二，递归算法
            Long middle=(start+end)/2;
            ForkJoinDemo task1 = new ForkJoinDemo(start, middle);
            task1.fork();// 拆分任务，把任务压入线程队列
            ForkJoinDemo task2 = new ForkJoinDemo(middle + 1, end);
            task2.fork();
            return task1.join()+task2.join();//将若干小问题的解合并起来成为最终的解
        }
    }
}
