package com.lotus.stage01.demo01;

/**
 * 初始化执行顺序遵循原则：
 * 父类静态代码块 --> 子类静态代码块  ---> 父类非静态代码块 --> 父类构造函数 --> 子类非静态代码块 --> 子类构造函数
 *
 * @author lotus
 * @create 2020-12-25 00:07
 */
public class ExtendsTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        C c = new D();  // 创建派生类对象
    }
}

class C {
    static {
        System.out.println("C 基类静态域 ");   // 基类 类成员 构造函数 = 基类静态代码块   1
    }
    {
        System.out.println("C 基类对象成员构造函数");   // 基类 对象成员 构造函数 = 基类非静态代码块  3
    }

    public C() {
        System.out.println("C 基类本身的构造函数");    // 基类 构造函数      4
    }
}

class D extends C {
    static {
        System.out.println("D 派生类静态域");       // 派生类 类成员  构造函数        2
    }
    {
        System.out.println("D 派生类对象成员构造函数");  // 派生类  对象成员  构造函数   5
    }

    public D() {
        System.out.println("D 派生类本身的构造函数");  // 派生类 构造函数    6
    }
}







