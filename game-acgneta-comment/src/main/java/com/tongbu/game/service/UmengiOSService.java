package com.tongbu.game.service;

import com.alibaba.fastjson.JSONObject;
import com.tongbu.game.common.constant.UmengConstant;
import com.tongbu.game.service.umeng.PushClient;
import com.tongbu.game.service.umeng.ios.IOSUnicast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author jokin
 * @date 2018/9/28 17:11
 */
@Service(value = "umengiOSService")
public class UmengiOSService implements IUmengService {

    private static final Logger log = LoggerFactory.getLogger(UmengiOSService.class);

    @Override
    public String sendUnicast(int type,int commentId) {

        try {
            IOSUnicast unicast = new IOSUnicast(UmengConstant.IOS.APPKEY, UmengConstant.IOS.APP_MASTER_SECRET);
            // TODO Set your device token
            unicast.setDeviceToken("a5e8266a23a90e44cb35b1e8505cc34a7039745d339fa494e09243dac7146fe4");
            // 屏幕弹出的内容
            unicast.setAlert("hello world");
            unicast.setBadge(0);
            unicast.setDescription("hello world");
            unicast.setSound("default");
            // TODO set 'production_mode' to 'true' if your app is under production mode
            // unicast.setTestMode();
            // 设置为生成环境 测试一下
            unicast.setProductionMode();
            // Set customized fields
            // 内容
            unicast.setCustomizedField("content", "hello world");
            // 跳转信息
            JSONObject jsonObject = new JSONObject();
            // 0：动画 1：捏报（咨询）
            jsonObject.put("type",type);
            // 动画或者捏报（咨询）id
            jsonObject.put("id",1279);
            // 当评论时的评论ID
            jsonObject.put("commentId",commentId);
            // {type:0,id:1,commentId:1}
            unicast.setCustomizedField("typeSource",jsonObject);

            //PushClient.send(unicast);
            return "success";
        }
        catch (Exception e){
            log.error("iOS sendUnicast",e);
        }
        return "fail";
    }
}
