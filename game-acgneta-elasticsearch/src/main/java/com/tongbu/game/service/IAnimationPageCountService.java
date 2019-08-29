package com.tongbu.game.service;

import com.tongbu.game.entity.response.PageTotal;

public interface IAnimationPageCountService {
    /**
     * 根据关键字匹配查询，不进行分词
     * @param page 页码 从1开始
     * @param size 每页数据
     * @param q 关键字
     * @return 新闻内容
     */
    PageTotal<Integer> wildcard(Integer page, Integer size, String q);
}
