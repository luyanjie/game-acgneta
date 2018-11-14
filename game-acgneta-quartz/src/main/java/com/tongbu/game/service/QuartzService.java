package com.tongbu.game.service;

import com.tongbu.game.entity.JobEntity;
import org.quartz.SchedulerException;

/**
 * @author jokin
 * @date 2018/11/8 17:00
 */
public interface QuartzService {
    /**
     * 循环判断任务添加与更新
     * */
    void initVopVos();

    /**
     * 添加job
     * */
    boolean addJob(JobEntity job) throws SchedulerException;

    /**
     * 获取Job信息
     * */
    JobEntity getJobInfo(String name, String group) throws SchedulerException;

    /**
     * 修改某个任务的执行时间
     * */
    boolean modifyJob(JobEntity job) throws SchedulerException;

    /**
     * 暂定所有的任务
     * */
    void pauseAllJob() throws SchedulerException;

    /**
     * 暂定某个任务
     * */
    void pauseJob(String name, String group) throws SchedulerException;

    /**
     * 恢复所有的任务
     * */
    void resumeAllJob() throws SchedulerException;

    /**
     * 恢复某个指定任务
     */
    void resumeJob(String name, String group) throws SchedulerException;

    /**
     * 删除某个任务
     * */
    boolean deleteJob(String name, String group) throws SchedulerException;
}
