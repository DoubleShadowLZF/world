package service.rule;

import common.RuleContext;

/**
 * 规则
 */
public interface Rule {

    /**
     * 校验
     */
    boolean verify(RuleContext context);
}
