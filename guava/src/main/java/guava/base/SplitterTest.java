package guava.base;

import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class SplitterTest {
    /**
     * 分割字符串
     */
    @Test
    public void test(){
        String text = "Double,Jack,,Marry";
        Iterable<String> names = Splitter.on(",").omitEmptyStrings().split(text);
        for (String name : names) {
            log.debug("{}",name);
        }

        int len = 2;
        log.debug("-->限制长度{}：",len);
        names = Splitter.on(",").trimResults().limit(len).split(text);
        for (String name : names){
            log.debug("{}",name);
        }
    }
}
