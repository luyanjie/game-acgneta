package com.tongbu.game.controller.umeng;

import com.tongbu.game.common.ResponseUtil;
import com.tongbu.game.entity.AnimationMessageEntity;
import com.tongbu.game.entity.MessageResponse;
import com.tongbu.game.entity.UmengDeviceTokenEntity;
import com.tongbu.game.service.AnimationMessageService;
import com.tongbu.game.service.DeviceTokenService;
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
    AnimationMessageService messageService;

    @Autowired
    DeviceTokenService deviceTokenService;


    @RequestMapping("/send/unicast")
    public MessageResponse sendUnicast(int msgId) {
        // 获取消息内容
        AnimationMessageEntity message = messageService.findById(msgId);
        if (message == null || message.getId() <= 0) {
            return ResponseUtil.success("no message send");
        }

        // 获取deviceToken
        // TODO Set your device token
        List<UmengDeviceTokenEntity> items = deviceTokenService.findByUid(message.getToUid());
        if (items == null || items.size() <= 0) {
            return ResponseUtil.success("no device token");
        }

        items.stream().filter(x -> x.getAppSource() == 1).limit(1).forEach(item -> {
            iosService.sendUnicast(message, item.getDeviceToken());
        });

        items.stream().filter(x -> x.getAppSource() == 2).limit(1).forEach(item -> {
            umengAndroidService.sendUnicast(message, item.getDeviceToken());
        });

        /*items.stream().limit(1).forEach(x -> {
            if (x.getAppSource() == 1) {
                iosService.sendUnicast(message, x.getDeviceToken());
            } else {
                umengAndroidService.sendUnicast(message, x.getDeviceToken());
            }
        });*/
        return ResponseUtil.success("success");
    }
}
