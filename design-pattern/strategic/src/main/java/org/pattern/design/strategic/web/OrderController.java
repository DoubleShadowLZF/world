package org.pattern.design.strategic.web;

import org.pattern.design.strategic.entity.OrderDto;
import org.pattern.design.strategic.service.IorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private IorderService orderService;

    @GetMapping("/order")
    public String order(OrderDto order){
        return orderService.handle(order);
    }

}
