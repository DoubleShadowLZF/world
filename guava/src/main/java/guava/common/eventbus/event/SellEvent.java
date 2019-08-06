package guava.common.eventbus.event;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 销售事件
 */
@Slf4j
public class SellEvent extends TradeAccountEvent {

    public SellEvent(double amount, Date tradeExecutionTime, int tradeType, int tradeAccount) {
        super(amount, tradeExecutionTime, tradeType, tradeAccount);
        log.debug("销售事件");
    }
}
