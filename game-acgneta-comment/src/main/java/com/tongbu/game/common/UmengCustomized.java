package com.tongbu.game.common;

import com.alibaba.fastjson.JSONObject;

public class UmengCustomized {
    /**
     * Customized field
     * */
    public static JSONObject field(int type,int id,int commentId)
    {
        // 跳转信息
        JSONObject jsonObject = new JSONObject();
        // 0：动画 1：捏报（咨询）
        jsonObject.put("type",type);
        // 动画或者捏报（咨询）id
        jsonObject.put("id",id);
        // 当评论时的评论ID
        jsonObject.put("commentId",commentId);
        // {type:0,id:1,commentId:1}
        return jsonObject;
    }
}
