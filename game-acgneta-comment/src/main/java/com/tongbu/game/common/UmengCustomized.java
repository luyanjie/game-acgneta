package com.tongbu.game.common;

import com.alibaba.fastjson.JSONObject;

/**
 * @author jokin
 * @date 2018-10-11
 * <p>
 * 友盟自定义内容
 */
public class UmengCustomized {
    /**
     * Customized field
     *
     * @param pushModule      跳转模块 0：动画页面 1：捏报（咨询）页面 2：消息页面 3： 话题活动
     * @param id        当type=1 时 捏报（咨询）id   当type=0 时 动画 id
     * @param dbSource  当type=2 时 消息分类 1：系统通知 2：点赞 3：回复
     * @return JSONObject
     */
    public static JSONObject field(int pushModule, int id, int dbSource) {
        // 跳转信息
        JSONObject jsonObject = new JSONObject();
        // 跳转模块 0：动画页面 1：捏报（咨询）页面 2：消息页面
        jsonObject.put("type", pushModule);
        // 当type=2 时，1（系统消息） 2（点赞） 3（回复）
        // 当type=1 时 捏报（咨询）id
        // 当type=0 时 动画 id
        if (pushModule == 2) {
            jsonObject.put("source", dbSource);
        }
        else {
            jsonObject.put("source", id);
        }
        return jsonObject;
    }
}
