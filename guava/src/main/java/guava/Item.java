package guava;

import com.google.common.collect.Lists;
import lombok.Data;
import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.util.*;

@Data
public class Item  implements Comparable<Item>{
    private String name;
    private Integer count;

    private Timestamp createTime;
    private Timestamp updateTime;

    public Item() {
    }

    public Item(String name, Integer count) {
        this.name = name;
        this.count = count;
    }

    public Item(String name, Integer count, Timestamp createTime, Timestamp updateTime) {
        this.name = name;
        this.count = count;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    @Override
    protected Object clone() {
        return new Item(name, count);
    }


    public static List<Item> getData(){
        Item a = new Item("A", 1,
                new Timestamp(new DateTime("2019-06-14").getMillis()),
                new Timestamp(new DateTime("2019-06-15").getMillis()));
        Item e = new Item("E", 2,
                new Timestamp(new DateTime("2019-06-14").getMillis()),
                new Timestamp(new DateTime("2019-06-15").getMillis()));
        Item c = new Item("C", 3,
                new Timestamp(new DateTime("2019-03-14").getMillis()),
                new Timestamp(new DateTime("2019-04-15").getMillis()));
        Item d = new Item("D", 4,
                new Timestamp(new DateTime("2019-05-14").getMillis()),
                new Timestamp(new DateTime("2019-09-19").getMillis()));
        Item b = new Item("B", 2,
                new Timestamp(new DateTime("2019-08-14").getMillis()),
                new Timestamp(new DateTime("2019-08-15").getMillis()));
        Item aDouble = new Item("Double", 24);
        aDouble.setCreateTime(new Timestamp(new DateTime().getMillis()));
        aDouble.setUpdateTime(new Timestamp(new DateTime().getMillis()));
        return Lists.newArrayList(a, b, c, d,aDouble,e);
    }

    public static List<Item> randomList(){
        List ret = new ArrayList();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            ret.add(new Item(UUID.randomUUID().toString().substring(0,3), random.nextInt(10)));
        }
        return ret;
    }

    public static Item[] randomArray(){
        Item[] items = new Item[5];
        Random random = new Random();
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item(UUID.randomUUID().toString().substring(0,3), random.nextInt(10));
        }
        return items;
    }


    @Override
    public int compareTo(Item o) {
        if(o == null || o.getCreateTime() == null) return 1;
        if(o.getCreateTime().getTime() < this.getCreateTime().getTime()) return 1;
        return -1;
    }
}
