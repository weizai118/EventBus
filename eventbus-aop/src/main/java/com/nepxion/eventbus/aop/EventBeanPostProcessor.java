package com.nepxion.eventbus.aop;

/**
 * <p>Title: Nepxion EventBus</p>
 * <p>Description: Nepxion EventBus AOP</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.nepxion.eventbus.annotation.EventBus;
import com.nepxion.eventbus.core.EventControllerFactory;

public class EventBeanPostProcessor implements BeanPostProcessor {
    @Autowired
    private EventControllerFactory eventControllerFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(EventBus.class)) {
            EventBus eventBusAnnotation = bean.getClass().getAnnotation(EventBus.class);
            String identifier = eventBusAnnotation.identifier();
            boolean async = eventBusAnnotation.async();

            eventControllerFactory.getController(identifier, async).register(bean);
        }

        return bean;
    }
}
