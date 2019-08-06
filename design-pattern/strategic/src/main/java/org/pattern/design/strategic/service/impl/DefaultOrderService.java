package org.pattern.design.strategic.service.impl;

import org.pattern.design.strategic.entity.OrderDto;
import org.pattern.design.strategic.service.IorderService;
import org.springframework.stereotype.Service;

/**
 * 传统实现类
 * 使用"if/else"处理逻辑
 */
@Service("defaultOrderService")
public class DefaultOrderService implements IorderService {
    @Override
    public String handle(OrderDto order) {
        String type = order.getType();
        if ("1".equals(type)) {
            return "处理普通订单";
        } else if ("2".equals(type)) {
            return "处理团购订单";
        } else if ("3".equals(type)) {
            return "处理促销订单";
        }
        return null;
    }
}
