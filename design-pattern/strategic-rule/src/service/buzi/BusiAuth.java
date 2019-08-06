package service.buzi;

import common.RuleContext;

/**
 * @Function 规则校验
 * @author Double
 */
public interface BusiAuth {

    /**
     * 规则校验
     * @param context 上下文信息
     * @return true 通过认证 ； false 认证失败
     */
    boolean auth(RuleContext context);


}
