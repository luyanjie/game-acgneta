package com.tongbu.game.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import com.tongbu.game.service.UmengiOSServiceImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author jokin
 * @date 2018/10/9 16:21
 *
 * 实现AsyncConfigurer接口对异常线程池更加细粒度的控制
 * EnableAsync  启动异步调用
 * 自定义异步调用相关配置 @Async {@link UmengiOSServiceImpl}
 */

@Configuration
@EnableAsync
public class MyAsyncConfigurer implements AsyncConfigurer {

    private final static Logger logger = LoggerFactory.getLogger(MyAsyncConfigurer.class);

    /**
     * 创建线程自己的线程池
     * */
    @Override
    public Executor getAsyncExecutor(){
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(5);
        threadPool.setMaxPoolSize(10);
        threadPool.setWaitForTasksToCompleteOnShutdown(true);
        threadPool.setAwaitTerminationSeconds(60 * 15);
        threadPool.setThreadNamePrefix("MyAsync-");
        threadPool.initialize();
        return threadPool;
    }

    /**
     * 自定义异常处理类
     * .对void方法抛出的异常处理的类AsyncUncaughtExceptionHandler
     * 返回Future的无法在这捕捉
     * */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler()
    {
        return (Throwable throwable, Method method, Object... objects) -> {
            JSONObject object = new JSONObject();
            object.put("Exception StackTrac", Arrays.toString(throwable.getStackTrace()));
            object.put("Exception message",throwable.getMessage());
            object.put("Method name",method.getName());

            // 获取参数 类型 值 记录
            List<JSONObject> jasonParameter = new ArrayList<>();
            ParameterNameDiscoverer parameterNameDiscoverer =
                    new LocalVariableTableParameterNameDiscoverer();
            String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
            Class<?>[] classes = method.getParameterTypes();
            for (int i = 0; i < parameterNames.length; i++) {
                JSONObject json = new JSONObject();
                json.put("type", classes[i].getName());
                json.put("name", parameterNames[i]);
                json.put("value", objects[i]);
                jasonParameter.add(json);
            }
            object.put("Parameter",jasonParameter);

            logger.error(JSON.toJSONString(object));
        };
    }
}
