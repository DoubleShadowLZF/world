package org.pattern.design.strategic.handler;

import org.pattern.design.strategic.entity.OrderDto;

/**
 * 抽象处理器
 */
public abstract class AbstractHandler {
    abstract public String handle(OrderDto order);
}
