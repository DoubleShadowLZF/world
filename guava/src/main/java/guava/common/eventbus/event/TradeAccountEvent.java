package guava.common.eventbus.event;

import lombok.Data;

import java.util.Date;

/**
 * 贸易基础事件
 */
@Data
public class TradeAccountEvent {
    private double amount;
    private Date tradeExecutionTime;
    private int tradeType;
    private int tradeAccount;

    public TradeAccountEvent(double amount, Date tradeExecutionTime, int tradeType, int tradeAccount) {
        this.amount = amount;
        this.tradeExecutionTime = tradeExecutionTime;
        this.tradeType = tradeType;
        this.tradeAccount = tradeAccount;
    }
}
