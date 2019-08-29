package com.tongbu.game.entity.task.act10106;

import lombok.Data;

import java.io.Serializable;

@Data
public class AnimationsUpdatePlanEntity implements Serializable {
    private static final long serialVersionUID = -8647525456856548145L;

    /**
     * 任务id
     */
    private int id;
    /**
     * 动画id
     */
    private int animationId;
    /**
     * 播放源
     */
    private String platformCode;
    /**
     * 请求地址
     */
    private String url;
    /**
     * 下次更新时间
     */
    private String nextUpdateDate;
    /**
     * 更新间隔时间
     */
    private int intervalDay;
    /**
     * 最后集数
     */
    private String lastEpisode;
    /**
     * 最后更新时间
     */
    private String lastUpdateTime;
    /**
     * 对应请求对方api的一些额外参数列表，使用json格式
     */
    private String urlApiParamter;
}
