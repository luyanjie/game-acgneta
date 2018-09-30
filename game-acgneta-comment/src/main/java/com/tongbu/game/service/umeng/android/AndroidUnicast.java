package com.tongbu.game.service.umeng.android;

import com.tongbu.game.service.umeng.AndroidNotification;

/**
 * @author jokin
 * @date 2018/9/20 10:11
 *
 * unicast-单播
 */
public class AndroidUnicast extends AndroidNotification {
    public AndroidUnicast(String appkey,String appMasterSecret) throws Exception {
        setAppMasterSecret(appMasterSecret);
        setPredefinedKeyValue("appkey", appkey);
        this.setPredefinedKeyValue("type", "unicast");
    }

    public void setDeviceToken(String token) throws Exception {
        setPredefinedKeyValue("device_tokens", token);
    }
}
