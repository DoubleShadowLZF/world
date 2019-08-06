package guava.base;

import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class CaseFormatTest {

    @Test
    public void test(){
        String a = "lowerCamel";
        String b = "lower-hyphen";
        String c= "lower_underscore";
        String d = "UpperCamel";
        String e = "UPPER_UNDERSCORE";
        log.debug("-->驼峰命名：{}转化为蛇形命名:{}",a,CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, a));
        log.debug("-->蛇形命名：{}转化为驼峰命名:{}",a,CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, a));
    }
}
