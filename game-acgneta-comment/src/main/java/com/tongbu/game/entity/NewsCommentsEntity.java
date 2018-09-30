package com.tongbu.game.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jokin
 * @date 2018/9/29 15:18
 *
 * 捏报咨询 评论
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsCommentsEntity {
    private int id;
    /**
     * 捏报咨询ID
     * */
    private int newsId;
    /**
     * 评论内容
     * */
    private String content;
    /**
     * 评论者ID,若是回复的内容就是回复者ID
     * */
    private int fromUid;
    /**
     * 被回复者的ID，用户回复评论时，对应评论的用户ID，当表示回复内容时有值，其余默认为0
     * */
    private int toUid;
    /**
     * 回复对应的评论ID，当表示回复时有值，其余默认为0
     * */
    private int parentId;
    /**
     * 回复时的引用内容
     * */
    private String refContent;
    /**
     * 赞数量
     * */
    private int likes;
    /**
     * 踩数量(暂时没有)
     * */
    private int dislikes;
    /**
     * 回复数量
     * */
    private int replyCount;
    /**
     * 状态: 1=有效 0=无效
     * */
    private int status;
    /**
     * 添加时间
     * */
    private String insertTime;
    /**
     * 更新时间
     * */
    private String updateTime;
    /**
     * 评论来源：1：iOS 2：Android 3：pc 4：M站
     * */
    private int source;
}
