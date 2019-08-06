package guava.common.eventbus;

import com.google.common.eventbus.EventBus;
import guava.common.eventbus.event.BuyEvent;
import guava.common.eventbus.event.SellEvent;
import guava.common.eventbus.event.TradeAccountEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class BuySellTradeExecutor extends SimpleTradeExecutor{

    public BuySellTradeExecutor(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected TradeAccountEvent processTrade(int tradeAccount,double amount,  int tradeType){
        Date executionTime = new Date();
        String message = String.format("Processed trade for %s of amount type %s @ %s",
                tradeAccount,amount,tradeType,executionTime);
        log.debug("{}",message);
        if(tradeType == 0){
//            double amount, Date tradeExecutionTime, int tradeType, int tradeAccount
            BuyEvent buyEvent = new BuyEvent(amount, executionTime, tradeType, tradeAccount);
            log.debug("生成购买事件：{}",buyEvent);
            return buyEvent;
        }else if( tradeType == 1){
            SellEvent sellEvent = new SellEvent(amount, executionTime, tradeType, tradeAccount);
            log.debug("生成销售事件：{}",sellEvent);
            return sellEvent;
        }else{
            throw new RuntimeException("tradeType 只支持 0 或 1");
        }

    }

}
