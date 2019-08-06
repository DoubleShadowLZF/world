package service.buzi.impl;

import common.RuleContext;
import service.buzi.BusiAuth;
import service.rule.Rule;
import tools.ScanPackageTool;

import java.util.ArrayList;
import java.util.List;

/**
 * @Function 规则认证
 * @author Double
 */
public class RuleAuth implements BusiAuth {

    /**
     * 所有的规则
     */
    private List<Rule> rules = new ArrayList<>();

    private void init() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<Object> objs = ScanPackageTool.scanRetObj("service.rule.impl");
        for (Object obj : objs) {
            rules.add((Rule) obj);
        }
    }

    /**
     * 规则认证
     * @param context 上下文信息
     * @return
     */
    @Override
    public boolean auth(RuleContext context) {
        System.out.println("规则认证");
        //初始化验证的规则
        try {
            init();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        for ( Rule rule : rules ) {
            boolean verifyFlag = rule.verify(context);
            if(!verifyFlag){
                return false ;
            }
        }
        return true;
    }
}
