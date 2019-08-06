package guava.common.eventbus;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import guava.common.eventbus.event.BuyEvent;
import guava.common.eventbus.event.SellEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 贸易审计类
 * 注册销售和购买事件
 */
@Slf4j
public class AllTradeAuditor {
    @Getter
    private List<BuyEvent> buyEvents = Lists.newArrayList();
    @Getter
    private List<SellEvent> sellEvents = Lists.newArrayList();

    public AllTradeAuditor(EventBus eventBus){
        log.debug("AllTradeAuditor 注册进 {}",eventBus);
        eventBus.register(this);
    }

    @Subscribe
    public void auditSell(SellEvent sellEvent) {
        log.debug("Received TradeSellEvent:{}",sellEvent);
        this.sellEvents.add(sellEvent);
    }

    @Subscribe
    public void auditBuy(BuyEvent buyEvent) {
        log.debug("Received TradeBuyEvent:{}",buyEvent);
        this.buyEvents.add(buyEvent);
    }

}
