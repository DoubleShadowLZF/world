package org.pattern.design.strategic.handler;

import org.pattern.design.strategic.handler.AbstractHandler;
import org.pattern.design.strategic.tools.BeanTool;
import org.springframework.stereotype.Component;

import java.util.Map;

//@Component
public class HandlerContext {
    /**
     * key: type 订单类型
     * value: class 类
     * 通过策略模式，直接使用对应的类
     */
    private Map<String,Class> handlerMap;

    public HandlerContext(Map<String, Class> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public AbstractHandler getInstance(String type){
        Class clazz = handlerMap.get(type);
        if(clazz == null){
            throw new IllegalArgumentException("not found handler for type : " + type);
        }
        return (AbstractHandler) BeanTool.getBean(clazz);
    }
}
