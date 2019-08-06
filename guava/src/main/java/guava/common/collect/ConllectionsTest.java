package guava.common.collect;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import guava.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 集合测试类
 */
@Slf4j
public class ConllectionsTest {

    List<Item> list = Item.getData();

    /**
     * 过滤器
     */
    @Test
    public void FilterTest() {

        System.out.println(String.format("创建后的数组：%s", list));
        Collection<Item> items = Collections2.filter(list, (item) -> item.getCount() > 2);

        System.out.println(String.format("过滤后的数组：%s", items));

        Assert.assertEquals(2, items.size());
    }

    /**
     * 格式转换
     */
    @Test
    public void TransformTest() {
        Collection<String> names = Collections2.transform(list, item -> item.getName());
        log.debug("{}", names);
        Assert.assertEquals(4, names.size());
    }


    /**
     * orderedPermutations 对迭代器进行排序
     * 先将元素排序，再排序
     */
    @Test
    public void OrderedPermutationsTest() {
        //Comparator
        // 大于0，降序排序
        // 小于0，升序排序
        Collection<List<Item>> items = Collections2.orderedPermutations(list, (o1, o2) -> o2.getCount() - o1.getCount());
        log.debug("{}", items);


        ArrayList<String> names = Lists.newArrayList( "b", "a","c");
        Collection<List<String>> lists = Collections2.orderedPermutations(names);
        log.debug("{}",lists);
    }

    /**
     * 对list直接排序
     */
    @Test
    public void PerMutationsTest(){
        log.debug("排序前集合顺序：{}",list);
        Collection<List<Item>> permutations = Collections2.permutations(list);
        log.debug("排序后集合顺序：{}",permutations);
    }

}
