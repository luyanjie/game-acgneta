package com.tongbu.game.service;

import com.alibaba.fastjson.JSON;
import com.tongbu.game.config.ApplicationContextProvider;
import com.tongbu.game.entity.JobEntity;
import com.tongbu.game.service.handler.AbstractHandler;
import com.tongbu.game.service.handler.TaskHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;


/**
 * @author jokin
 * @date 2018/11/9 0009 0:01
 */
@DisallowConcurrentExecution
@Slf4j
public class QuartzInitVopVosFactory implements Job {

    //private static final HashMap<String, TaskService> ACTIONS = new HashMap<>();

    /*static {
        add();
    }*/

    @Override
    public void execute(JobExecutionContext context) {

        JobEntity jobBean = (JobEntity) context.getMergedJobDataMap().get("job");
        if (jobBean == null) {
            return;
        }

        AbstractHandler handler = ApplicationContextProvider.getBeanClass(TaskHandlerContext.class).getInstance(jobBean.getJobName());
        log.info(JSON.toJSONString(jobBean));
        handler.start(jobBean);

        /*TaskService task = ACTIONS.getOrDefault(jobBean.getJobName(), (obj) -> log.warn("class is not exists. param: {}", JSON.toJSONString(obj)));
        log.info(JSON.toJSONString(jobBean));
        task.start(jobBean);*/
    }

    /*private static void add() {
        ACTIONS.put("10107", ApplicationContextProvider.getBeanClass(Act10107ServiceImpl.class));
        ACTIONS.put("10108", ApplicationContextProvider.getBeanClass(Act10108ServiceImpl.class));
        ACTIONS.put("10109", ApplicationContextProvider.getBeanClass(Act10109ServiceImpl.class));
        ACTIONS.put("10110", ApplicationContextProvider.getBeanClass(Act10110ServiceImpl.class));
        ACTIONS.put("2037", ApplicationContextProvider.getBeanClass(Act2037ServiceImpl.class));
    }*/
}
