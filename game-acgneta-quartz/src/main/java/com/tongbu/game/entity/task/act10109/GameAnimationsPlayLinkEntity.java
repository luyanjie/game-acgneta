package com.tongbu.game.entity.task.act10109;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jokin
 * @date 2018/12/27 10:53
 */
@Data
public class GameAnimationsPlayLinkEntity implements Serializable {
    private static final long serialVersionUID = 836130366271920953L;

    private int id;
    /**
     * 动画id
     * */
    private int  animationId;
    /**
     * 抓取的平台代号
     * */
    private String platformCode;
    /**
     * 抓取的平台名称
     * */
    private String platformName;
    /**
     * 抓取的集数
     * */
    private String episode;
}
