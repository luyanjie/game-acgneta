package com.tongbu.game.service.umeng.ios;

import com.tongbu.game.service.umeng.IOSNotification;

/**
 * @author jokin
 * @date 2018/9/20 9:52
 * 广播
 */
public class IOSBroadcast extends IOSNotification {
    public IOSBroadcast(String appkey,String appMasterSecret) throws Exception{
        setAppMasterSecret(appMasterSecret);
        setPredefinedKeyValue("appkey", appkey);
        this.setPredefinedKeyValue("type", "broadcast");
    }
}
