package com.tongbu.game.common;

import com.alibaba.fastjson.JSONObject;

/**
 * @author jokin
 * @date 2018-10-11
 *
 * 友盟自定义内容
 * */
public class UmengCustomized {
    /**
     * Customized field
     * @param type 0：动画 1：捏报（咨询）
     * @param id 动画或者捏报（咨询）id
     * @param commentId 当评论时的评论ID
     * @param source 消息分类 1：系统通知 2：点赞 3：回复
     * @return JSONObject
     * */
    public static JSONObject field(int type,int id,int commentId,int source)
    {
        // 跳转信息
        JSONObject jsonObject = new JSONObject();
        // 0：动画 1：捏报（咨询）
        jsonObject.put("type",type);
        // 动画或者捏报（咨询）id
        jsonObject.put("id",id);
        // 当评论时的评论ID
        jsonObject.put("commentId",commentId);
        // 消息分类 1：系统通知 2：点赞 3：回复
        jsonObject.put("source",source);
        // {type:0,id:1,commentId:1,source:2}
        return jsonObject;
    }
}
