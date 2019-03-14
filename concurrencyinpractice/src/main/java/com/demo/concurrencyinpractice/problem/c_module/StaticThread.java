package com.demo.concurrencyinpractice.problem.c_module;

/**
 * 静态初始化块里启动新线程的陷阱
 * @link https://blog.csdn.net/hcjsjqjssm/article/details/78476798
 *
 *  我们知道,main线程开始执行对类的初始化的时候,初始化的主要的步骤就是两个
 *        (1):为这个类的所有的静态的变量分配内存空间.
 *        (2):调用静态初始化块代码执行初始化.
 *
 *        注意这两者之间的顺序哟.
 *        当某一个线程访问一个静态变量的时候,这个类的状态会分大概四种情况的:
 *       (1):该类尚未被初始化,当前线程开始对其进行初始化.
 *       (2):该类正在执行初始化,当前线程就会 递归的执行初始化.
 *       (3):这个类正在被其他的线程执行初始化,当前的线程就会暂停,等待其他的线程初始化完成.
 *       (4):这个类已经初始化完成啦,就会直接得到这个静态变量的值 
 *
 * 类初始化执行顺序
 * 1. 父类的静态成员变量
 * 2. 父类的静态代码块
 * 3. 子类的静态成员变量
 * 4. 子类的静态代码块
 * 5. 父类的成员变量
 * 6. 父类的代码块
 * 7. 父类的构造函数
 * 8. 子类的成员变量
 * 9. 子类的代码块
 * 10. 子类的构造函数
 */
public class StaticThread {
    static {
        //线程类使用一次推荐使用匿名内部类来实现
        Thread thread = new Thread() {
            //启动新线程将website属性进行赋值
            public void run() {
                System.out.println("进入run()方法");
                System.out.println(website + "2");
                website = "www.baidu.com1";
                System.out.println("退出run()方法");
            }
        };
        //只是让线程处于“就绪态”，并没有运行
        thread.start();
        //让当前的main线程等待新线程执行完毕
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*static {
        //线程类使用一次推荐使用匿名内部类
        Thread thread = new Thread() {
            //启动新线程将website属性进行赋值
            @Override
            public void run() {
                System.out.println("进入run()方法");
                System.out.println(website);
                website = "www.huya.com";
                System.out.println("退出run()方法");
            }
        };
        thread.start();
    }*/

    /*static{
        //线程类使用一次推荐使用匿名内部类
        Thread thread=new Thread(){
            //启动新线程将website属性进行赋值
            @Override
            public void run(){
                System.out.println("进入run()方法");
                System.out.println(website);
                website="www.huya.com";
                System.out.println("退出run()方法");
            }
        };
        thread.start();
    }
    static String website;*/

    //定义一个静态的变量,设置初始值
    static String website = "www.baidu.com";


    public static void main(String[] args) {
        System.out.println(StaticThread.website + "1");
    }
}