package com.tongbu.game.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongbu.game.common.UmengCustomized;
import com.tongbu.game.common.constant.UmengConstant;
import com.tongbu.game.dao.AnimationCommentsMapper;
import com.tongbu.game.dao.UmengDeviceTokenMapper;
import com.tongbu.game.entity.AnimationCommentsEntity;
import com.tongbu.game.entity.UmengDeviceTokenEntity;
import com.tongbu.game.service.umeng.PushClient;
import com.tongbu.game.service.umeng.ios.IOSUnicast;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author jokin
 * @date 2018/9/28 17:11
 */
@Service(value = "umengiOSService")
public class UmengiOSService implements IUmengService {

    private static final Logger log = LoggerFactory.getLogger(UmengiOSService.class);

    @Autowired
    UmengDeviceTokenMapper mapper;

    /**
     * 设置iOS umeng 环境
     */
    @Value("${umeng.ios.mode}")
    private static boolean mode;

    @Override
    public String sendUnicast(JSONObject jsonObject) {

        if (!jsonObject.containsKey("uid")) {
            return "no uid comments";
        }

        try {
            IOSUnicast unicast = new IOSUnicast(UmengConstant.IOS.APPKEY, UmengConstant.IOS.APP_MASTER_SECRET);
            // TODO Set your device token
            UmengDeviceTokenEntity umeng = mapper.findByUidAndSource((Integer) jsonObject.get("uid"), (Integer) jsonObject.get("source"));
            if (umeng == null) {
                return "no device token";
            }
            //unicast.setDeviceToken("a5e8266a23a90e44cb35b1e8505cc34a7039745d339fa494e09243dac7146fe4");
            unicast.setDeviceToken(umeng.getDeviceToken());
            // 屏幕弹出的内容
            unicast.setAlert(String.valueOf(jsonObject.get("content")));
            unicast.setBadge(0);
            // 描述信息，官方建议填写
            unicast.setDescription("hello world");
            unicast.setSound(UmengConstant.IOS.SOUND);
            // TODO set 'production_mode' to 'true' if your app is under production mode
            // 设置环境
            if (mode) {
                unicast.setProductionMode();
            } else {
                unicast.setTestMode();
            }
            // Set customized fields 额外信息
            // 内容
            unicast.setCustomizedField(UmengConstant.CUSTOMZIED_FIELD_CONTENT, "hello world");
            // 跳转信息
            unicast.setCustomizedField(UmengConstant.CUSTOMZIED_FIELD_TYPE_SOURCE,
                    UmengCustomized.field((Integer) jsonObject.get("type"), (Integer) jsonObject.get("id"), (Integer) jsonObject.get("commentId")));
            // {"ret":"SUCCESS","data":{"msg_id":"uujt5gw153810170011200"}}
            String response = PushClient.send(unicast);
            return StringUtils.isEmpty(response)
                    ? "fail"
                    : String.valueOf(JSON.parseObject(response).get("ret"));
        } catch (Exception e) {
            log.error("iOS sendUnicast", e);
        }
        return "fail";
    }
}
