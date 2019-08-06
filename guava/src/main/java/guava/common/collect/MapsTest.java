package guava.common.collect;

import com.google.common.collect.*;
import guava.Db;
import guava.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

/**
 * map的测试类
 */
@Slf4j
public class MapsTest {

    @Test
    public void newTest(){
        Maps.newHashMap();
        //HashMap 默认分配容量为16，指定容量大小
        Maps.newHashMapWithExpectedSize(4);
        Maps.newLinkedHashMap();

        //创建线程安全地map
        Maps.newConcurrentMap();

        //键值允许重复的map
        IdentityHashMap<String, Object> identityHashMap = Maps.newIdentityHashMap();
        identityHashMap.put("a",1);
        identityHashMap.put("b",2);
        identityHashMap.put(new String("c"),3);
        identityHashMap.put(new String("c"),4);
        log.debug("{}",identityHashMap);


        Maps.newTreeMap();
        EnumMap<Enum, Object> enumMap = Maps.newEnumMap(Enum.class);
        enumMap.put(Enum.a,1);

    }

    /**
     * 将 Function 的返回值 作为键值对中的值返回一个 map
     */
    @Test
    public void asMapTest(){
        HashSet<Item> integers = Sets.newHashSet(Item.getData());
        Map<Item, Boolean> a = Maps.asMap(integers, item -> item.getName().indexOf("a") > -1);
        log.debug("{}",a);
        // {Item(name=B, count=2)=false, Item(name=A, count=1)=false, Item(name=D, count=4)=false, Item(name=C, count=3)=false}
    }

    /**
     * 使用返回值（唯一）作为键，集合作为值，返回一个 Map
     */
    @Test
    public void uniqueIndexTest(){
        ImmutableMap<Integer, Item> map = Maps.uniqueIndex(Item.getData(), item -> item.getCount());
       log.debug("{}",map);
       //{1=Item(name=A, count=1), 2=Item(name=B, count=2), 3=Item(name=C, count=3), 4=Item(name=D, count=4)}
    }

    @Test
    public void differenceTest(){
        List<Item> data = Item.getData();
        ImmutableMap<Integer, Item> map1 = Maps.uniqueIndex(data, item -> item.getCount());
        data.remove(2);
        data.add(2,new Item("T",12));
        ImmutableMap<Integer, Item> map2 = Maps.uniqueIndex(data, item -> item.getCount());
        MapDifference<Integer, Item> difference = Maps.difference(map1, map2);
        log.debug("比较的Map：{},{},得到的Map：{}",map1,map2,difference);
//        {1=Item(name=A, count=1), 2=Item(name=B, count=2), 3=Item(name=C, count=3), 4=Item(name=D, count=4)},
// {1=Item(name=A, count=1), 2=Item(name=B, count=2), 12=Item(name=T, count=12), 4=Item(name=D, count=4)},
// 得到的Map：not equal: only on left={3=Item(name=C, count=3)}: only on right={12=Item(name=T, count=12)}

//        difference的数据结构：
//        键：                   值：
//        onlyOnLeft            "3" -> "Item(name=C, count=3)"
//        onlyOnRight           "12" -> "Item(name=T, count=12)"
//        onBoth                "1" -> "Item(name=A, count=1)"
//                              "2" -> "Item(name=B, count=2)"
//                              "4" -> "Item(name=D, count=4)"

    }

    /**
     * 过滤器
     */
    @Test
    public void FilterTest(){
        Map<Integer, Item> map1 = Maps.filterKeys(Db.data(), key -> key > 2);
        log.debug("过滤出“键”大于2的键值对:{}",map1);

        Map<Integer, Item> map2 = Maps.filterValues(Db.data(), item -> item.getCount() > 2);
        log.debug("过滤出“值”中count值大于2的键值对:{}",map2);

        Map<Integer, Item> map3 = Maps.filterEntries(Db.data(), entity -> entity.getKey() == entity.getValue().getCount());
        log.debug("过滤出“键”和“值”中count值相等的键值对：{}",map3);
    }

    @Test
    public void subMapTest(){
        NavigableMap map = new TreeMap(Db.data());

        /**
         *
         */
        //fixme NavigableMap 有哪些？除了TreeMap 还有没有别的？
        /*Maps.subMap(map,Range.atLeast(new Comparable<Item>() {
            @Override
            public int compareTo(Item item) {
                return item.getCreateTime().getTime() - 30 * 7 * 24 * 60 * 60 * 1000 > 0 ? 1 : -1;
            }
        }));*/
    }

    public static enum Enum{
        a,b,c
    }
}
