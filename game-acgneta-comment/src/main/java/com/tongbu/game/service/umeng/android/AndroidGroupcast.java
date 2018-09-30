package com.tongbu.game.service.umeng.android;

import com.alibaba.fastjson.JSONObject;
import com.tongbu.game.service.umeng.AndroidNotification;

/**
 * @author jokin
 * @date 2018/9/20 10:10
 *
 * groupcast-组播，按照filter筛选用户群, 请参照filter参数
 */
public class AndroidGroupcast extends AndroidNotification {
    public AndroidGroupcast(String appkey,String appMasterSecret) throws Exception {
        setAppMasterSecret(appMasterSecret);
        setPredefinedKeyValue("appkey", appkey);
        this.setPredefinedKeyValue("type", "groupcast");
    }

    public void setFilter(JSONObject filter) throws Exception {
        setPredefinedKeyValue("filter", filter);
    }
}
