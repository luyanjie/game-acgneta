package com.tongbu.game.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author jokin
 * @date 2018/11/15 10:20
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 需要Component注解，不然在启动的时候不会调用这个get方法
        ApplicationContextProvider.applicationContext = applicationContext;
    }

    /**
     * 获取applicationContext
     * */
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * 通过name获取Bean
     * */
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     * */
    public static Object getBean(Class<?> clazz){
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过class获取Bean.
     * */
    public static <T> T getBeanClass(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     * */
    public static Object getBean(String name,Class<?> clazz){
        return getApplicationContext().getBean(name,clazz);
    }
}
