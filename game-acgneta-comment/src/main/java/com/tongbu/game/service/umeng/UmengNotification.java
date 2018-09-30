package com.tongbu.game.service.umeng;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author jokin
 * @date 2018/9/19 17:46
 */
public abstract class UmengNotification {
    /**
     * This JSONObject is used for constructing the whole request string.
     * */
    protected final JSONObject rootJson = new JSONObject();

    /**
     * The app master secret
     * */
    protected String appMasterSecret;

    /**
     * Keys can be set in the root level：发送基本信息
     * */
    protected static final HashSet<String> ROOT_KEYS = new HashSet<String>(Arrays.asList(
            "appkey", "timestamp", "type", "device_tokens", "alias", "alias_type", "file_id",
            "filter", "production_mode", "feedback", "description", "thirdparty_id"));

    /**
     * Keys can be set in the policy level: 发送策略
     * */
    protected static final HashSet<String> POLICY_KEYS = new HashSet<String>(Arrays.asList(
            "start_time", "expire_time", "max_send_num"
    ));

    /**
     * 抽象方法属性添加
     * */
    public abstract boolean setPredefinedKeyValue(String key,Object value) throws Exception;


    public String getAppMasterSecret() {
        return appMasterSecret;
    }

    public void setAppMasterSecret(String appMasterSecret) {
        this.appMasterSecret = appMasterSecret;
    }

    public String getPostBody(){
        return rootJson.toString();
    }

    /**
     * 环境设置
     * */
    protected void setProductionMode(Boolean prod) throws Exception {
        setPredefinedKeyValue("production_mode", prod.toString());
    }

    /**
     * 设置为正式环境
     * */
    public void setProductionMode() throws Exception{
        setProductionMode(true);
    }

    /**
     * 设置为测试环境
     * */
    public void setTestMode() throws Exception{
        setProductionMode(false);
    }

    /**
     * 官方建议添加发送信息描述
     * */
    public void setDescription(String description) throws Exception{
        setPredefinedKeyValue("description", description);
    }

    /**
     * 设置发送开始时间，若不填写表示立即发送。格式: "YYYY-MM-DD hh:mm:ss"。
     * */
    public void setStartTime(String startTime) throws Exception{
        setPredefinedKeyValue("start_time",startTime);
    }

    /**
     * 设置消息过期时间，格式："YYYY-MM-DD hh:mm:ss",默认3天过期
     * */
    public void setExpireTime(String expireTime) throws Exception{
        setPredefinedKeyValue("expire_time",expireTime);
    }

    /**
     * 发送限速，每秒发送的最大条数。
     * */
    public void setMaxSendNum(Integer num) throws Exception {
        setPredefinedKeyValue("max_send_num", num);
    }
}
