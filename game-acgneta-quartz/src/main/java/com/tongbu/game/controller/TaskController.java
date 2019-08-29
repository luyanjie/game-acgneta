package com.tongbu.game.controller;

import com.tongbu.game.entity.JobEntity;
import com.tongbu.game.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jokin
 * @date 2019/1/16 9:59
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    @Qualifier("act10111ServiceImpl")
    @Autowired
    TaskService service10111;

    @RequestMapping("/10111")
    public String act10110()
    {
        service10111.start(new JobEntity());
        return "success";
    }
}
