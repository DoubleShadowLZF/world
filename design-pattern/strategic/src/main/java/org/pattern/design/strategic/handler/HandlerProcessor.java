package org.pattern.design.strategic.handler;

import com.google.common.collect.Maps;
import org.pattern.design.strategic.tools.ClassScaner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 具体思路是：
 * 1.扫描指定包中标有 @HandlerType 的类；
 * 2.将注解中的类型值作为key，对应的类作为value,保存在Map中；
 * 3.以上面的 map 作为构造函数参数，初始化 HandlerContext,
 * 将其注册到 Spring 容器中；
 */
@Component
@SuppressWarnings("unchecked")
public class HandlerProcessor implements BeanFactoryPostProcessor {
    private final static String HANDLER_PACKAGE = "org.pattern.design.strategic";

    /**
     * 扫描 @HandlerTyoe，初始化 HandlerContext, 将其注册到 spring 容器
     *
     * @param configurableListableBeanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        HandlerContext context = getHandlerContext();
        configurableListableBeanFactory.registerSingleton(HandlerContext.class.getName(), context);
    }

    private static HandlerContext getHandlerContext() {
        Map<String, Class> handlerMap = Maps.newHashMapWithExpectedSize(3);
        ClassScaner.scan(HANDLER_PACKAGE, HandlerType.class).forEach(clazz -> {
            //获取注解中的类型值
            String type = clazz.getAnnotation(HandlerType.class).value();
            //将注解中的类型值作为key，对应的类作为value,保存在Map中
            handlerMap.put(type, clazz);
        });
        //初始化 HandlerContext ，将其注册到 spring 容器中
        return new HandlerContext(handlerMap);
    }

/**
 * 上面的代码功能是将 HandlerContext 注入到 Spring 容器中，
 * 可使用以下代码，代替 @Component 将 HandlerContext 注入到 Spring 容器中。
 */
//    @Configuration
//    public static class Register{
//        @Bean
//        public HandlerContext handlerContext(){
//            return getHandlerContext();
//        }
//    }
}
