package com.tongbu.game.service.handler;

import com.alibaba.fastjson.JSON;
import com.tongbu.game.config.ApplicationContextProvider;
import com.tongbu.game.entity.JobEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class TaskHandlerContext {

    private Map<String, Class> handlerMap;

    public TaskHandlerContext(Map<String, Class> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public AbstractHandler getInstance(String type) {
        Class<?> clazz = handlerMap.get(type);
        if (clazz == null) {
            return new AbstractHandler() {
                @Override
                public void start(JobEntity job) {
                    log.warn("type is not find " + JSON.toJSONString(job));
                }
            };
        }
        return (AbstractHandler) ApplicationContextProvider.getBeanClass(clazz);
    }
}
