package com.tongbu.game.service;

import com.alibaba.fastjson.JSON;
import com.tongbu.game.entity.JobEntity;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jokin
 * @date 2018/11/8 17:01
 * 需要知道的几个概念
 * 1. Job：是一个接口，只有一个方法void execute(JobExecutionContext context)，开发者实现该接口定义运行任务，JobExecutionContext类提供了调度上下文的各种信息。
 *      Job运行时的信息保存在JobDataMap实例中；
 * 2. JobDetail：Quartz在每次执行Job时，都重新创建一个Job实例，所以它不直接接受一个Job的实例，相反它接收一个Job实现类，以便运行时通过newInstance()的反射机制实例化Job。
 *      因此需要通过一个类来描述Job的实现类及其它相关的静态信息，如Job名字、描述、关联监听器等信息，JobDetail承担了这一角色。
 * 3. Trigger：是一个类，描述触发Job执行的时间触发规则。主要有SimpleTrigger和CronTrigger这两个子类。当仅需触发一次或者以固定时间间隔周期执行，SimpleTrigger是最适合的选择；
 *      而CronTrigger则可以通过Cron表达式定义出各种复杂时间规则的调度方案：如每早晨9:00执行，周一、周三、周五下午5:00执行等；
 * 4. Calendar：org.quartz.Calendar和java.util.Calendar不同，它是一些日历特定时间点的集合（可以简单地将org.quartz.Calendar看作java.util.Calendar的集合——
 *      java.util.Calendar代表一个日历时间点，无特殊说明后面的Calendar即指org.quartz.Calendar）。一个Trigger可以和多个Calendar关联，以便排除或包含某些时间点。
 *      假设，我们安排每周星期一早上10:00执行任务，但是如果碰到法定的节日，任务则不执行，这时就需要在Trigger触发机制的基础上使用Calendar进行定点排除。
 * 5. Scheduler：代表一个Quartz的独立运行容器，Trigger和JobDetail可以注册到Scheduler中，两者在Scheduler中拥有各自的组及名称，组及名称是Scheduler查找定位容器中某一对象的依据，
 *      Trigger的组及名称必须唯一，JobDetail的组和名称也必须唯一（但可以和Trigger的组和名称相同，因为它们是不同类型的）。Scheduler定义了多个接口方法，
 *      允许外部通过组及名称访问和控制容器中Trigger和JobDetail。
 */
@Service
@Slf4j
@EnableScheduling
public class QuartzServiceImpl implements QuartzService {

    @Autowired
    private Scheduler scheduler;

    private static List<JobEntity> JOBLIST;

    static {
        JOBLIST = getJobs();
    }

    /**
     * 定时执行任务
     * 每隔10s查库，并根据查询结果决定是否重新设置定时任务
     */
    @Override
    //@Scheduled(fixedRate = 10000)
    public void initVopVos() {
        // 获取任务列表，可以把任务写入到数据库
        List<JobEntity> list = JOBLIST;

        System.out.println("任务列表：" + JSON.toJSONString(list));

        list.forEach(job -> {
            try {
                addJob(job);
            } catch (SchedulerException e) {
                log.error("添加，更新任务异常！", e);
            }
        });
    }

