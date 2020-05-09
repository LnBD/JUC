package org.example.thread;

import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 四大函数式接口：Function,Predicate,Consumer,Supplier
 * */

public class FunctionDemo {

    @Test
    //Function 函数型接口, 有一个输入参数，有一个输出
    public void testFunction(){
        /*Function<String,String> function=new Function<String, String>() {
            @Override
            public String apply(String s) {
                return "函数型接口";
            }
        };*/

        Function<String,String> function=(str)->{
            return str;
        };
        System.out.println(function.apply("function"));
    }

    //Predicate 断定型接口：有一个输入参数，返回值只能是 布尔值！
    @Test
    public void testPredicate(){
        /*Predicate<String> predicate=new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.isEmpty();
            }
        };*/

        Predicate<String> predicate=(s)->{
            return s.isEmpty();
        };
        System.out.println(predicate.test("123"));
    }

    //Consumer 消费型接口: 只有输入，没有返回值
    @Test
    public void testConsumer(){
        /*Consumer<String> consumer=new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("Consumer()"+s);
            }
        };*/

        Consumer<String> consumer=(str)->{
            System.out.println("Consumer()"+str);
        };
        consumer.accept("执行了");
    }

    //Supplier 供给型接口: 没有参数，只有返回值
    @Test
    public void testSupplier(){
        /*Supplier<Integer> supplier=new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 1024;
            }
        };*/

        Supplier<Integer> supplier=()->{
            return 2048;
        };
        System.out.println(supplier.get());
    }
}
