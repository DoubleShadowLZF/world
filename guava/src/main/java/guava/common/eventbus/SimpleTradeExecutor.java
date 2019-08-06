package guava.common.eventbus;

import com.google.common.eventbus.EventBus;
import guava.common.eventbus.event.TradeAccountEvent;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.util.Assert;

import java.util.Date;

@Slf4j
public class SimpleTradeExecutor {
    private EventBus eventBus;

    public SimpleTradeExecutor(EventBus eventBus) {
        Assert.notNull(eventBus,"eventBus不能为空");
        this.eventBus = eventBus;
    }

    /**
     * 指定贸易事件
     * @param tradeAccount
     * @param amount
     * @param tradeType
     */
    public void executeTrade(int tradeAccount, double amount, int tradeType) {
        TradeAccountEvent event = processTrade(tradeAccount, amount, tradeType);
        log.debug("事件{}注册进 EventBus{}",event,this.eventBus);

        log.debug("执行贸易事件，{}",event);
        eventBus.post(event);
    }

    /**
     * 根据输入输入参数生成贸易事件（销售事件或购买事件）
     * @param tradeAccount
     * @param amount
     * @param tradeType
     * @return
     */
    protected TradeAccountEvent processTrade(int tradeAccount, double amount, int tradeType) {
        Date executionTime = new Date();
        String message = String.format("Processed trade for %s of amount type %s @%s", tradeAccount, amount, tradeType, executionTime);
        log.debug("{}", message);

        //重构
        TradeAccountEvent event = new TradeAccountEvent(amount,executionTime,tradeType,tradeAccount);
        return event;
    }
}
