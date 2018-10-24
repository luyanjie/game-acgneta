package com.tongbu.game.entity.Request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jokin
 * @date 2018/10/24 15:05
 */
@Data
@NoArgsConstructor
public class AnimationMessageRequest implements Serializable {
    private static final long serialVersionUID = 7505991295595023171L;

    private Integer id = 0;

    /**
     * 消息标题
     */
    private String title = "";
    /**
     * 消息内容
     */
    private String message = "";
    /**
     * 消息来源
     */
    private Integer source = 1;

    /**
     * 链接url
     */
    private String linkUrl = "";
    /**
     * 消息产生者id
     */
    private Integer fromUid = 0;
    /**
     * 消息接收者id
     */
    private Integer toUid = 0;

    private Integer commentId = 0;
    private Integer rewardScore = 0;
    private Integer grade = 0;
    private Integer typeSource = 0;
    private Integer typeId = 0;
}
