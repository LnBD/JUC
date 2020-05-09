package org.example.thread.single;

import java.util.concurrent.atomic.AtomicInteger;

public class Test {
    public static void main(String[] args) {
        AtomicInteger integer=new AtomicInteger();
        System.out.println(integer.getAndIncrement());
        System.out.println(integer);
    }
}
