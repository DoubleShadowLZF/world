package guava.common.hash;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

@Slf4j
public class BloomFilterTest {

    //100万
    private final int insertions = 1000 * 1000;

    @Test
    public void test(){
        BloomFilter<String> bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8),insertions);

        Set<String> set = new HashSet<>(insertions);
        List<String> list = new ArrayList<>(insertions);

        for (int i = 0; i < insertions; i++) {
            String uuid = UUID.randomUUID().toString();
            bf.put(uuid);
            set.add(uuid);
            list.add(uuid);
        }

        int wrong = 0; //布隆过滤器误判的次数
        int right = 0; //布隆过滤器正确次数

        for (int i = 0; i < 10 * 1000; i++) {
            String str = i % 100 == 0 ? list.get(i/100) : UUID.randomUUID().toString();
            if(bf.mightContain(str)){ //如果数值已经存在布隆过滤器中，则为true，否则为false
                if(set.contains(str)){
                    right++;
                }else{
                    wrong++;
                }
            }
        }
        log.info("right:{}",right);
        log.info("wrong:{}",wrong);
    }
}
