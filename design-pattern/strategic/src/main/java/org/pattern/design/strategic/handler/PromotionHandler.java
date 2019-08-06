package org.pattern.design.strategic.handler;

import org.pattern.design.strategic.entity.OrderDto;
import org.springframework.stereotype.Component;

@Component
@HandlerType("3")
public class PromotionHandler extends AbstractHandler{

    @Override
    public String handle(OrderDto order){
        return "处理促销订单";
    }
}
