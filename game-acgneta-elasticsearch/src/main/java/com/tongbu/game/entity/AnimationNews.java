package com.tongbu.game.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "animationsnews", type = "doc", createIndex = false)
@Data
public class AnimationNews {
    @Id
    private int id;

    private String title;

    private String briefcontent;

    private String inserttime;

    private String updatetime;

    private int status;

    private int areatype;

    private int moduleid;
}
