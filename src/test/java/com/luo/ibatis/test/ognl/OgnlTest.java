package com.luo.ibatis.test.ognl;

import ognl.*;
import org.junit.Test;

/**
 * @author ：archer
 * @date ：Created in 2021/7/8 9:47
 * @description：
 */
public class OgnlTest {

    public OgnlContext createOgnlContext() {
        ADemo a = new ADemo();
        a.setName("yihui");
        a.setAge(10);

        PrintDemo print = new PrintDemo();
        print.setPrefix("ognl");
        print.setADemo(a);

        // 构建一个OgnlContext对象
        // 扩展，支持传入class类型的参数
        //我们这里可以直接访问私有成员，访问私有方法，访问父类的私有成员，这些都得益于我们自定义的DefaultMemberAccess，并制定了访问策略为true（即私有、保护、默认访问权限的都可以访问）
        OgnlContext context = (OgnlContext) Ognl.createDefaultContext(this,
                new DefaultMemberAccess(true), new DefaultClassResolver(), new DefaultTypeConverter());
        context.setRoot(print);
        context.put("print", print);
        context.put("a", a);
        return context;
    }

    @Test
    public void invokePringtDemoSayHello() {
        OgnlContext context = createOgnlContext();
        Object ans = null;
        try {
            ans = Ognl.getValue(Ognl.parseExpression("#print.sayHello(\"一灰灰blog\", 18)"), context, context.getRoot());
        } catch (OgnlException e) {
            e.printStackTrace();
        }
        System.out.println("实例方法执行： " + ans);
    }

    @Test
    public void shouldPrintADemoField() {
        try {
            OgnlContext context = createOgnlContext();
            Object ans = Ognl.getValue(Ognl.parseExpression("#a.name=\"一灰灰Blog\""), context, context.getRoot());
            System.out.println("实例属性设置： " + ans);

            ans = Ognl.getValue(Ognl.parseExpression("#a.name"), context, context.getRoot());
            System.out.println("实例属性访问： " + ans);
        } catch (OgnlException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldPrintParentField() {
        try {
            OgnlContext context = createOgnlContext();
            // 注册到ognlContext
            BDemo b = new BDemo();
            b.setName("b name");
            b.setAge(20);
            b.setAddress("测试ing");
            context.put("b", b);


            // 测试case
            Object ans = Ognl.getValue(Ognl.parseExpression("#b.name"), context, context.getRoot());
            System.out.println("实例父类属性访问：" + ans);
        } catch (OgnlException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldInvokeStaticMethod() {
        try {
            OgnlContext context = createOgnlContext();
            Object ans = Ognl.getValue(Ognl.parseExpression("@com.luo.ibatis.test.ognl.StaticDemo@showDemo(20)"), context,
                    context.getRoot());
            System.out.println("静态类方法执行：" + ans);
        } catch (OgnlException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldPrintStaticClassField() {
        try {
            OgnlContext context = createOgnlContext();
            Object ans = Ognl.getValue(Ognl.parseExpression("@com.luo.ibatis.test.ognl.StaticDemo@num"), context,
                    context.getRoot());
            System.out.println("静态类成员访问：" + ans);

            // todo 问题：静态成员默认场景下不能修改，那么有办法让它支持修改么
            ans = Ognl.getValue(Ognl.parseExpression("@com.luo.ibatis.test.ognl.StaticDemo@num=1314"), context,
                    context.getRoot());
            System.out.println("静态类成员设置：" + ans);
        } catch (OgnlException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void shouldTransferComplexFieldSuccess() {
        try {
            OgnlContext context = createOgnlContext();
            Object ans = Ognl.getValue(Ognl.parseExpression(
                    "#print.print('{\"name\":\"xx\", \"age\": 20}', @com.luo.ibatis.test.ognl.ADemo@class)"), context,
                    context.getRoot());
            System.out.println("class 参数方法执行：" + ans);

            // class传参
            ans = Ognl.getValue(Ognl.parseExpression("#print.print('{\"name\":\"xx\", \"age\": 20}', #a.getClass())"),
                    context, context.getRoot());
            System.out.println("class 参数方法执行：" + ans);

            // 枚举参数
            ans = Ognl.getValue(
                    Ognl.parseExpression("#print.print(\"print enum\", @com.luo.ibatis.test.ognl.OgnlEnum@CONSOLE)"),
                    context, context.getRoot());
            System.out.println("枚举参数方法执行：" + ans);

            //传null
            // todo 问题：在PrintDemo中的print方法，有多个重载的case，那么两个参数都传null，具体是哪个方法会被执行呢？有啥潜规则么，然而我并没有找到
            ans = Ognl.getValue(Ognl.parseExpression("#print.print(null)"), context, context.getRoot());
            System.out.println("null 传参：" + ans);

            //传递对象
            Object ex = Ognl.parseExpression("#print.print(\"对象构建\", new com.luo.ibatis.test.ognl.ADemo(\"test\", 20))");
            ans = Ognl.getValue(ex, context, context.getRoot());
            System.out.println("对象传参：" + ans);

            // 传递对象并设置参数值
            // todo 问题：我们创建的这个属性会丢到OgnlContext上下文中，所以这种操作非常有可能导致我们自己创建的临时对象覆盖了原有的对象
            //那么有什么方法可以避免么？
            ex = Ognl.parseExpression("#print.print(\"对象构建\", (#demo=new com.luo.ibatis.test.ognl.ADemo(), #demo.setName(\"一灰灰\"), #demo))");
            ans = Ognl.getValue(ex, context, context.getRoot());
            System.out.println("对象传参：" + ans);
        } catch (OgnlException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldTransferContainerFieldSuccess() {
        OgnlContext context = createOgnlContext();
        try {
            Object ex = Ognl.parseExpression("#print.print({1, 3, 5})");
            Object ans = Ognl.getValue(ex, context, context.getRoot());
            System.out.println("List传参：" + ans);

            ex = Ognl.parseExpression("#print.print(#{\"A\": 1, \"b\": 3, \"c\": 5})");
            ans = Ognl.getValue(ex, context, context.getRoot());
            System.out.println("Map传参：" + ans);
        } catch (OgnlException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void shouldTransferExpressionSuccess() {
        OgnlContext context = createOgnlContext();
        try {
            Object ans = Ognl.getValue(Ognl.parseExpression("1 + 3 + 4"), context, context.getRoot());
            System.out.println("表达式执行: " + ans);

            // 阶乘
            ans = Ognl.getValue(Ognl.parseExpression("#fact = :[#this<=1? 1 : #this*#fact(#this-1)], #fact(3)"), context, context.getRoot());
            System.out.println("lambda执行: " + ans);
        } catch (OgnlException e) {
            e.printStackTrace();
        }


    }
}
