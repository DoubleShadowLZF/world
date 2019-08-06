package common;

import entity.BusiArea;
import entity.BusiTime;

import java.util.List;

/**
 * @Function 规则内容
 * @author Double
 */
public class RuleContext {

    /**
     * 规则内容上下文
     */
    private String context;

    /**
     * 禁止的区域规则
     */
    private List<BusiArea> banAreaRules;

    /**
     * 限制的使用时间
     * @return
     */
    private List<BusiTime> banTimes;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<BusiArea> getBanAreaRules() {
        return banAreaRules;
    }

    public void setBanAreaRules(List<BusiArea> banAreaRules) {
        this.banAreaRules = banAreaRules;
    }

    public List<BusiTime> getBanTimes() {
        return banTimes;
    }

    public void setBanTimes(List<BusiTime> banTimes) {
        this.banTimes = banTimes;
    }
}
