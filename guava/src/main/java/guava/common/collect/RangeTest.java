package guava.common.collect;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class RangeTest {

    @Test
    public void test(){

//        Range.closed(1,3).isEmpty();
        ContiguousSet<Integer> integers = ContiguousSet.create(Range.closed(1, 3), DiscreteDomain.integers());
        log.info("{}",integers);

    }
}
