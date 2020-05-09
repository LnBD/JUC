package org.example.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // new Thread(new Runnable()).start();
        // new Thread(new FutureTask<V>()).start();
        // new Thread(new FutureTask<V>( Callable )).start();
        MyThread thread=new MyThread();
        //适配类，Thread的构造方法没有直接传Callable的，要通过这个适配类来传值
        FutureTask futureTask=new FutureTask(thread);
        new Thread(futureTask,"A").start();
        new Thread(futureTask,"B").start();// 结果会被缓存，效率高。故“callable执行了"只会打印一次
        //获取Callable的返回值，get方法会产生阻塞，所以放在最后执行
        Integer o = (Integer)futureTask.get();
        System.out.println(o);
    }
}

class MyThread implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("callable执行了");
        return 1024;
    }
}
