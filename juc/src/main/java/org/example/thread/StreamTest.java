package org.example.thread;

import java.util.Arrays;
import java.util.List;

//Stream流式计算
public class StreamTest {
    /**
     * 题目要求：一分钟内完成此题，只能用一行代码实现！
     * 现在有5个用户！筛选：
     * 1、ID 必须是偶数
     * 2、年龄必须大于23岁
     * 3、用户名转为大写字母
     * 4、用户名字母倒着排序
     * 5、只输出一个用户！
     */
    public static void main(String[] args) {
        User u1 = new User(1,"a",21);
        User u2 = new User(2,"b",22);
        User u3 = new User(3,"c",23);
        User u4 = new User(4,"d",24);
        User u5 = new User(6,"e",25);

        // 集合用于存储
        List<User> list = Arrays.asList(u1, u2, u3, u4, u5);
        // 计算交给Stream流
        list.stream()
                .filter(user->{
                    return user.getId()%2==0;
                })
                .filter(user -> {
                    return user.getAge()>23;
                })
                .map(user->{
                    return user.getName().toUpperCase();
                })
                .sorted((uu1,uu2)->{
                    return uu2.compareTo(uu1);
                })
                .limit(1)
                .forEach(System.out::println);


    }
}

class User{
    Integer id;
    String name;
    Integer age;

    public User(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}