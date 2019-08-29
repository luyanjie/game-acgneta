package com.tongbu.game.entity.task.act10110;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jokin
 * @date 2019/1/15 15:34
 */
@Data
public class GameResourcesCategoryEntity implements Serializable {
    private static final long serialVersionUID = 7146561813319824405L;

    private int id;
    private String name;
    private String icon;
    private String module;
}
