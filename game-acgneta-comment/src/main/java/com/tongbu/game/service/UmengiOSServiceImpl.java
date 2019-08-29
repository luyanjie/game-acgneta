package com.tongbu.game.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongbu.game.common.HtmlRegexpUtil;
import com.tongbu.game.common.UmengCustomized;
import com.tongbu.game.common.constant.UmengConstant;
import com.tongbu.game.entity.AnimationMessageEntity;
import com.tongbu.game.service.umeng.IOSNotification;
import com.tongbu.game.service.umeng.PushClient;
import com.tongbu.game.service.umeng.ios.IOSBroadcast;
import com.tongbu.game.service.umeng.ios.IOSFilecast;
import com.tongbu.game.service.umeng.ios.IOSUnicast;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import vip.xinba.core.constant.ResponseCodeEnum;
import vip.xinba.core.entity.MessageResponse;
import vip.xinba.core.exception.ExceptionUtil;
import vip.xinba.core.exception.ServiceException;

import java.util.List;

/**
 * @author jokin
 * @date 2018/9/28 17:11
 */
@Service(value = "umengiOSService")
public class UmengiOSServiceImpl implements IUmengService {

    private static final Logger log = LoggerFactory.getLogger(UmengiOSServiceImpl.class);
    /**
     * 设置iOS umeng 环境
     */
    @Value("${umeng.ios.mode}")
    private boolean mode;

    @Async
    @Override
    public void sendUnicast(AnimationMessageEntity message, String deviceToken) {

        MessageResponse response = new MessageResponse<String>();
        if (message.getToUid() <= 0) {
            // "no uid comments"
            response.setCode(ResponseCodeEnum.SYS_PARAM_NOT_RIGHT.getCode());
            response.setCode(ResponseCodeEnum.SYS_PARAM_NOT_RIGHT.getMsg());
            response.setMsg("no uid comments");
            return;
        }
        try {
            IOSUnicast unicast = new IOSUnicast(UmengConstant.IOS.APPKEY, UmengConstant.IOS.APP_MASTER_SECRET);
            unicast.setDeviceToken(deviceToken);
            send(unicast, message);

            /*// 屏幕弹出的内容(Alert 使用json格式，也可以直接使用字符串)
            JSONObject jsonAlert = new JSONObject();

            if (message.getGrade() >= GRADE || message.getSource() == 1) {
                jsonAlert.put("title", message.getTitle());
            } else {
                jsonAlert.put("title", UmengConstant.getUmengTitle().get(StringUtils.join(UmengConstant.TITLE, String.valueOf(message.getSource()))));
            }

            jsonAlert.put("subtitle", "");
            jsonAlert.put("body", HtmlRegexpUtil.filterHtml(StringEscapeUtils.unescapeHtml4(message.getContent())));
            unicast.setAlert(jsonAlert);
            unicast.setBadge(0);
            // 描述信息，官方建议填写
            unicast.setDescription(UmengConstant.getUmengDescription().get(message.getSource()));
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
            unicast.setCustomizedField(UmengConstant.CUSTOMZIED_FIELD_CONTENT, "");
            // 跳转信息
            unicast.setCustomizedField(UmengConstant.CUSTOMZIED_FIELD_TYPE_SOURCE,
                    UmengCustomized.field(message.getPushModule(), message.getTypeId(), message.getSource()));
            // {"ret":"SUCCESS","data":{"msg_id":"uujt5gw153810170011200"}}
            String responseContent = PushClient.send(unicast);
            *//*return StringUtils.isEmpty(responseContent)
                    ? "fail"
                    : String.valueOf(JSON.parseObject(responseContent).get("ret"));*/
        } catch (Exception e) {
            log.error("iOS sendUnicast", e);
            ServiceException serviceException = (ServiceException) ExceptionUtil.handlerException4biz(e);
            response.setCode(serviceException.getErrorCode());
            response.setMsg(serviceException.getErrorMessage());
        }
    }

