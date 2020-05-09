package org.example.thread.single;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public enum EnumSingle {

    INSTANCE;

    public EnumSingle getInstance(){
        return INSTANCE;
    }

    public static void main(String[] args) throws Exception {
        EnumSingle instance1 = EnumSingle.INSTANCE;

        Constructor<EnumSingle> constructor = EnumSingle.class.getDeclaredConstructor(String.class,int.class);
        constructor.setAccessible(true);
        EnumSingle instance2 = constructor.newInstance();

        /*EnumSingle instance2 = EnumSingle.INSTANCE;*/
        System.out.println(instance1);
        System.out.println(instance2);
    }
}
