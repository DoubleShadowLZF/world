package guava.base;

import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.List;

/**
 * 判断条件测试类
 */
public class PreconditionsTest {
    @Test
    public void test(){
        //1.判断是否为true
//        int i = 1;
//        Preconditions.checkArgument( i == 0,"i的值不为0");

        //2.判断不为null
//        Item item = new Item();
//        item.setName("Double");
//        Item itemNull  =  null;
//        Item item1 = Preconditions.checkNotNull(item);
//        System.out.println(item1);
//        item1 = Preconditions.checkNotNull(itemNull);
//        System.out.println(item1);

        //3.检测某些状态
//        Preconditions.checkState(item.name != null);

        //4.检测数组或集合的下标值
        //size大于index，不抛异常，并返回index
        //size小于或等于index，抛出 IndexOutOfBoundsException
        List<Integer> list = Ints.asList(1, 2, 3);
//        int i = Preconditions.checkElementIndex(3, list.size());
//        System.out.println(i);

        //5.检测数组和集合的下标值
        //size大于或等于index，不抛异常，并返回下标值
        //size小于index，抛出 IndexOutOfBoundsException
        // checkPositionIndex 和 checkElementIndex 的区别主要是，前者在 index 和 size 相同时，不会抛异常，后者会。
//        int i1 = Preconditions.checkPositionIndex(3, list.size());
//        System.out.println(i1);

    }
}
