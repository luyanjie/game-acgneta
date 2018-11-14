package com.tongbu.game.controller;

import com.tongbu.game.common.ResponseUtil;
import com.tongbu.game.entity.JobEntity;
import com.tongbu.game.entity.MessageResponse;
import com.tongbu.game.service.QuartzService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jokin
 * @date 2018/11/14 15:47
 */
@RestController
@RequestMapping("/quartz")
public class QuartzController {

    @Autowired
    QuartzService quartzService;


    @RequestMapping("/add")
    public MessageResponse add() throws SchedulerException {
        JobEntity jobEntity = new JobEntity(1, "10107", "acgneta", 0, "0/10 * * * * ? *", "获取A站B站视频播放量");
        return ResponseUtil.success(quartzService.addJob(jobEntity));
    }
}
