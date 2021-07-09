package com.luo.ibatis.test.ognl;

/**
 * @author ：archer
 * @date ：Created in 2021/7/8 9:48
 * @description：
 */
public class StaticDemo {

    private static int num = (int) (Math.random() * 100);

    public static int showDemo(int a) {
        System.out.println("static show demo: " + a);
        return a;
    }
}
