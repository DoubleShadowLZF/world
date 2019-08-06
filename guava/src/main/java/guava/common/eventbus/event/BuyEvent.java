package guava.common.eventbus.event;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 购买事件
 */
@Slf4j
public class BuyEvent extends TradeAccountEvent {
    public BuyEvent(double amount, Date tradeExecutionTime, int tradeType, int tradeAccount) {
        super(amount, tradeExecutionTime, tradeType, tradeAccount);
        log.debug("购买事件");
    }
}
