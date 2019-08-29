package com.tongbu.game.service;

import com.tongbu.game.entity.AnimationMessageEntity;

import java.util.List;

/**
 * @author jokin
 * @date 2018/9/28 17:13
 */
public interface IUmengService {

    /**
     * 消息级别
     * */
    int GRADE = 2;

    /**
     * 单个deviceToken发送
     * @param message 消息内容
     * @param deviceToken 单个deviceToken
     * */
    void sendUnicast(AnimationMessageEntity message,String deviceToken);
    /**
     * 多个deviceToken 批量发送
     * @param message 消息内容
     * @param deviceTokens 多个deviceToken列表
     * */
    void sendFilecast(AnimationMessageEntity message, List<String> deviceTokens);

    /**
     * 广播方式，在友盟有记录的设备都发送
     */
    void sendBroadcast(AnimationMessageEntity message);
}
