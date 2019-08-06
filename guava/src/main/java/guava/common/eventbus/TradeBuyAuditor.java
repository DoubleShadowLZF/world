package guava.common.eventbus;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import guava.common.eventbus.event.BuyEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Deprecated
public class TradeBuyAuditor {
    private List<BuyEvent> buyEvents  = Lists.newArrayList();
    public TradeBuyAuditor(EventBus eventBus){
        eventBus.register(this);
    }

    @Subscribe
    public void auditBuy(BuyEvent buyEvent){
        buyEvents.add(buyEvent);
        log.debug("Received TradeBuyEvent：{}",buyEvent);
    }

    public List<BuyEvent> getBuyEvents(){
        return buyEvents;
    }
}
