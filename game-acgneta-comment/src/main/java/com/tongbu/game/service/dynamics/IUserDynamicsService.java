package com.tongbu.game.service.dynamics;

import com.tongbu.game.entity.dynamics.DynamicsNoShow;
import com.tongbu.game.entity.dynamics.DynamicsShow;
import com.tongbu.game.entity.response.PageResponse;

import java.util.ArrayList;
import java.util.List;

public interface IUserDynamicsService {

    /**
     * 每页数据
     */
    int defaultSize = 10;

    /**
     * 排序字段
     */
    String sortFiled = "_id";

    String CACHE_LOGIN_TODAY = "g2:login:today";
    String CACHE_SCORE_LIST = "g2:dynamics:score";

    /**
     * CACHE_SCORE_LIST 里面uid与score的分隔符
     */
    String CACHE_SCORE_LIST_SPLIT = ":";

    /**
     * 根据用户id 获取动态行为
     * @param uid 用户id
     * @return list
     */
    List<DynamicsShow> findByUid(int uid);

    /**
     * 根据用户id获取分页动态行为
     * @param uid 用户id
     * @param page 页码（从0开始）
     * @param size 每页显示条数
     * @return 返回结果
     */
    PageResponse<DynamicsShow> findByUid(int uid, int page, int size);

    DynamicsShow insert(DynamicsShow dynamicsA);

    /**
     * 添加积分
     * @param dynamicsNoShow 积分信息
     * @return 积分信息
     */
    DynamicsNoShow addScore(DynamicsNoShow dynamicsNoShow);

    /**
     * 根据id删除
     * @param id 主键id
     */
    void deleteById(String id);

    /**
     * 根据用户id，typeId，commentId删除
     * @param uid 用户id
     * @param typeId typeId
     * @param commentId 评论id 如果有
     */
    void deleteByUidAndTypeIdAndCommentId(int uid,int typeId,int commentId);

    /**
     * 批量插入mongo
     * @param list list
     * @return list
     */
    List<?> insert(ArrayList<DynamicsShow> list);

    /**
     * 更新积分
     */
    void updateScore();
}
