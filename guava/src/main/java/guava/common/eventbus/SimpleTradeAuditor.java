package guava.common.eventbus;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import guava.common.eventbus.event.TradeAccountEvent;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.util.Assert;

import java.util.List;

/**
 * 贸易事件监听器
 */
@Slf4j
public class SimpleTradeAuditor {
    private List<TradeAccountEvent> tradeEvents = Lists.newArrayList();

    public SimpleTradeAuditor(List<TradeAccountEvent> tradeEvents) {
        Assert.notNull(tradeEvents,"tradeEvents不能为空");
        this.tradeEvents = tradeEvents;
    }

    public SimpleTradeAuditor(EventBus eventBus){
        Assert.notNull(eventBus,"eventBus不能为空");
        log.debug("SimpleTradeAuditor 注册进 {}",eventBus);
        eventBus.register(this);
    }

    /**
     * 事件处理器
     * ①将事件加入 tradeEvents ；
     * ②控制台打印。
     * @param tradeAccountEvent
     */
    @Subscribe
    public void auditTrade(TradeAccountEvent tradeAccountEvent){
        tradeEvents.add(tradeAccountEvent);
        log.debug("Received trade:{}",tradeAccountEvent);
    }
}
