package com.tongbu.game.controller;

import com.tongbu.game.entity.JobEntity;
import com.tongbu.game.service.QuartzService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vip.xinba.core.ResponseUtil;
import vip.xinba.core.entity.MessageResponse;

/**
 * @author jokin
 * @date 2018/11/14 15:47
 */
@RestController
@RequestMapping("/quartz")
public class QuartzController {

    @Autowired
    QuartzService quartzService;

    /**
     * 添加某个任务
     */
    @RequestMapping("/add")
    public MessageResponse add(String jobName) throws SchedulerException {
        JobEntity job = quartzService.selectOne(jobName);
        if (job == null || job.getId() <= 0 || StringUtils.isAnyEmpty(job.getJobName(), job.getJobGroup())) {
            return ResponseUtil.error("00001", "jobName is not exists");
        }
        return ResponseUtil.success(quartzService.addJob(job));
    }

    /**
     * 获取指定任务
     *
     * @param name  任务名称
     * @param group 任务所属组
     */
    @RequestMapping(value = "/get")
    public MessageResponse getJobInfo(String name, String group) throws SchedulerException {
        return ResponseUtil.success(quartzService.getJobInfo(name, group));
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public MessageResponse postJobInfo(String name, String group) throws SchedulerException {
        return ResponseUtil.success(quartzService.getJobInfo(name, group));
    }

    /**
     * 暂停指定任务
     *
     * @param name  任务名称
     * @param group 任务所属组
     */
    @RequestMapping("/pause")
    public MessageResponse pauseJob(String name, String group) throws SchedulerException {
        quartzService.pauseJob(name, group);
        return ResponseUtil.success(true);
    }

    /**
     * 恢复指定任务
     *
     * @param name  任务名称
     * @param group 任务所属组
     */
    @RequestMapping("/resume")
    public MessageResponse resume(String name, String group) throws SchedulerException {
        quartzService.resumeJob(name, group);
        return ResponseUtil.success(true);
    }

    /**
     * 删除指定任务
     *
     * @param name  任务名称
     * @param group 任务所属组
     */
    @RequestMapping("/delete")
    public MessageResponse deleteJob(String name, String group) throws SchedulerException {
        return ResponseUtil.success(quartzService.deleteJob(name, group));
    }

    /**
     * 正在执行的任务
     */
    @RequestMapping("/executing")
    public MessageResponse getCurrentlyExecutingJobs() throws SchedulerException {
        return ResponseUtil.success(quartzService.getCurrentlyExecutingJobs());
    }
}
