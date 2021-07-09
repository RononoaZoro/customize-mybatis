package com.luo.ibatis.test.ognl;

import com.luo.ibatis.util.JsonUtil;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author ：archer
 * @date ：Created in 2021/7/8 9:48
 * @description：
 */
@Data
public class PrintDemo {

    private String prefix;

    private ADemo aDemo;

    public void sayHello(String name, int age) {
        System.out.println("name: " + name + " age: " + age);
    }

    private void print(ADemo a) {
        System.out.println(prefix + " => " + a);
    }

    public <T> T print(String str, Class<T> clz) {
        T obj = JsonUtil.fromJson(str, clz);
        System.out.println("class: " + obj);
        return obj;
    }

    public void print(String str, String clz) {
        System.out.println("str2a: " + str + " clz: " + clz);
    }

    public void print(String str, OgnlEnum ognlEnum) {
        System.out.println("enum: " + str + ":" + ognlEnum);
    }

    public void print(String str, ADemo a) {
        System.out.println("obj: " + str + ":" + a);
    }

    public void show(Class clz) {
        System.out.println(clz.getName());
    }

    public void print(List<Integer> args) {
        System.out.println(args);
    }

    public void print(Map<String, Integer> args) {
        System.out.println(args);
    }

}
