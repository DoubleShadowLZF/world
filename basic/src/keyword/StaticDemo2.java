package keyword;

/**
 * ClassName: StaticDemo1 <br/>
 * Description: 类加载机制解析 <br/>
 * <p>JVM 类加载机制分为五个部分：加载，验证，准备，解析，初始化</p>
 * date: 2020/7/28 1:17<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public class StaticDemo2 {
    public static class A {
        //1、在准备阶段，类变量分配内存并设置类变量的初始值阶段
        //如果去掉 static ，则会栈溢出，因为 A 的对象内部引用 A的示例，会递归创建
        private static A a = new A();

        // 初始化阶段是执行类构造器<client>方法的过程。<client>方法是由编译器自动收集类中的类变
        // 量的赋值操作和静态语句块中的语句合并而成的。虚拟机会保证子<client>方法执行之前，父类
        // 的<client>方法已经执行完毕，如果一个类中没有对静态变量赋值也没有静态语句块，那么编译
        // 器可以不为这个类生成<client>()方法。

        // 2、在解析阶段，初始化静态代码块
        static {
            System.out.println("static");
        }

        //1.1、在准备阶段，类变量分配内存并设置类变量的初始值阶段时， a 属性会被初始化，优先于被 static 执行。
        {
            System.out.println("A1 : " + hashCode());
        }

        //1.2、在准备阶段，类变量分配内存并设置类变量的初始值阶段时， a 属性会被初始化，优先于被 static 执行。
        // 3、在解析阶段，执行构造函数
        public A() {
            System.out.println("A Constructor : " + hashCode());
        }
    }


}

class B extends StaticDemo2.A {

    //4、在解析阶段，执行构造函数
    public B() {
        System.out.println("B Constructor");
    }

    public static void main(String[] args) {
        System.out.println("main");
        B b = new B();
    }

}
