package guava.base;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Slf4j
public class JoinerTest {

    @Test
    public void JoinerTest(){
        Joiner joiner = Joiner.on(",").skipNulls();
        List<String> names = Arrays.asList("Double", "Jack", null, "Merry");
        log.debug("{}",joiner.join(names));


        Joiner joiner1 = Joiner.on(";").useForNull("--");
        log.debug("{}",joiner1.join(names));

        Joiner.MapJoiner mapJoiner = Joiner.on(",").withKeyValueSeparator("--");
        Map map = new HashMap<>();
        map.put(1,"Double");
        map.put(2,"Jack");
        log.debug("{}",mapJoiner.join(map));
    }

}
