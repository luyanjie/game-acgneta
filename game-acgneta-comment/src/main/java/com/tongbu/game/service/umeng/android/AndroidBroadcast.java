package com.tongbu.game.service.umeng.android;

import com.tongbu.game.service.umeng.AndroidNotification;

/**
 * @author jokin
 * @date 2018/9/20 10:05
 *
 * 广播
 */
public class AndroidBroadcast extends AndroidNotification {
    public AndroidBroadcast(String appkey,String appMasterSecret) throws Exception {
        setAppMasterSecret(appMasterSecret);
        setPredefinedKeyValue("appkey", appkey);
        this.setPredefinedKeyValue("type", "broadcast");
    }
}
