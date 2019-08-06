package org.pattern.design.strategic.service.impl;

import org.pattern.design.strategic.handler.HandlerContext;
import org.pattern.design.strategic.entity.OrderDto;
import org.pattern.design.strategic.handler.AbstractHandler;
import org.pattern.design.strategic.service.IorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("orderService")
public class StrategicOrderServiceImpl implements IorderService {

    /**
     * 此处不是使用注解注入该对象，因此IDEA会报错，可以忽略。
     */
    @Autowired
    private HandlerContext handlerContext;

    @Override
    public String handle(OrderDto order) {
        AbstractHandler handler = handlerContext.getInstance(order.getType());
        return handler.handle(order);
    }
}
