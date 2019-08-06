package guava.common.collect;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import guava.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ListsTest {

    List<Item> list = Item.getData();

    @Test
    public void toStringTest(){
        ArrayList<String> list = Lists.newArrayList("a", "b", "c");
        System.out.println(String.format("转换：%s",list));
    }

    /**
     * 合并集合和数组
     */
    @Test
    public void asListTest(){
        List<Object> objects = Lists.asList(this.list, Item.randomArray());
        log.debug("{}",objects);
    }

    /**
     * 构造器
     */
    @Test
    public void NewTest(){

        Lists.newArrayList();
        //确定元素的个数时，返回容量大小 = 5L + arraySize + (arraySize / 10),
        //给定到大小和真正分配的容量之比为 10/11
        Lists.newArrayListWithCapacity(4);
        //不确定元素的个数
        Lists.newArrayListWithExpectedSize(4);
        Lists.newLinkedList();
        Lists.newCopyOnWriteArrayList();

        /**
         * CopyOnWriteArrayList:
         * 实现了List接口
         * 内部持有一个ReentrantLock lock = new ReentrantLock();
         * 底层是用volatile transient声明的数组 array
         * 读写分离，写时复制出一个新的数组，完成插入、修改或者移除操作后将新数组赋值给array
         */
    }

    /**
     * 转换器
     */
    @Test
    public void transformTest(){
        List<String> names = Lists.transform(this.list, item -> item.getName());
        log.debug("{}",names);
    }

    /**
     * 根据 size 传入的list进行分割，切割成符合要求小的List，
     * 并将这些小的List存入到新的List对象中
     */
    @Test
    public void partitionTest(){
        List<List<Item>> subList = Lists.partition(this.list, 3);
        log.debug("{}",subList);
        //[[Item(name=A, count=1), Item(name=B, count=2), Item(name=C, count=3)], [Item(name=D, count=4)]]
    }

    /**
     * 将传进来的 String 或者 CharSequence 分割为单个字符，并存入到一个ImmutableList对象中返回。
     * （ImmutableList：一个高性能、不可变、随机访问列表的实现）
     */
    @Test
    public void charactersOfTest(){
        ImmutableList<Character> characters = Lists.charactersOf("Double");
        log.debug("{}",characters);
        //[D, o, u, b, l, e]
    }

    @Test
    public void reverseTest(){
        log.debug("原集合排序：{}",this.list);
        List<Item> reverseList = Lists.reverse(this.list);
        log.debug("倒序后的排序：{}",reverseList);
    }

    /**
     * 将集合内的元素拆分为单个元素为一个集合
     */
    @Test
    public void Test(){
        log.debug("原集合：{}",this.list);
        List<List<Item>> lists = Lists.cartesianProduct(this.list);
        log.debug("排序后的集合：{}",lists);
        //原集合：[Item(name=A, count=1), Item(name=B, count=2), Item(name=C, count=3), Item(name=D, count=4)]
        //排序后的集合：[[Item(name=A, count=1)], [Item(name=B, count=2)], [Item(name=C, count=3)], [Item(name=D, count=4)]]

        List<List<Integer>> lists1 = Lists.cartesianProduct(Lists.newArrayList(1, 3, 2, 5, 3));
        log.debug("{}",lists1);
        //[[1], [3], [2], [5], [3]]
    }
}
