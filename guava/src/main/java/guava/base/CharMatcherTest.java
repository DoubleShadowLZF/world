package guava.base;

import com.google.common.base.CharMatcher;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class CharMatcherTest {

    @Test
    public void test(){
        String blob = "Double123";
        String ret = CharMatcher.digit().retainFrom(blob);
        log.debug("1.获取字符串中的数字：{}",ret);

        blob = " Hello  World  ! ";
        ret = CharMatcher.whitespace().trimAndCollapseFrom(blob,'-');
        log.debug("2.多个空格替换，并去掉首位的空格：{}",ret);

        blob = " ab\tcd\nef\bg";
        ret = CharMatcher.javaIsoControl().removeFrom(blob);
        log.debug("3.去掉转移字符：{}",ret);

        blob = "Double123";
        ret = CharMatcher.javaDigit().replaceFrom(blob,"");
        log.debug("4.将数字替换为空字符串：{}",ret);

        ret = CharMatcher.javaDigit().or(CharMatcher.javaLowerCase()).retainFrom(blob);
        log.debug("5.获取所有的数字和小写字母：{}",ret);

        ret = CharMatcher.javaUpperCase().retainFrom(blob);
        log.debug("6.获取所有的大写字母：{}",ret);

        blob = "hello 中国";
        ret = CharMatcher.singleWidth().retainFrom(blob);
        log.debug("7.获取所有单字节长度的符号：{}，长度：{}",ret,ret.length());

        blob = "Double123 !@#$%^&*() 中国";
        log.debug("8.获取字母：{}",CharMatcher.javaLetter().retainFrom(blob));

        log.debug("9.获取字母和数字：{}",CharMatcher.javaLetterOrDigit().retainFrom(blob));

        log.debug("10.出现次数：{},长度：{}",CharMatcher.any().countIn(blob),blob.length());

        log.debug("11.数字出现的次数：{}",CharMatcher.digit().countIn(blob));

        log.debug("12.获取除小写字母外其他所有字符：{}",CharMatcher.javaLowerCase().negate().retainFrom(blob));

    }

//        CharMatcher本身提供了很多CharMatcher实现类,如下:
//        ANY: 匹配任何字符
//        ASCII: 匹配是否是ASCII字符
//        BREAKING_WHITESPACE: 匹配所有可换行的空白字符(不包括非换行空白字符,例如"\u00a0")
//        DIGIT: 匹配ASCII数字
//        INVISIBLE: 匹配所有看不见的字符
//        JAVA_DIGIT: 匹配UNICODE数字, 使用 Character.isDigit() 实现
//        JAVA_ISO_CONTROL: 匹配ISO控制字符, 使用 Charater.isISOControl() 实现
//        JAVA_LETTER: 匹配字母, 使用 Charater.isLetter() 实现
//        JAVA_LETTER_OR_DIGET: 匹配数字或字母
//        JAVA_LOWER_CASE: 匹配小写
//        JAVA_UPPER_CASE: 匹配大写
//        NONE: 不匹配所有字符
//        SINGLE_WIDTH: 匹配单字宽字符, 如中文字就是双字宽
//        WHITESPACE: 匹配所有空白字符

//        CharMatcher is(char match): 返回匹配指定字符的Matcher
//        CharMatcher isNot(char match): 返回不匹配指定字符的Matcher
//        CharMatcher anyOf(CharSequence sequence): 返回匹配sequence中任意字符的Matcher
//        CharMatcher noneOf(CharSequence sequence): 返回不匹配sequence中任何一个字符的Matcher
//        CharMatcher inRange(char startInclusive, char endIncludesive): 返回匹配范围内任意字符的Matcher
//        CharMatcher forPredicate(Predicate<? super Charater> predicate): 返回使用predicate的apply()判断匹配的Matcher
//        CharMatcher negate(): 返回以当前Matcher判断规则相反的Matcher
//        CharMatcher and(CharMatcher other): 返回与other匹配条件组合做与来判断的Matcher
//        CharMatcher or(CharMatcher other): 返回与other匹配条件组合做或来判断的Matcher
//        boolean matchesAnyOf(CharSequence sequence): 只要sequence中有任意字符能匹配Matcher,返回true
//        boolean matchesAllOf(CharSequence sequence): sequence中所有字符都能匹配Matcher,返回true
//        boolean matchesNoneOf(CharSequence sequence): sequence中所有字符都不能匹配Matcher,返回true
//        int indexIn(CharSequence sequence): 返回sequence中匹配到的第一个字符的坐标
//        int indexIn(CharSequence sequence, int start): 返回从start开始,在sequence中匹配到的第一个字符的坐标
//        int lastIndexIn(CharSequence sequence): 返回sequence中最后一次匹配到的字符的坐标
//        int countIn(CharSequence sequence): 返回sequence中匹配到的字符计数
//        String removeFrom(CharSequence sequence): 删除sequence中匹配到到的字符并返回
//        String retainFrom(CharSequence sequence): 保留sequence中匹配到的字符并返回
//        String replaceFrom(CharSequence sequence, char replacement): 替换sequence中匹配到的字符并返回
//        String trimFrom(CharSequence sequence): 删除首尾匹配到的字符并返回
//        String trimLeadingFrom(CharSequence sequence): 删除首部匹配到的字符
//        String trimTrailingFrom(CharSequence sequence): 删除尾部匹配到的字符
//        String collapseFrom(CharSequence sequence, char replacement): 将匹配到的组(连续匹配的字符)替换成replacement
//        String trimAndCollapseFrom(CharSequence sequence, char replacement): 先trim在replace

}
/**
 * 11:40:26.192 [main] DEBUG guava.base.CharMatcherTest - 1.获取字符串中的数字：123
 * 11:40:26.196 [main] DEBUG guava.base.CharMatcherTest - 2.多个空格替换，并去掉首位的空格：Hello-World-!
 * 11:40:26.197 [main] DEBUG guava.base.CharMatcherTest - 3.去掉转移字符： abcdefg
 * 11:40:26.197 [main] DEBUG guava.base.CharMatcherTest - 4.将数字替换为空字符串：Double
 * 11:40:26.197 [main] DEBUG guava.base.CharMatcherTest - 5.获取所有的数字和小写字母：ouble123
 * 11:40:26.197 [main] DEBUG guava.base.CharMatcherTest - 6.获取所有的大写字母：D
 * 11:40:26.197 [main] DEBUG guava.base.CharMatcherTest - 7.获取所有单字节长度的符号：hello ，长度：6
 * 11:40:26.198 [main] DEBUG guava.base.CharMatcherTest - 8.获取字母：Double中国
 * 11:40:26.199 [main] DEBUG guava.base.CharMatcherTest - 9.获取字母和数字：Double123中国
 * 11:40:26.199 [main] DEBUG guava.base.CharMatcherTest - 10.出现次数：23,长度：23
 * 11:40:26.199 [main] DEBUG guava.base.CharMatcherTest - 11.数字出现的次数：3
 * 11:40:26.199 [main] DEBUG guava.base.CharMatcherTest - 12.获取除大写字母外其他所有字符：D123 !@#$%^&*() 中国
 */
