package com.tongbu.game.service.umeng.ios;

import com.tongbu.game.service.umeng.IOSNotification;

/**
 * @author jokin
 * @date 2018/9/20 9:58
 * 文件播，多个device_token可通过文件形式批量发送
 */
public class IOSFilecast extends IOSNotification {
    public IOSFilecast(String appkey,String appMasterSecret) throws Exception {
        setAppMasterSecret(appMasterSecret);
        setPredefinedKeyValue("appkey", appkey);
        this.setPredefinedKeyValue("type", "filecast");
    }

    public void setFileId(String fileId) throws Exception {
        setPredefinedKeyValue("file_id", fileId);
    }
}
