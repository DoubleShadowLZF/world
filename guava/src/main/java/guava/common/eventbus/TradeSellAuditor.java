package guava.common.eventbus;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import guava.common.eventbus.event.SellEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Deprecated
public class TradeSellAuditor {
    private List<SellEvent> sellEvents = Lists.newArrayList();

    public TradeSellAuditor(List<SellEvent> sellEvents) {
        this.sellEvents = sellEvents;
    }

    @Subscribe
    public void auditSell(SellEvent sellEvent){
        sellEvents.add(sellEvent);
        log.debug("{}",sellEvent);
    }

    public List<SellEvent> getSellEvents(){
        return sellEvents;
    }
}
