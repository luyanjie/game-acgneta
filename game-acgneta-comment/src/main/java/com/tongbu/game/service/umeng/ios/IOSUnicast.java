package com.tongbu.game.service.umeng.ios;

import com.tongbu.game.service.umeng.IOSNotification;

/**
 * @author jokin
 * @date 2018/9/19 18:30
 * 单播
 */
public class IOSUnicast extends IOSNotification {
    public IOSUnicast(String appkey,String appMasterSecret) throws Exception{
        setAppMasterSecret(appMasterSecret);
        setPredefinedKeyValue("appkey",appkey);
        this.setPredefinedKeyValue("type", "unicast");
    }

    public void setDeviceToken(String token) throws Exception {
        setPredefinedKeyValue("device_tokens", token);
    }
}
