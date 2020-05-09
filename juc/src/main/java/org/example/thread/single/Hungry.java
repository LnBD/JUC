package org.example.thread.single;

public class Hungry {

    //单例模式的特征：私有的构造方法
    private Hungry(){
    }

    private static final Hungry Hungry = new Hungry();

    public static Hungry getHungry(){
        return Hungry;
    }

}
