package org.world.demo.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.jupiter.api.Test;

public class TimeTest {

    @Test
    public void test01(){
        //①取当前时间
        DateTime dateTime = new DateTime();
        System.out.println("now:\t"+dateTime);

        //②时间格式转换
        String timeString = "2006-01-26T13:30:00";
        dateTime = new DateTime(timeString);
        System.out.println(timeString +":\t"+dateTime);

        timeString = "2006-01-26";
        dateTime = new DateTime(timeString);
        System.out.println("2006-01-26:\t"+dateTime);

        //③日期计算
        //前：    minus**()
        //后：    plus**()
        DateTime now = new DateTime();
        //昨天
        DateTime yesterday = now.minusDays(1);
        System.out.println("yesterday:\t"+yesterday);
        //明天
        DateTime tomorrow = now.plusDays(1);
        System.out.println("tomorrow:\t"+tomorrow);
        //两周前
        DateTime beforeTwoWeeks = now.minusWeeks(2);
        System.out.println("before two weeks:"+beforeTwoWeeks);

        //④格式化输出
        System.out.println(now.toString("yyyy-mm-dd hh:MM:ss"));
        System.out.println(now.toString(ISODateTimeFormat.dateTimeNoMillis()));

        //⑤时区
        DateTimeZone.setDefault(DateTimeZone.forID("Asia/Tokyo"));
        DateTime dt1 = new DateTime();
        System.out.println(dt1);

        //⑥计算区间
        DateTime birthday = new DateTime("1995-07-01T20:00:00");
        Duration duration = new Duration(birthday, now);
        System.out.println("存活时间："+duration.getStandardSeconds());
        System.out.println("存活时间："+duration.getStandardDays());

        //⑦日期比较
        DateTime temp = new DateTime("2012-05-01");
        boolean beforeNow = temp.isBeforeNow();
        System.out.println(temp+"是否是过去时间："+beforeNow);
        boolean after = temp.isAfter(now);
        System.out.println(temp+"是否是"+now+"的超前时间："+after);

        //⑧周期的特殊转换
        switch(now.getDayOfWeek()) {
            case DateTimeConstants.SUNDAY:
                System.out.println("星期日");
                break;
            case DateTimeConstants.MONDAY:
                System.out.println("星期一");
                break;
            case DateTimeConstants.TUESDAY:
                System.out.println("星期二");
                break;
            case DateTimeConstants.WEDNESDAY:
                System.out.println("星期三");
                break;
            case DateTimeConstants.THURSDAY:
                System.out.println("星期四");
                break;
            case DateTimeConstants.FRIDAY:
                System.out.println("星期五");
                break;
            case DateTimeConstants.SATURDAY:
                System.out.println("星期六");
                break;
        }

        //⑨取特殊日期
        //月末日期
        DateTime dateTime1 = now.dayOfMonth().withMaximumValue();
        System.out.println("月末日期:\t"+dateTime1);
        //90天后那周的周一
        DateTime dateTime2 = now.plusDays(90).dayOfWeek().withMinimumValue();
        System.out.println("90天后那周的周一:\t"+dateTime2);
        //二十四年前七月份的一号的晚上八点
        DateTime writeTime = new DateTime("2019-03-14T14:27:02.279+08:00");
        DateTime dateTime3 = writeTime.minusYears(24).withMonthOfYear(7).dayOfMonth().withMinimumValue().withHourOfDay(20);
        System.out.println("二十四年前七月份的一号的晚上八点:"+dateTime3);
    }


}
