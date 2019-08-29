package com.tongbu.game.aspect;

import com.tongbu.game.common.TargetDataSource;
import com.tongbu.game.common.enums.DataSourceType;
import org.aspectj.lang.reflect.MethodSignature;
import com.tongbu.game.config.JdbcContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author jokin
 * @date 2018/11/19 18:00
 */
@Aspect
@Order(1)
@Component
public class DynamicDataSourceAspect {
    private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Pointcut(value = "execution(* com.tongbu.game.service.task.*.*(..))")
    public void pointCut(){}

    @Before(value = "@annotation(targetDataSource)")
    public void doBefore(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        DataSourceType dataSourceType = targetDataSource.dataSourceType();
        JdbcContextHolder.set(dataSourceType.getName());
    }

    /**
     * 执行方法后清除数据源设置
     *
     * @param joinPoint        切点
     * @param targetDataSource 动态数据源
     */
    @After(value = "@annotation(targetDataSource)")
    public void doAfter(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        log.info(String.format("当前数据源  %s  执行清理方法", targetDataSource.dataSourceType()));
        JdbcContextHolder.clear();
    }

    @Before(value = "pointCut()")
    public void doBeforeWithSlave(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取当前切点方法对象
        Method method = methodSignature.getMethod();
        //判断是否为借口方法
        if (method.getDeclaringClass().isInterface()) {
            try {
                //获取实际类型的方法对象
                method = joinPoint.getTarget().getClass()
                        .getDeclaredMethod(joinPoint.getSignature().getName(), method.getParameterTypes());
            } catch (NoSuchMethodException e) {
                log.error("方法不存在！", e);
            }
        }
        if (null == method.getAnnotation(TargetDataSource.class)) {
            JdbcContextHolder.set(DataSourceType.Master.getName());
        }
    }
}