    /**
     * 添加job
     */
    @Override
    public boolean addJob(JobEntity job) throws SchedulerException {
        // 判断原有job是否存在，不存在添加，存在变更
        if (job == null) {
            return false;
        }
        TriggerKey triggerKey = new TriggerKey(job.getJobName(), job.getJobGroup());
        //获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
        // CronTrigger可以通过Cron表达式定义出各种复杂时间规则的调度方案：如每早晨9:00执行，周一、周三、周五下午5:00执行等；
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 当仅需触发一次或者以固定时间间隔周期执行，SimpleTrigger是最适合的选择
        // SimpleTrigger simple = (SimpleTrigger)scheduler.getTrigger(triggerKey);


        //不存在，创建一个
        if (null == trigger) {
            JobDetail jobDetail = JobBuilder.newJob(QuartzInitVopVosFactory.class)
                    .withIdentity(job.getJobName(), job.getJobGroup())
                    .withDescription(job.getDescription())
                    .build();
            jobDetail.getJobDataMap().put("job", job);

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
            //按新的cronExpression表达式构建一个新的trigger
            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);
            return true;
        }
        // 获取原有的任务时间
        Date date = null;
        String oldTime = trigger.getCronExpression();
        // 强制更新或者任务执行时间变更时进行更新任务
        if (job.getUpdateNow() == 1 || !oldTime.equals(job.getCron())) {
            // Trigger已存在，那么更新相应的定时设置(并且设定需要更新任务)
            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
            //按新的cronExpression表达式重新构建trigger
            // scheduler.rescheduleJob，如果服务器当前时间与你的表达式配置的执行时间差在两小时以内时，动态修改就会出现立即执行的情况。所以这里设置执行时间从当前时间开始.主要startAt
            trigger = trigger.getTriggerBuilder().startAt(new Date())
                    .withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder)
                    // 设置优先级
                    .withPriority(1)
                    .withDescription(job.getDescription())
                    .build();
            //scheduler.rescheduleJob如果服务器当前时间与你的表达式配置的执行时间差在两小时以内时，
            //动态修改就会出现立即执行的情况。所以这里设置执行时间从当前时间开始
            //重新获取JobDataMap，并且更新参数
            JobDataMap jobDataMap = trigger.getJobDataMap();
            jobDataMap.put("job", job);
            //按新的trigger重新设置job执行
            date = scheduler.rescheduleJob(triggerKey, trigger);
        }
        return date != null;
    }

    /**
     * 获取Job信息
     *
     * @param name  任务名称
     * @param group 任务所属组
     */
    @Override
    public JobEntity getJobInfo(String name, String group) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (trigger == null) {
            return null;
        }
        JobDataMap jobDataMap = trigger.getJobDataMap();
        return (JobEntity) jobDataMap.get("job");
        /*return  String.format("time:%s,state:%s", trigger.getCronExpression(),
                scheduler.getTriggerState(triggerKey).name());*/
    }

    /**
     * 判断任务是否存在
     *
     * @param name  任务名称
     * @param group 任务分组
     */
    @Override
    public boolean exists(String name, String group) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(name, group);
        return scheduler.checkExists(triggerKey);
    }

    /**
     * 修改某个任务的执行时间
     *
     * @param job 任务对象
     */
    @Override
    public boolean modifyJob(JobEntity job) throws SchedulerException {
        if (job == null) {
            return false;
        }
        Date date = null;
        TriggerKey triggerKey = new TriggerKey(job.getJobName(), job.getJobGroup());
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (cronTrigger == null) {
            return false;
        }
        String oldTime = cronTrigger.getCronExpression();
        if (!oldTime.equalsIgnoreCase(job.getCron())) {
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(job.getJobName(), job.getJobGroup())
                    .withDescription(job.getDescription())
                    .withSchedule(cronScheduleBuilder).build();
            date = scheduler.rescheduleJob(triggerKey, trigger);
        }
        return date != null;
    }

    /**
     * 暂定所有的任务
     */
    @Override
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 暂停某个任务
     */
    @Override
    public void pauseJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复所有任务
     */
    @Override
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }

    /**
     * 恢复某个任务
     *
     * @param name  任务名称
     * @param group 任务分类
     */
    @Override
    public void resumeJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除某个任务
     *
     * @param name  任务名称
     * @param group 任务分类
     */
    @Override
    public boolean deleteJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);

        return jobDetail != null && scheduler.deleteJob(jobKey);
    }

    private static List<JobEntity> getJobs() {
        List<JobEntity> list = new ArrayList<>();
        list.add(new JobEntity(1, "a", "kk", 1, "5 0/1 * * * ? * ", "任务 a：每分钟的第5s执行"));
        list.add(new JobEntity(2, "b", "kk", 0, "10 0/1 * * * ? *", "任务 b：每分钟的第10s执行"));
        list.add(new JobEntity(3, "c", "kk", 0, "15 0/1 * * * ? *", "任务 c：每分钟的第15s执行"));
        return list;
    }
}
