package guava.common.collect;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

@Slf4j
public class TableTest {

    @Test
    public void test(){
        Table<Integer, Integer, Integer> table = HashBasedTable.create();
        table.put(1,2,4);
        //允许row和column确定的二维点重复
        table.put(1,6,3);
        //判断row和column确定的二维点是否存在
        if(table.contains(1,2)){
            table.put(1,4,4);
            table.put(2,5,4);
        }
        log.debug("table:{}",table);

        //获取column为5的数据集
        Map<Integer,Integer> column = table.column(5);
        log.debug("column:{}",column);

        //获取row为1的数据集
        Map<Integer, Integer> row = table.row(1);
        log.debug("row:{}",row);

        //获取row为1，column为2的结果
        Integer value = table.get(1, 2);
        log.debug("row:1,column:2 -> {}",value);

        //判断是否包含row为1的视图
        log.debug("是否包含row为1的视图：{}",table.containsRow(1));

        log.debug("是否包含column为2的视图：{}",table.containsColumn(2));

        log.debug("是否包含值为2的集合：{}",table.containsValue(2));

        //将table转换为Map套Map 格式
        Map<Integer,Map<Integer,Integer>> rowMap = table.rowMap();
        log.debug("rowMap：{}",rowMap);

        //获取所有的rowKey值的集合
        Set<Integer> keySet = table.rowKeySet();
        log.debug("keySet:{}",keySet);

        //删除row为1，column为2的元素，返回删除元素的值
        Integer res = table.remove(1, 2);
        table.clear();
        log.debug("删除元素：{}",res);
        log.debug("table:{}",table);
    }
    /**
     * 12:55:16.601 [main] DEBUG guava.common.collect.TableTest - table:{1={2=4, 6=3, 4=4}, 2={5=4}}
     * 12:55:16.610 [main] DEBUG guava.common.collect.TableTest - column:{2=4}
     * 12:55:16.612 [main] DEBUG guava.common.collect.TableTest - row:{2=4, 6=3, 4=4}
     * 12:55:16.612 [main] DEBUG guava.common.collect.TableTest - row:1,column:2 -> 4
     * 12:55:16.612 [main] DEBUG guava.common.collect.TableTest - 是否包含row为1的视图：true
     * 12:55:16.612 [main] DEBUG guava.common.collect.TableTest - 是否包含column为2的视图：true
     * 12:55:16.613 [main] DEBUG guava.common.collect.TableTest - 是否包含值为2的集合：false
     * 12:55:16.613 [main] DEBUG guava.common.collect.TableTest - rowMap：{1={2=4, 6=3, 4=4}, 2={5=4}}
     * 12:55:16.613 [main] DEBUG guava.common.collect.TableTest - keySet:[1, 2]
     * 12:55:16.614 [main] DEBUG guava.common.collect.TableTest - 删除元素：4
     * 12:55:16.614 [main] DEBUG guava.common.collect.TableTest - table:{}
     */
}

