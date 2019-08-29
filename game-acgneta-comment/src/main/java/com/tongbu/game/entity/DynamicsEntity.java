package com.tongbu.game.entity;

import com.tongbu.game.common.enums.UserActionEnum;
import lombok.Data;

@Data
public class DynamicsEntity {
    private Integer id;
    /**
     * 用户行为
     */
    private UserActionEnum userActionEnum;
    /**
     * 用户id
     */
    private int uid;
    /**
     * 动画id
     */
    private int animationId;
    /**
     * 评论id
     */
    private int commentId;
    /**
     * 问答id
     */
    private int questionId;
    /**
     * 描述
     */
    private String description;
}
