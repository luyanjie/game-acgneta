package com.tongbu.game.service.handler;

import com.tongbu.game.common.ClassScaner;
import com.tongbu.game.common.TaskHandlerType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@SuppressWarnings("unchecked")
public class TaskHandlerProcessor implements BeanFactoryPostProcessor {

    private static final String HANDLER_PACKAGE = "com.tongbu.game.service.task";

    /**
     * 扫描@TaskHanderType 初始化TaskHandlerContext，将其注册到spring 容器中
     *
     * @param beanFactory beanFactory
     * @throws BeansException exception
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String, Class> handlerMap = new HashMap<>();
        ClassScaner.scan(HANDLER_PACKAGE, TaskHandlerType.class).forEach(clazz -> {
            // 获取注解中的类型值
            String type = clazz.getAnnotation(TaskHandlerType.class).value();
            // 将注解中的类型值作为key，对应的类作为value，保存到map中
            handlerMap.put(type, clazz);
        });
        // 初始化TaskHandlerContext，将其注册到spring容器中
        TaskHandlerContext context = new TaskHandlerContext(handlerMap);
        beanFactory.registerSingleton(TaskHandlerContext.class.getName(), context);
    }
}
