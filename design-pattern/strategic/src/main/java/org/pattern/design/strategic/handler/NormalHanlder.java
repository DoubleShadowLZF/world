package org.pattern.design.strategic.handler;

import org.pattern.design.strategic.entity.OrderDto;
import org.springframework.stereotype.Component;

@Component
@HandlerType("1")
public class NormalHanlder extends AbstractHandler {

    @Override
    public String handle(OrderDto order) {
        return "处理普通订单";
    }
}
