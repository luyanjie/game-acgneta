package com.tongbu.game.entity.task.act10109;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jokin
 * @date 2018/12/27 10:42
 */
@Data
public class TaskNumEntity implements Serializable {
    private static final long serialVersionUID = -1622893342407102282L;

    private int id;
    /**
     * 任务类型名称可以是表名或者自定义的值
     * */
    private String name;
    /**
     * 当前任务执行的最大值
     * */
    private int maxId;
    private String updateTime;
    /**
     * 1: 执行表的最大值
     * */
    private int taskType;
}
