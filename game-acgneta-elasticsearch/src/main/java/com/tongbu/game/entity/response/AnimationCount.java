package com.tongbu.game.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class AnimationCount<T> implements Serializable {

    private static final long serialVersionUID = -7678002370252275762L;
    /**
     * 新闻捏报条数
     */
    private long newsCount;

    private int newsPage;

    /**
     * 问答条数
     */
    private long questionCount;

    private int questionPage;

    private List<T> list = new ArrayList<>();
}
