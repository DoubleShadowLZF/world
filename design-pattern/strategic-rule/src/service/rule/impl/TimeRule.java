package service.rule.impl;

import common.RuleContext;
import entity.BusiTime;
import service.rule.Rule;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Double
 * @Function 时间规则
 */
public class TimeRule implements Rule {

    private static List<BusiTime> times;

    static {
        times = Arrays.asList(
//                new BusiTime(new Timestamp(new Date(2018, 07, 01).getTime()), new Timestamp(new Date(2010, 10, 01).getTime())),
                new BusiTime(new Timestamp(new Date(1995, 07, 01).getTime()), new Timestamp(new Date(2020, 07, 01).getTime()))
//                new BusiTime(new Timestamp(new Date(2010, 07, 01).getTime()), new Timestamp(new Date(2020, 07, 01).getTime()))
        );
    }

    /**
     * 时间校验规则
     * 匹配到上下文中和数据库中的规则，则认证失败，
     * 否则认证成功
     * @param context
     * @return
     */
    @Override
    public boolean verify(RuleContext context) {
        System.out.print(">>>时间校验规则：");
        List<BusiTime> banTimes = context.getBanTimes();
        for (int i = 0; i < banTimes.size(); i++) {
            BusiTime banTime = banTimes.get(i);
            for (int j = 0; j < times.size(); j++) {
                if (banTime.getBanStartTime().getTime() >= times.get(j).getBanStartTime().getTime()
                        && banTime.getBanEndTime().getTime() <= times.get(j).getBanEndTime().getTime()) {
                    System.out.println("失败");
                    return false;
                }
            }
        }
        System.out.println("成功");
        return true;
    }
}
