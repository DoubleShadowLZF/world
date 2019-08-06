package guava.common.eventbus;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public class DeadEventSubscriber {

    public DeadEventSubscriber(EventBus eventBus) {
        log.debug("DeadEventSubscriber 注册进 {}",eventBus);
        eventBus.register(this);
    }

    @Subscribe
    public void handleUnsubscribedEvent(DeadEvent deadEvent){
        log.warn("No subscribers for {}",deadEvent.getEvent());
    }

}
