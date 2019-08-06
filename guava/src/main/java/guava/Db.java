package guava;

import com.google.common.collect.Maps;

import java.util.Map;

public class Db {

    /**
     * 模拟数据库数据
     * @return
     */
    public static Map<Integer,Item> data(){
        return Maps.uniqueIndex(Item.getData(),item -> item.getCount());
    }
}
