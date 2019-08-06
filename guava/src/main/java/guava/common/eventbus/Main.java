package guava.common.eventbus;

import com.google.common.eventbus.EventBus;
import guava.common.eventbus.event.TradeAccountEvent;
import org.junit.Test;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        BuySellTradeExecutor executor = init();

        executor.executeTrade(0, 100.0, 1);
    }

    /**
     * 初始化，可以使用Spring容器管理
     * @return
     */
    private static BuySellTradeExecutor init() {
        EventBus eventBus = new EventBus("auditorEventBus");

        //将 BuyEvent 和 SellEvent 事件执行器注册到事件总线上
        new AllTradeAuditor(eventBus);

        //将贸易基本事件注册到事件总线上
        new SimpleTradeAuditor(eventBus);

        //将 DeadEvent 事件注册到事件总线上
        new DeadEventSubscriber(eventBus);

//        SimpleTradeExecutor executor = new SimpleTradeExecutor(eventBus);
        return new BuySellTradeExecutor(eventBus);
    }

    @Test
    public void test(){
        EventBus eventBus = new EventBus("bus");
        SimpleTradeAuditor SimpleTradeAuditor = new SimpleTradeAuditor(eventBus);
//        double amount, Date tradeExecutionTime, int tradeType, int tradeAccount
        TradeAccountEvent event = new TradeAccountEvent(100.0,new Date(),0,1);
        eventBus.post(event);

    }
}