    @Async
    @Override
    public void sendFilecast(AnimationMessageEntity message, List<String> deviceTokens) {
        try {
            if (deviceTokens == null || deviceTokens.isEmpty()) {
                return;
            }
            IOSFilecast filecast = new IOSFilecast(UmengConstant.IOS.APPKEY, UmengConstant.IOS.APP_MASTER_SECRET);
            // 拼接deviceToken 以“\n” 隔开
            StringBuilder sb = new StringBuilder();
            deviceTokens.forEach(x -> sb.append(x).append("\n"));
            String fileId = PushClient.uploadContents(UmengConstant.IOS.APPKEY, UmengConstant.IOS.APP_MASTER_SECRET, sb.toString().substring(0, sb.lastIndexOf("\n")));
            filecast.setFileId(fileId);

            send(filecast, message);
            /*// 屏幕弹出的内容(Alert 使用json格式，也可以直接使用字符串)
            JSONObject jsonAlert = new JSONObject();
            if (message.getGrade() >= GRADE || message.getSource() == 1) {
                jsonAlert.put("title", message.getTitle());
            } else {
                jsonAlert.put("title", UmengConstant.getUmengTitle().get(StringUtils.join(UmengConstant.TITLE, String.valueOf(message.getSource()))));
            }
            jsonAlert.put("subtitle", "");
            jsonAlert.put("body", HtmlRegexpUtil.filterHtml(StringEscapeUtils.unescapeHtml4(message.getContent())));
            filecast.setAlert(jsonAlert);

            filecast.setBadge(0);
            // 描述信息，官方建议填写
            filecast.setDescription(UmengConstant.getUmengDescription().get(message.getSource()));
            filecast.setSound(UmengConstant.IOS.SOUND);

            // 设置环境
            if (mode) {
                filecast.setProductionMode();
            } else {
                filecast.setTestMode();
            }
            // 内容
            filecast.setCustomizedField(UmengConstant.CUSTOMZIED_FIELD_CONTENT, "");
            // 跳转信息
            filecast.setCustomizedField(UmengConstant.CUSTOMZIED_FIELD_TYPE_SOURCE,
                    UmengCustomized.field(message.getPushModule(), message.getTypeId(), message.getSource()));
            log.info(StringUtils.join("iOS sendFilecast", "\nmessage:",
                    JSON.toJSONString(message)), "\ndeviceTokens:", JSON.toJSONString(deviceTokens), "\nfileId:", fileId);
            PushClient.send(filecast);*/
        } catch (Exception e) {
            log.error(StringUtils.join("iOS sendFilecast", "\nmessage:",
                    JSON.toJSONString(message)), "\ndeviceTokens", JSON.toJSONString(deviceTokens),
                    e);
        }
    }

    @Override
    public void sendBroadcast(AnimationMessageEntity message) {
        try {
            IOSBroadcast broadcast = new IOSBroadcast(UmengConstant.IOS.APPKEY, UmengConstant.IOS.APP_MASTER_SECRET);
            send(broadcast, message);
        } catch (Exception e) {
            log.error(StringUtils.join("iOS sendFilecast", "\nmessage:",
                    JSON.toJSONString(message)),
                    e);
        }
    }

    private void send(IOSNotification notification,AnimationMessageEntity message) throws Exception {
        // 屏幕弹出的内容(Alert 使用json格式，也可以直接使用字符串)
        JSONObject jsonAlert = new JSONObject();
        if (message.getGrade() >= GRADE || message.getSource() == 1) {
            jsonAlert.put("title", message.getTitle());
        } else {
            jsonAlert.put("title", UmengConstant.getUmengTitle().get(StringUtils.join(UmengConstant.TITLE, String.valueOf(message.getSource()))));
        }
        jsonAlert.put("subtitle", "");
        jsonAlert.put("body", HtmlRegexpUtil.filterHtml(StringEscapeUtils.unescapeHtml4(message.getContent())));
        notification.setAlert(jsonAlert);

        notification.setBadge(0);
        // 描述信息，官方建议填写
        notification.setDescription(UmengConstant.getUmengDescription().get(message.getSource()));
        notification.setSound(UmengConstant.IOS.SOUND);
        // 设置环境
        if (mode) {
            notification.setProductionMode();
        } else {
            notification.setTestMode();
        }
        // 内容
        notification.setCustomizedField(UmengConstant.CUSTOMZIED_FIELD_CONTENT, "");
        // 跳转信息
        notification.setCustomizedField(UmengConstant.CUSTOMZIED_FIELD_TYPE_SOURCE,
                UmengCustomized.field(message.getPushModule(), message.getTypeId(), message.getSource()));
        PushClient.send(notification);
    }
}
