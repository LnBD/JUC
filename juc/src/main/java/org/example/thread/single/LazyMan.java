package org.example.thread.single;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class LazyMan {
    private static boolean flag = false;

    private LazyMan(){
        synchronized (LazyMan.class){
            if (flag==false){
                flag=true;
            } else{
                throw new RuntimeException("不要试图使用反射破坏单例模式");
            }
        }
    }

    private volatile static LazyMan lazyMan;

    public static LazyMan getLazyMan(){
        //双重检测锁模式的懒汉式单例（DCL懒汉式）
        if(lazyMan==null){
            synchronized (LazyMan.class){
                if(lazyMan==null){
                    lazyMan = new LazyMan();
                }
            }
        }
        return lazyMan;
    }

    public static void main(String[] args) throws Exception {
        /*for (int i = 0; i < 10; i++) {
            new Thread(()->{
                LazyMan.getLazyMan();
            }).start();
        }*/

        //LazyMan lazyMan2 = LazyMan.getLazyMan();
        Field flag = LazyMan.class.getDeclaredField("flag");
        flag.setAccessible(true);
        Constructor<LazyMan> constructor = LazyMan.class.getDeclaredConstructor(null);
        constructor.setAccessible(true);
        LazyMan lazyMan1 = constructor.newInstance();
        flag.set(lazyMan1,false);
        LazyMan lazyMan2 = constructor.newInstance();
        System.out.println(lazyMan1);
        System.out.println(lazyMan2);

    }
}
