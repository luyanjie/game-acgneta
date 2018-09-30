package com.tongbu.game.service.umeng.android;

import com.tongbu.game.service.umeng.AndroidNotification;

/**
 * @author jokin
 * @date 2018/9/20 10:09
 *
 * filecast-文件播，多个device_token可通过文件形式批量发送
 */
public class AndroidFilecast extends AndroidNotification {
    public AndroidFilecast(String appkey,String appMasterSecret) throws Exception {
        setAppMasterSecret(appMasterSecret);
        setPredefinedKeyValue("appkey", appkey);
        this.setPredefinedKeyValue("type", "filecast");
    }

    public void setFileId(String fileId) throws Exception {
        setPredefinedKeyValue("file_id", fileId);
    }
}
