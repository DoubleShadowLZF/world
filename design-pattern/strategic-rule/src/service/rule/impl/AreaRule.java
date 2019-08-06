package service.rule.impl;

import common.RuleContext;
import entity.BusiArea;
import service.rule.Rule;

import java.util.*;

/**
 * @Function 区域规则
 * @author Double
 */
public class AreaRule implements Rule {

    private static List<BusiArea> areas = null;

    static {
        areas = Arrays.asList(
                new BusiArea("江西","赣州"),
                new BusiArea("广州"),
                new BusiArea("江门")
                );
    }

    /**
     * 区域规则校验
     */
    @Override
    public boolean verify(RuleContext context) {
        System.out.print(">>>区域规则校验：");
        //获取禁用的规则信息
        List<BusiArea> banAreaRules = context.getBanAreaRules();
        for (int i = 0; i < banAreaRules.size(); i++) {
            BusiArea busiArea = banAreaRules.get(i);
            for (int j = 0; j < areas.size(); j++) {
                if(busiArea.getCountry().equals(areas.get(j).getCountry())
                        &&busiArea.getProvince().equals(areas.get(j).getProvince())
                        && busiArea.getCity().equals(areas.get(j).getCity())
                ) {
                    //匹配到一条禁用的规则，则认证失败
                    System.out.println("失败");
                    return false;
                }

            }
        }
        //没有匹配到禁用规则，则认证成功
        System.out.println("成功");
        return true;
    }
}
