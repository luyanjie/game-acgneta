package com.tongbu.game.service.task.act10107;

/**
 * @author jokin
 * @date 2018/11/14 14:25
 */
public interface UrlService {
    /**
     * 执行获取数据方法
     * @param id 数据库标识
     * @param pageUrlId 请求链接的id
     * @param url 请求链接
     * */
    void execute(int id, String url, int pageUrlId);
}
