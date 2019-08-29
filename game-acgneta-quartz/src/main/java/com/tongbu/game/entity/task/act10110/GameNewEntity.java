package com.tongbu.game.entity.task.act10110;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jokin
 * @date 2019/1/16 9:24
 */
@Data
public class GameNewEntity implements Serializable {
    private static final long serialVersionUID = 7408076946526041552L;

    private int id;
    /**
     * 抓取网页的源地址
     * */
    private String webUrl;
    /**
     * 抓取网页的地址id
     * */
    private int webUrlId;
    /**
     * 标题
     * */
    private String title;
    /**
     * 封面
     * */
    private String img;
    /**
     * 抓取外站的封面地址,动漫之家因为有防盗链，需要下载到本地在保存
     * */
    private String imgWeb;
    /**
     * 状态: 1=有效 0=草稿 -1=已删除
     * */
    private int status;
    /**
     * 发布时间
     * */
    private String pushTime;
    /**
     * 作者
     * */
    private String author;
    /**
     * 简介
     * */
    private String about;
    /**
     * 模块id 7:捏他君 8：动漫星空 9：动漫之家 10：ACG狗屋
     * */
    private int moduleId;
}
