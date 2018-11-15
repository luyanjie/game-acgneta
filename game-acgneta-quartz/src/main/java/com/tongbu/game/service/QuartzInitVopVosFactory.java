package com.tongbu.game.service;

import com.alibaba.fastjson.JSON;
import com.tongbu.game.config.ApplicationContextProvider;
import com.tongbu.game.entity.JobEntity;
import com.tongbu.game.service.task.TaskService;
import com.tongbu.game.service.task.act10107.Act10107ServiceImpl;
import org.quartz.*;

import java.util.HashMap;


/**
 * @author jokin
 * @date 2018/11/9 0009 0:01
 * {@link DisallowConcurrentExecution} :保证上一次任务执行完毕，再执行下一次任务
 * 在任务类上注解@DisallowConcurrentExecution，比如此任务需耗时7秒，却配置5秒执行一次，注解后将会7秒才运行一次：
 */
@DisallowConcurrentExecution
public class QuartzInitVopVosFactory implements Job {

    private static final HashMap<String,TaskService> ACTIONS = new HashMap<>();

    static {
        add();
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

       /* //这里写job代码，就是这个任务，具体要实现什么，就在这里写
        JobEntity jobBean = (JobEntity) context.getMergedJobDataMap().get("job");
        System.out.println("任务执行:" + JSON.toJSONString(jobBean));
        //上面这句比较坑,必须用getMergedJobDataMap，不然获取的是一个list<map>对象。不好解析，
        //所有的参数以及其他信息都在JobExecutionContext
        //顺带提一句，如果你没有JobFactory 这个类，在这里是没办法注入任何类的。
        //JobEntity是实体类，

        JobKey jobKey = context.getJobDetail().getKey();
        System.out.println("name :"+jobKey.getName() + " group:"+jobKey.getGroup());*/

        JobEntity jobBean = (JobEntity) context.getMergedJobDataMap().get("job");

        System.out.println(JSON.toJSONString(jobBean));

        if (jobBean == null) {
            return;
        }

        TaskService task = ACTIONS.getOrDefault(jobBean.getJobName(),null);
        if(task== null){return;}
        task.start(jobBean);

    }

    private static void add(){
        ACTIONS.put("10107", (Act10107ServiceImpl)ApplicationContextProvider.getBean(Act10107ServiceImpl.class));
    }
}
