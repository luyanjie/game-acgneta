package com.tongbu.game.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jokin
 * @date 2018/11/8 16:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity implements Serializable {
    private static final long serialVersionUID = -6067429687525378263L;
    private int id;
    private String jobName;
    private String jobGroup;
    /**
     * 1：（已经存在的任务）需要更新任务 0：不需要
     * */
    private int updateNow;

    /**
     * 任务计划 执行时间表达式
     * */
    private String cron;
    /**
     * 任务描述
     * */
    private String description;

    /**
     * 执行指定的jar包文件
     */
    //private String path;
}
