package com.tongbu.game.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jokin
 * @date 2018/9/29 15:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UmengDeviceTokenEntity {
    private int id;
    /**
     * 用户i
     * */
    private int uid;
    /**
     * Umeng 返回的deviceToken
     * */
    private String deviceToken;
    /**
     * 1：iOS 2：Android
     * */
    private int appSource;
    /**
     * 入库时间
     * */
    private String insertTime;
    /**
     * 1:有效 0：无效
     * */
    private int status;

    public UmengDeviceTokenEntity(int uid, String deviceToken, int appSource) {
        this.uid = uid;
        this.deviceToken = deviceToken;
        this.appSource = appSource;
    }
}
