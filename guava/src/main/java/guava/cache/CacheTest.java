package guava.cache;

import com.google.common.cache.*;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 适用性
 * 1.用内存换速度；
 * 2.key不止一次被查询；
 * 3.缓存数据不会超过RAM容量；
 * 4.自动缓存加载。
 */
@Slf4j
public class CacheTest {
    @Test
    public void Test() throws ExecutionException {
        LoadingCache<Object, Object> cache = CacheBuilder.newBuilder()
                .maximumSize(1000) //设置缓存的最大容量
                .expireAfterAccess(1, TimeUnit.HOURS) //如果 1小时内没有被访问，就失效
                .expireAfterWrite(10, TimeUnit.MINUTES) //如果 10 分钟内没有被重写，就失效
                .concurrencyLevel(10) //设置并发级别为 10
                .recordStats() //开启缓存统计
                .build(new MyCacheLoader()); //当缓存不存在时，通过CacheLoader自动加载，

        //缓存数据
        cache.put("key", "value");
        //获取缓存数据
        Object value = cache.getIfPresent("key");
        log.debug("{}", value);

        Object value01 = cache.get("key-" + new DateTime().toString("yyyy-MM-dd"));
        log.debug("{}", value01);

        Object value02 = cache.getIfPresent("key-" + new DateTime().toString("yyyy-MM-dd"));
        log.debug("{}", value02);

        cache.getUnchecked("key");//获取缓存，不会抛异常

    }

    /**
     * 缓存回收策略：
     * maximumWeigh：指定最大容量
     * Weigher：在加载缓存时用于计算缓存容量大小
     */
    @Test
    public void garbageTest() {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumWeight(20) // 设置最大容量为10 byte
                .weigher(new MyWeight())  //每次存放数据时，都会计算数据的权重值，超出的话，则清除缓存
                .build();
        //此处设置 maximumWeight 为10
//        cache.put("0123456789", "1");  //每次存放数据时，会调用 weigher 重新计算一次权重值
//        cache.put("01234567891", "01234567891");
//        cache.put("0123456789", "1");

        cache.put("0","0123456789");
//        cache.put("1","0123456789");
//        cache.put("2","0123456789");

        long size = cache.size();
        log.debug("缓存容量：{}", size);
        ConcurrentMap<String, String> map = cache.asMap();
        Set<Map.Entry<String, String>> entries = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            log.debug("key:{},value:{}", entry.getKey(), entry.getValue());
        }

    }

    /**
     * 通过软/弱引用的回收方式，相当于将缓存收回任务交给了GC，使得缓存的命中率变得十分不稳定，
     * 在非必要的情况下，还是推荐基于数量和容量的回收
     */
    @Test
    public void refTest(){
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .weakKeys()  //使用弱引用存储键，当键没有其他（强或软）引用时，该缓存可能会被回收
                .weakValues() //使用弱引用存储值，当值没有其他（强或软）引用时，该缓存可能会被回收
                .softValues() //使用软引用存储值，当内存不足并且该值其他强引用引用时，该缓存就会被回收
                .build();
    }

    /**
     * 回收缓存
     */
    @Test
    public void invalidateTest(){
        Cache<String, Object> cache = CacheBuilder.newBuilder().build();
        cache.invalidate("k1"); // 回收key为1的缓存
        List<String> keys = Arrays.asList("k1", "k2");
        cache.invalidateAll(keys); //批量回收key为k1,k2的缓存
        cache.invalidateAll(); //回收所有缓存
    }

    /**
     * 设置缓存过期策略
     */
    @Test
    public void expireTimeTest(){
        CacheBuilder.newBuilder()
                .expireAfterWrite(10,TimeUnit.MINUTES) //写入10分钟后过期
                .expireAfterAccess(10,TimeUnit.MINUTES)//10分钟内未访问则过期，每次访问刷新过期时间
                .build();
    }

    /**
     * 定时刷新缓存，指定缓存的刷新间隔，和一个用来加载缓存的CacheLoader，当达到刷新时间间隔后，
     * 下一次获取缓存时，会调用CacheLoader的load方法刷新缓存。
     */
    @Test
    public void refreshTest(){
        CacheBuilder.newBuilder()
                .refreshAfterWrite(10,TimeUnit.MINUTES) //设置缓存在写入10分钟后，通过CacheLoader的load方法进行刷新
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String s) throws Exception {
                        //缓存加载逻辑
                        return null;
                    }
                });
    }

    @Test
    public void culStringSizeTest() {
        String text = "1";
        log.debug("{}", text.getBytes().length);
    }

    /**
     * 缓存加载策略
     */
    public class MyCacheLoader extends CacheLoader<Object, Object> {
        @Override
        public Object load(Object o) throws Exception {
            if (o == null) throw new NullPointerException();
            if (o instanceof String) return (String) o + "--Double";
            return null;
        }
    }

    /**
     * 权重计算
     */
    public class MyWeight implements Weigher<String, String> {
        @Override
        public int weigh(String key, String value) {
            int weigh = key.getBytes().length + value.getBytes().length;
            log.debug("{}:{}的权重值：{}",key,value,weigh);
            return weigh;
        }
    }

}
