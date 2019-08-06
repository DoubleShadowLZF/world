package org.springboot.distribute;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimePrinter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springboot.distribute.bitmap.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SignInServiceTest {

    @Autowired
    private CheckInService checkInService;


    Long userId = 19950701L;

    @Test
    public void test(){
        checkInService.checkIn(userId);
        boolean checkIn = checkInService.isCheckIn(userId, "20190630");
        Assert.assertEquals(true,checkIn);
        Long dateCount = checkInService.countDateCheckIn("20190630");
        log.info("dateCount:{}",dateCount);
        Long countIn = checkInService.countCheckIn(userId, "20190530", "20190714");
        log.info("{}",countIn);
    }

    @Test
    public void test01(){
        checkInService.updateContinuousCheckIn(userId);
        Long continuousCheckIn = checkInService.getContinuousCheckIn(userId);
        log.info("{}连续签到天数：{}",userId,continuousCheckIn);
    }

    @Test
    public void timeTest(){
        String pattern = "yyyyMMdd";
        DateTime parse = DateTime.parse("20190701", DateTimeFormat.forPattern(pattern));
        System.out.println(parse.toString("yyyy-MM-dd"));

    }


}
