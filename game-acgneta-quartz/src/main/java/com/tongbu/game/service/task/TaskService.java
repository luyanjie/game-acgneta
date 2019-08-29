package com.tongbu.game.service.task;

import com.tongbu.game.entity.JobEntity;

/**
 * @author jokin
 * @date 2018/11/14 14:02
 */
public interface TaskService {
    default void start(JobEntity job){}
    default void execute(){}
}
