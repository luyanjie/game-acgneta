package com.tongbu.game.service.task;

/**
 * @author jokin
 * @date 2019/1/15 15:42
 */
public interface Act10110NewsResources {
    void exec();

    /**
     * 获取外站详细页
     * @param newId 新闻id
     * @param webUrl 外站网站地址
     * @param moduleId 外站标识
     * @param source 0：新抓取的 1：补抓的
     */
    void getDetails(int newId, String  webUrl,int moduleId,int source);
}
