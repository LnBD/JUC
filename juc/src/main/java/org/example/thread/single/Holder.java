package org.example.thread.single;

public class Holder {
    private Holder(){
    }

    public static Holder getInstance(){
        return InnerClass.HOLDER;
    }

    //静态内部类形式的单例模式创建对象
    public static class InnerClass{
        private static final Holder HOLDER=new Holder();
    }
}
