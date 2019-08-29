package com.tongbu.game.common.enums;

import lombok.Getter;

/**
 * 用户行为
 */
@Getter
public enum UserActionEnum {
    /**
     * 注册
     */
    REGISTER("register", 50, false,"注册"),
    /**
     * 登陆
     */
    LOGIN("login", 50, false,"登陆"),
    /**
     * 个性签名
     */
    SIGNATURE("signature", 20, false,"个性签名"),
    /**
     * 启动APP
     */
    STARTUP("startApp", 5, false,"启动APP"),
    /**
     * 添加动画评论
     */
    COMMENT("comment", 5, true,"添加动画评论"),
    /**
     * 取消动画评论
     */
    CANCEL_COMMENT("cancelComment", -5, false,"取消动画评论"),

    /**
     * 捏报评论
     */
    NEWS_COMMENT("newComment", 5, false,"捏报评论"),
    /**
     * 取消捏报评论
     */
    CANCEL_NEWS_COMMENT("cancelNewComment", -5, false,"取消捏报评论"),
    /**
     * 回复评论
     */
    REPLY("reply", 2, false,"回复评论"),
    /**
     * 取消回复
     */
    CANCEL_REPLY("cancelReply", -2, false,"取消回复"),

    /**
     * 捏报回复评论
     */
    NEWS_REPLY("newReply", 2, false,"捏报回复评论"),
    /**
     * 捏报取消回复评论
     */
    CANCEL_NEWS_REPLY("cancelNewReply", -2, false,"捏报取消回复评论"),
    /**
     * 点赞
     */
    LIKE("like", 1, false,"点赞"),
    /**
     * 取消点赞
     */
    CANCEL_LIKE("cancelLike", -1, false,"取消点赞"),
    /**
     * 被点赞
     */
    TO_LIKE("toLike", 1, false,"被点赞"),
    /**
     * 取消被点赞
     */
    CANCEL_TO_LIKE("cancelToLike", -1, false,"取消被点赞"),

    NEWS_LIKE("newsLike",1,false,"捏报评论点赞"),

    NEWS_TO_LIKE("newsToLike",1,false,"捏报评论被点赞"),
    /**
     * 标记追番
     */
    LOVE("love", 1, true,"标记追番"),
    /**
     * 取消标记追番
     */
    CANCEL_LOVE("cancelLove", -1, false,"取消标记追番"),
    /**
     * 看完
     */
    AFTER_READING("afterReading", 1, false,"看完"),
    /**
     * 取消看完
     */
    CANCEL_AFTER_READING("cancelAfterReading", -1, false,"取消看完"),

    /**
     * 后台设置优评
     */
    GOOD_COMMENT("goodComment", 20, false,"后台设置优评"),
    /**
     * 后台取消优评
     */
    CANCEL_GOOD_COMMENT("cancelGoodComment", -20, false,"后台取消优评"),
    /**
     * 提问
     */
    QUESTION("question", 5, true,"提问"),
    /**
     * 回答提问
     */
    ANSWER("answer", 5, true,"回答提问"),
    /**
     * 分享
     */
    SHARE("share", 5, false,"分享"),
    DAILY_SIGN("dailySign",5,false,"每日签到");

    /**
     * 行为
     */
    private String action;

    /**
     * 对应的积分
     */
    private int integral;

    /**
     * 是否需要展示动态
     */
    private boolean show;
    /**
     * 描述
     */
    private String description;

    UserActionEnum(String action, int integral, boolean show,String description) {
        this.action = action;
        this.integral = integral;
        this.show = show;
        this.description = description;

    }
}
