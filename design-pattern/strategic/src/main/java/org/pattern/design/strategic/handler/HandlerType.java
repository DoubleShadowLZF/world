package org.pattern.design.strategic.handler;

import java.lang.annotation.*;

/**
 * 标志处理器对应哪个订单类型
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HandlerType {
    String value();
}
