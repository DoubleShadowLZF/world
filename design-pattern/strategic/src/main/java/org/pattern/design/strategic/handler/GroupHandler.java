package org.pattern.design.strategic.handler;

import org.pattern.design.strategic.entity.OrderDto;
import org.springframework.stereotype.Component;

@Component
@HandlerType("2")
public class GroupHandler extends AbstractHandler {

    @Override
    public String handle(OrderDto order) {
        return "处理团购订单";
    }
}
