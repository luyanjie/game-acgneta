package com.tongbu.game.service.umeng;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongbu.game.common.request.HttpClientUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jokin
 * @date 2018/9/20 10:25
 */
public class PushClient {

    private static final Logger log = LoggerFactory.getLogger(PushClient.class);

    private static final String USER_AGENT = "Mozilla/5.0";

    private static final String HOST = "http://msg.umeng.com";

    private static final String UPLOAD_PATH = "/upload";

    private static final String POST_PATH = "/api/send";

    public static String send(UmengNotification msg) throws Exception {
        String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
        msg.setPredefinedKeyValue("timestamp", timestamp);
        String url = StringUtils.join(HOST, POST_PATH);
        String postBody = msg.getPostBody();
        log.info(postBody);
        String sign = DigestUtils.md5Hex(("POST" + url + postBody + msg.getAppMasterSecret()).getBytes("utf8"));
        url = url + "?sign=" + sign;
        log.info(url);
        Map<String, String> heads = new HashMap<>();
        heads.put("User-Agent", USER_AGENT);
        String responseContent = HttpClientUtils.postData(url, heads, postBody);
        log.info("PushClient send:" + responseContent);
        return responseContent;
    }

    public static String uploadContents(String appkey, String appMasterSecret, String contents) throws Exception {
        JSONObject uploadJson = new JSONObject();
        uploadJson.put("appkey", appkey);
        String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
        uploadJson.put("timestamp", timestamp);
        uploadJson.put("content", contents);
        // Construct the request
        String url = StringUtils.join(HOST, UPLOAD_PATH);
        String postBody = uploadJson.toString();
        String sign = DigestUtils.md5Hex(("POST" + url + postBody + appMasterSecret).getBytes("utf8"));
        url = url + "?sign=" + sign;

        Map<String, String> heads = new HashMap<>();
        heads.put("User-Agent", USER_AGENT);
        String responseContent = HttpClientUtils.postData(url, heads, postBody);
        JSONObject respJson = JSON.parseObject(responseContent);
        String ret = respJson.getString("ret");
        if (!"SUCCESS".equals(ret)) {
            throw new Exception("Failed to upload file");
        }
        JSONObject data = respJson.getJSONObject("data");
        // 返回 file_id
        return data.getString("file_id");
    }
}
