package org.world.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

/**
 * ClassName: LRUCache <br>
 * Description: LRU: 最近最少使用算法 。 最近最少使用的元素，在接下来一段时间内，被访问的概率也很低。 * 即最近被使用的元素，在接下来一段时间内，被访问概率较高。 *
 *
 * <p>* 用链表的结构： * 链表尾表示最近被访问的元素，越靠近链表头表示越早之前被访问的元素 *
 *
 * <p>* 插入一个元素，cache 不满，插到链表尾，满，移除cache链头元素再插入链表尾
 * <p>* 访问一个元素，从链表尾部开始遍历, 访问到之后，将其从原位置删除，重新加入链表尾部 *
 *
 * <p>* 实现1：用双向链表实现。 * put、get 时间复杂度:O(n) 效率低<br>
 * date: 2020/6/28 23:15<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class LRUCache<K, V> {

    //缓存
    private LinkedList<Node> cache;
    //容量
    private int capacity;

    public LRUCache(int capacity) {
        this.cache = new LinkedList<>();
        this.capacity = capacity;
    }


    /**
     * 插入一个元素，cache 不满，插到链表尾，满，移除cache链头元素再插入链表尾
     */
    public void put(K k, V v) {
        Iterator<Node> nodeIterator = cache.descendingIterator();
        while (nodeIterator.hasNext()) {
            Node next = nodeIterator.next();
            if (next.k.equals(k)) {
                nodeIterator.remove();
                break;
            }
        }
        if (cache.size() == this.capacity) {
            cache.removeFirst();
        }
        cache.addLast(new Node(k, v));
    }

    /**
     * 访问一个元素，从链表尾部开始遍历, 访问到之后，将其从原位置删除，重新加入链表尾部
     */
    public V get(K k, V v) {
        Iterator<Node> nodeIterator = cache.descendingIterator();
        Node retNode = null;
        while (nodeIterator.hasNext()) {
            retNode = nodeIterator.next();
            boolean isHit = (k == null || retNode.k.equals(k)) && (v == null || retNode.v.equals(v));
            if (isHit) {
                nodeIterator.remove();
                break;
            }
        }
        if (cache.size() == this.capacity) {
            cache.removeFirst();
        }
        cache.addLast(retNode);
        return retNode.v;
    }

    public V get(K k) {
        return get(k, null);
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator<Node> iterator = cache.iterator();
        sb.append("[");
        while (iterator.hasNext()) {
            Node node = iterator.next();
            sb.append(node).append(",\t");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }

    /**
     * 节点
     */
    public class Node {
        K k;
        V v;

        public Node(K k, V v) {
            this.k = k;
            this.v = v;
        }

        @Override
        public final String toString() {
            return k + "=" + v;
        }
    }

    public static void main(String[] args) {
        int capacity = 3;
        LRUCache<Integer, String> cache = new LRUCache<>(capacity);
        System.out.println(String.format("长度为%d的cache，存入超长数据后：", capacity));
        for (int i = 0; i < 5; i++) {
            cache.put(i, UUID.randomUUID().toString().substring(0, 10));
        }
        System.out.println(cache);

        String val = cache.get(0);
        System.out.println(String.format("取出的数据：%s", val));

        System.out.println(cache);
    }
}
