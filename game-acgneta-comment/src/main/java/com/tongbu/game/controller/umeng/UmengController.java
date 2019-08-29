package com.tongbu.game.controller.umeng;

import com.tongbu.game.entity.AnimationMessageEntity;
import com.tongbu.game.entity.UmengDeviceTokenEntity;
import com.tongbu.game.service.AnimationMessageService;
import com.tongbu.game.service.DeviceTokenService;
import com.tongbu.game.service.IUmengService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xinba.core.ResponseUtil;
import vip.xinba.core.entity.MessageResponse;

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
            if(!message.getTitle().startsWith("你在追的番有更新啦")){
                iosService.sendUnicast(message, item.getDeviceToken());
            }
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

    /**
     * 批量发送
     */
    @RequestMapping("/send/filecast")
    public MessageResponse sendfilecast(int msgId) {

        // 获取消息内容
        AnimationMessageEntity message = messageService.findById(msgId);
        if (message == null || message.getId() <= 0) {
            return ResponseUtil.success("no message send");
        }
        // 获取deviceToken
        // TODO Set your device token
        boolean bl = true;
        int page = 1;
        int pageSize = 100;
        while (bl) {

            List<String> items = deviceTokenService.findByAllDeviceToken(1, (page - 1) * pageSize, pageSize);
            iosService.sendFilecast(message, items);
            page = page + 1;
            if (items.size() < pageSize) {
                bl = false;
            }
        }

        bl = true;
        page = 1;
        while (bl) {
            List<String> items = deviceTokenService.findByAllDeviceToken(2, (page - 1) * pageSize, pageSize);
            umengAndroidService.sendFilecast(message, items);
            page = page + 1;
            if (items.size() < pageSize) {
                bl = false;
            }
        }
        return ResponseUtil.success("success");
    }

    /**
     * 广播消息，友盟发送所有记录的设备
     *
     * @param msgId 消息id
     * @return messageResponse
     */
    @RequestMapping("/send/broadcast")
    public MessageResponse sendBroadcast(int msgId) {
        // 获取消息内容
        AnimationMessageEntity message = messageService.findById(msgId);
        if (message == null || message.getId() <= 0) {
            return ResponseUtil.success("no message send");
        }

        // iOS 设备
        iosService.sendBroadcast(message);
        // android设备
        umengAndroidService.sendBroadcast(message);

        return ResponseUtil.success("success");
    }
}
