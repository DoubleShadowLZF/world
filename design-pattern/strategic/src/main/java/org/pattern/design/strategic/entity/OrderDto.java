package org.pattern.design.strategic.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDto {
    private String code;
    private BigDecimal price;

    /**
     * 订单类型：
     * 1.普通订单；
     * 2.团购订单；
     * 3.促销订单；
     */
    private String type;
}
