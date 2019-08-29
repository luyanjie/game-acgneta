package com.tongbu.game.entity.task.act10701;

import lombok.Data;

import java.io.Serializable;

@Data
public class RunEntity implements Serializable {
    private static final long serialVersionUID = 4328214336686200503L;

    private int id;

    private int count;

    private double score;
}
