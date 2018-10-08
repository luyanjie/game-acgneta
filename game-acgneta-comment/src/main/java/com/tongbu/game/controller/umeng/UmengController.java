package com.tongbu.game.controller.umeng;

import com.tongbu.game.dao.AnimationMessageMapper;
import com.tongbu.game.dao.UmengDeviceTokenMapper;
import com.tongbu.game.entity.AnimationMessageEntity;
import com.tongbu.game.entity.UmengDeviceTokenEntity;
import com.tongbu.game.service.IUmengService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jokin
 * @date 2018/9/28 17:34
 */

@RestController
@RequestMapping("/umeng")
public class UmengController {

    @Autowired
    @Qualifier(value = "umengiOSService")
    IUmengService iosService;

    @Autowired
    @Qualifier(value = "umengAndroidService")
    IUmengService umengAndroidService;

    @Autowired
    AnimationMessageMapper messageMapper;

    @Autowired
    UmengDeviceTokenMapper deviceTokenMapper;


    @RequestMapping("/send/unicast")
    public String sendUnicast(int msgId) {
        // 获取消息内容
        AnimationMessageEntity message = messageMapper.findById(msgId);
        if (message == null || message.getSource() < 2 || message.getSource() > 3) {
            return "no message send";
        }
        // 获取deviceToken
        // TODO Set your device token
        List<UmengDeviceTokenEntity> items = deviceTokenMapper.findByUid(message.getToUid());
        if (items == null || items.size() <= 0) {
            return "no device token";
        }
        items.stream().limit(1).forEach(x -> {
            if (x.getAppSource() == 1) {
                iosService.sendUnicast(message, x.getDeviceToken());
            } else {
                umengAndroidService.sendUnicast(message, x.getDeviceToken());
            }
        });
        return "success";
    }
}
