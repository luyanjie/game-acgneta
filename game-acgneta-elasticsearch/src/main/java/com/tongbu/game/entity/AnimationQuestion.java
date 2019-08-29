package com.tongbu.game.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "animationsquestion", type = "doc", createIndex = false)
@Data
public class AnimationQuestion {
    @Id
    private int id;

    private String inserttime;

    private String title;

    private String content;
}
