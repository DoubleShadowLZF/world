package jvm;

/**
 *
 *
 */
public class ReflectTest {
    private static final int _1MB = 1024 * 1024 ;

    /**
     * -verbose:gc -XX:+PrintGCDetails -Xms20m -Xmx20m -Xmn10m -XX:SurvivorRatio=8
     * 打印日志，堆内存为20M且不可拓展，年轻代为10m
     *
     *  运行结果：
     *  PSYoungGen      total 9216K, used 7285K                      //此处 allcation1,allcation2,allcation3 总占用 6M，则还有1141K是程序本身占用内存
     *      eden space 8192K, 77% used
     *      from space 1024K, 88% used
     *      to   space 1024K, 0% used
     *  ParOldGen       total 10240K, used 4104K
     *      object space 10240K, 40% used
     *  Metaspace       used 3495K, capacity 4498K, committed 4864K, reserved 1056768K
     *      class space    used 387K, capacity 390K, committed 512K, reserved 1048576K
     *
     * @throws InterruptedException
     */
    public static void testAllocation1() throws InterruptedException {
        byte[] allcation1 = new byte[2 * _1MB ];
        byte[] allcation2 = new byte[2 * _1MB];
        byte[] allcation3 = new byte[2 * _1MB];
        byte[] allcation4 = new byte[4 * _1MB];
    }

    public static void testAllocation2() throws InterruptedException {
        byte[] allcation1 = new byte[_1MB / 4];
        byte[] allcation2 = new byte[4 * _1MB];
        byte[] allcation3 = new byte[4 * _1MB];
//        allcation3 = null;
        byte[] allcation4 = new byte[4 * _1MB];
//        byte[] allcation5 = new byte[4 * _1MB];
        for (int i = 0; i < 1000; i++) {

        }
    }

    /**
     * 初始化占用内存 占用年轻代 2368K，老年代0K
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ReflectTest.testAllocation1();
//        ReflectTest.testAllocation2();
    }

}
