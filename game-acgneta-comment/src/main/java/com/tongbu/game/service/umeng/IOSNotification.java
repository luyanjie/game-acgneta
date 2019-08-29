package com.tongbu.game.service.umeng;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author jokin
 * @date 2018/9/19 17:57
 */
public class IOSNotification extends UmengNotification {

    /**
     * Keys can be set in the aps level
     * */
    private static final HashSet<String> APS_KEYS = new HashSet<>(Arrays.asList("alert", "badge", "sound", "content-available"));

    @Override
    public boolean setPredefinedKeyValue(String key, Object value) throws Exception {
        if(getRootKeys().contains(key)){
            rootJson.put(key,value);
        }
        else if(APS_KEYS.contains(key)){
            // This key should be in the aps level
            JSONObject apsJson;
            JSONObject payloadJson;
            if(rootJson.containsKey("payload")){
                payloadJson = rootJson.getJSONObject("payload");
            }
            else{
                payloadJson = new JSONObject();
                rootJson.put("payload",payloadJson);
            }
            if (payloadJson.containsKey("aps")) {
                apsJson = payloadJson.getJSONObject("aps");
            } else {
                apsJson = new JSONObject();
                payloadJson.put("aps", apsJson);
            }
            apsJson.put(key, value);
        }
        else if(getPolicyKeys().contains(key)){
            // This key should be in the body level
            JSONObject policyJson = null;
            if (rootJson.containsKey("policy")) {
                policyJson = rootJson.getJSONObject("policy");
            } else {
                policyJson = new JSONObject();
                rootJson.put("policy", policyJson);
            }
            policyJson.put(key, value);
        }else {
            if (key.equals("payload") || key.equals("aps") || key.equals("policy")) {
                throw new Exception("You don't need to set value for " + key + " , just set values for the sub keys in it.");
            } else {
                throw new Exception("Unknownd key: " + key);
            }
        }
        return true;
    }

    // Set customized key/value for IOS notification
    public boolean setCustomizedField(String key, Object value) throws Exception {
        //rootJson.put(key, value);
        JSONObject payloadJson = null;
        if (rootJson.containsKey("payload")) {
            payloadJson = rootJson.getJSONObject("payload");
        } else {
            payloadJson = new JSONObject();
            rootJson.put("payload", payloadJson);
        }
        payloadJson.put(key, value);
        return true;
    }

    public void setAlert(Object token) throws Exception {
        setPredefinedKeyValue("alert", token);
    }

    public void setBadge(Integer badge) throws Exception {
        setPredefinedKeyValue("badge", badge);
    }

    public void setSound(String sound) throws Exception {
        setPredefinedKeyValue("sound", sound);
    }

    public void setContentAvailable(Integer contentAvailable) throws Exception {
        setPredefinedKeyValue("content-available", contentAvailable);
    }
}
