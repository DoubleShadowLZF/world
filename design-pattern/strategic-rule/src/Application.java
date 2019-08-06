import common.RuleContext;
import entity.BusiArea;
import entity.BusiTime;
import service.buzi.BusiAuth;
import tools.ScanPackageTool;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Double
 * @Function TODO
 */
public class Application {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {

        //初始化
        RuleContext context = new RuleContext();
        context.setBanAreaRules(Arrays.asList(
//                new BusiArea("广州")
                new BusiArea("佛山")
        ));
        context.setBanTimes(Arrays.asList(
                new BusiTime(
                        new Timestamp(new Date(1995,07,01).getTime()),
                        new Timestamp(new Date(2019,8,06).getTime())
                )
        ));

        List<Object> authes = ScanPackageTool.scanRetObj("service.buzi.impl");
        for (Object auth : authes) {
            BusiAuth busiAuth = (BusiAuth) auth;
            boolean authFlag = busiAuth.auth(context);
            if(!authFlag){
                throw new RuntimeException("认证失败");
            }
        }

        System.out.println("认证成功");
    }


}
