package entity;

import java.sql.Timestamp;

/**
 * @Function 时间规则基础数据
 * @author Double
 */
public class BusiTime {

    /**
     * 限制开始使用时间
     */
    private Timestamp banStartTime;

    /**
     * 限制结束使用时间
     */
    private Timestamp banEndTime;

    public BusiTime(Timestamp banStartTime, Timestamp banEndTime) {
        this.banStartTime = banStartTime;
        this.banEndTime = banEndTime;
    }

    public Timestamp getBanStartTime() {
        return banStartTime;
    }

    public void setBanStartTime(Timestamp banStartTime) {
        this.banStartTime = banStartTime;
    }

    public Timestamp getBanEndTime() {
        return banEndTime;
    }

    public void setBanEndTime(Timestamp banEndTime) {
        this.banEndTime = banEndTime;
    }
}
