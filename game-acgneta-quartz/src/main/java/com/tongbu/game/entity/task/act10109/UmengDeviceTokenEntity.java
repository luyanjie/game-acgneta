package com.tongbu.game.entity.task.act10109;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jokin
 * @date 2018/12/29 18:12
 */
@Data
public class UmengDeviceTokenEntity  implements Serializable{
    private static final long serialVersionUID = -2792012791850690243L;

    private int id;
    private int uid;
    private String deviceToken;
    private int appSource;
    private String insertTime;
}
