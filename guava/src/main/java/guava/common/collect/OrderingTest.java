package guava.common.collect;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import guava.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 比较器
 * <p>
 * 当阅读链式调用产生的排序器时，应该从后往前度。下面的例子首先调用 apply 方法获取 sortedBy 值，
 * 并把 sortedBy 为null的元素放到最前面，
 * 然后把剩下的元素按 sortedBy 进行自然排序。
 * 之所以要从后往前度，是因为每次链式调用都是用后面的方法包装前面的排序器。
 * <p>
 * PS：
 * compound (复合)方法进行排序器时，就不应该遵循从后往前读的原则。一般另起一行。
 */
@Slf4j
public class OrderingTest {

    //以创建时间进行排序，null放在最前面
    Ordering<Item> order = Ordering.natural()   //对可排序类型做自然排序，如数组按大小，日期按先后排序
            .nullsFirst()                       // 使用当前排序器，但额外把 null 值拍到最前面
            .onResultOf(item -> {               //对集合中元素调用Function，再按返回值用当前排序器排序
                        if (item == null || item.getCreateTime() == null) return null;
                        return item.getCreateTime().getTime();
                    }
            );

    List<Item> items = Item.getData();

    /**
     * 按创建时间排序
     */
    @Test
    public void onResultOfTest() {


        /*items.add(0,new Item("Test-01",1,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis())));
        items.add(items.size() - 1,new Item("Test-02",2,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis())));
        items.add((items.size() - 1),new Item("Test-03",3,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis())));*/
        items.add(0, null);
        items.add(items.size() - 1, null);
        items.add((items.size() - 1), null);

        boolean ordered = order.isOrdered(items);   //获取可迭代对象中最大的k个元素
        log.debug("是否需已经排序：{}", ordered);
    }

    /**
     * 判断可迭代对象是否已严格按排序器排序
     * ①sortedCopy 返回一个可变的对象
     * immutableSortedCopy 返回一个不可变的对象
     * ②复制后的集合不是深拷贝
     * ③不可对存在null元素的集合进行操作
     */
    @Test
    public void sortedCopyTest() {
        log.debug("未排序前的顺序：{}", items);
        List<Item> itemsCopyed = order.sortedCopy(items);   //判断可迭代对象是否已严格按排序器排序，返回一个可变的对象
        log.debug("以创建时间排序后的数据：{}", itemsCopyed);
        log.debug("sortedCopy后的集合是否是深拷贝:{}", !items.get(2).equals(itemsCopyed.get(0)));

        List<Integer> list = Arrays.asList(1, 1, 2, 3, 4, 5);
        List<Integer> integers = Ordering.natural().sortedCopy(list);
        log.debug("sortedCopy存在相同的元素{}", integers);


        ImmutableList<Integer> integers1 = Ordering.natural().immutableSortedCopy(list);  //返回一个不可变的对象
        log.debug("immutableSortedCopy存在相同的元素：{}", integers1);
//        integers1.add(10); //抛出 UnsupportedOperationException

//        list.add(null); //Arrays.asList创建的对象不允许添加为空的元素

        /*Vector temp = new Vector();  //Vector允许添加为null的元素
        temp.add(1);temp.add(2);temp.add(3);
        temp.add(null);
        log.debug("sortedCopy存在 null 的元素{}", Ordering.natural().sortedCopy(temp));  //抛出NullPointerException
        log.debug("sortedCopy存在 null 的元素{}", Ordering.natural().immutableSortedCopy(temp)); //抛出 NullPointerException*/
    }


    /**
     * 取出第4个元素
     */
    @Test
    public void greatestOfTest() {
        List<Item> data = Item.getData();
        List<Integer> integers = Arrays.asList(1, 3, 5, 6, 2);
        List<Integer> intList = Ordering.natural()
                .greatestOf(integers, 2);
        log.debug("{}", intList);
        //k = 1   [6]
        //k = 2   [6,5]

        List<Item> items = Ordering.natural().greatestOf(data, 2);
        log.debug("{}", items);
        //[Item(name=B, count=2, createTime=2019-08-14 00:00:00.0, updateTime=2019-08-15 00:00:00.0),
        // Item(name=Double, count=24, createTime=2019-06-20 09:44:40.829, updateTime=2019-06-20 09:44:40.829)]
    }


    @Test
    public void FromTest() {
        Item max = Ordering.from(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getCreateTime().getTime() > o2.getCreateTime().getTime() ? 1 : -1;
            }
        }).max(Item.getData());
        log.debug("按创建时间排序：{}", max);

//        Ordering.explicit();

    }

    @Test
    public void usingToStringTest() {
        List<Integer> integers = Arrays.asList(1, 2, 5, 4, 6, 2);
        Collections.sort(integers, Ordering.usingToString());
        log.debug("{}", integers);
    }

    @Test
    public void explicitTest() {
        Ordering<Item> reverse = Ordering.explicit(items).reverse();
        log.debug("{}", reverse);
    }

    @Test
    public void allEqualTest() {
        Item max = Ordering.allEqual() //返回原顺序的集合，并且该排序不会被其他排序算法影响顺序发生变化
                .max(items);
        log.debug("{}", max);
    }

    @Test
    public void compoundTest() {

        Ordering<Item> compound = Ordering.from(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getCreateTime().getTime() - o2.getCreateTime().getTime() > 0 ? 1 : -1;
            }
        }).compound(Ordering.from(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getCount() - o2.getCount();
            }
        }));
        List<Item> items = compound.sortedCopy(this.items);
        log.debug("{}", items);
        // [Item(name=C, count=3, createTime=2019-03-14 00:00:00.0, updateTime=2019-04-15 00:00:00.0),
        // Item(name=D, count=4, createTime=2019-05-14 00:00:00.0, updateTime=2019-09-19 00:00:00.0),
        // Item(name=E, count=2, createTime=2019-06-14 00:00:00.0, updateTime=2019-06-15 00:00:00.0),
        // Item(name=A, count=1, createTime=2019-06-14 00:00:00.0, updateTime=2019-06-15 00:00:00.0),
        // Item(name=Double, count=24, createTime=2019-06-20 10:43:05.066, updateTime=2019-06-20 10:43:05.066),
        // Item(name=B, count=2, createTime=2019-08-14 00:00:00.0, updateTime=2019-08-15 00:00:00.0)]
    }

}
