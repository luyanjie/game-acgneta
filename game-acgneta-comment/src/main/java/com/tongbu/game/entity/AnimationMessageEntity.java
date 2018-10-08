package com.tongbu.game.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jokin
 * @date 2018/9/29 14:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimationMessageEntity {
    private int id;
    /**
     * 消息标题
     * */
    private String title;
    /**
     * 内容
     * */
    private String content;
    /**
     * 链接url
     * */
    private String linkUrl;
    /**
     * 消息类型：1=指定用户 2=所有用户
     * */
    private int msgType;
    /**
     * 消息发放来源：1=系统通知，2=评论有新的点赞，3=评论有新回复
     * */
    private int source;
    /**
     * 状态: 1=有效 0=无效
     * */
    private int status;
    /**
     * 添加时间
     * */
    private String insertTime;
    /**
     * 消息发送触发用户
     * */
    private int fromUid;
    /**
     * 消息发送接收用户
     * */
    private int toUid;
    /**
     * 当跟评论有关的消息时 的评论id
     * */
    private int commentId;
    /**
     * 奖励的积分
     */
    private int rewardScore;
    /**
     * 系统消息等级 0：最低级别（小于0 在app 中不显示）
     * */
    private int grade;
    /**
     * 当 评论来源 source=2或者3时 此值--->{0：动画 1：咨询}
     * */
    private int typeSource;
    /**
     * 当 评论来源 source=2或者3时 此值对应typeSource的动画id或者咨询（捏报）id
     * */
    private int typeId;
}
