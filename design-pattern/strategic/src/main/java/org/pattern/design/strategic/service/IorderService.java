package org.pattern.design.strategic.service;

import org.pattern.design.strategic.entity.OrderDto;

public interface IorderService {

    /**
     * 根据订单的不同类型作出不同的处理
     */
    String handle(OrderDto order);
}
