package com.tongbu.game.entity.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jokin
 * @date 2018/11/14 14:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Act10107UrlEntity implements Serializable {

    private static final long serialVersionUID = 267605883911336192L;

    private int id;
    /**
     * 视频来源平台 A站  还是B站
     * */
    private String vedioSource;
    /**
     * 名称 视频
     * */
    private String name;
    /**
     * 抓取的链接
     * */
    private String url;
    /**
     * 根据url获得的id
     * */
    private int pageUrlId;

    private String insertTime;
    /**
     * 1:有效 0：无效
     * */
    private int status;
}
