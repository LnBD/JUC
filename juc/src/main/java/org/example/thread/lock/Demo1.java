package org.example.thread.lock;

//Synchronized版本的可重入锁
public class Demo1 {
    public static void main(String[] args) {
        Phone phone=new Phone();
        new Thread(() -> {
            phone.sms();
        }, "T1").start();

        new Thread(() -> {
            phone.sms();
        }, "T2").start();
    }

}
class Phone{
    public synchronized void sms(){
        System.out.println(Thread.currentThread().getName()+"=>sms");
        call();
    }

    public synchronized void call(){
        System.out.println(Thread.currentThread().getName()+"=>call");
    }
}



