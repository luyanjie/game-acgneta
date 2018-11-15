package com.tongbu.game.service.task;

import com.tongbu.game.entity.JobEntity;

/**
 * @author jokin
 * @date 2018/11/14 14:02
 */
public interface TaskService {
    /**
     * 项目启动
     * @param job 执行工作
     * */
    void start(JobEntity job);
}
