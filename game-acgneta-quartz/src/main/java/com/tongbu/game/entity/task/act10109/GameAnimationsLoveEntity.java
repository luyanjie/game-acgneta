package com.tongbu.game.entity.task.act10109;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jokin
 * @date 2018/12/27 11:14
 */
@Data
public class GameAnimationsLoveEntity implements Serializable {
    private static final long serialVersionUID = -4401627719970159662L;
    private int id;
    private int uid;
    private int animationId;
    private String animationName;
    private int subscribe;
}
