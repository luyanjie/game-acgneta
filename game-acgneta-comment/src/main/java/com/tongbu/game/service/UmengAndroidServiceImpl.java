package com.tongbu.game.service;

import com.alibaba.fastjson.JSON;
import com.tongbu.game.common.HtmlRegexpUtil;
import com.tongbu.game.common.UmengCustomized;
import com.tongbu.game.common.constant.UmengConstant;
import com.tongbu.game.entity.AnimationMessageEntity;
import com.tongbu.game.service.umeng.AndroidNotification;
import com.tongbu.game.service.umeng.PushClient;
import com.tongbu.game.service.umeng.android.AndroidBroadcast;
import com.tongbu.game.service.umeng.android.AndroidFilecast;
import com.tongbu.game.service.umeng.android.AndroidUnicast;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jokin
 * @date 2018/9/28 17:12
 */
@Service(value = "umengAndroidService")
public class UmengAndroidServiceImpl implements IUmengService {
    private static final Logger log = LoggerFactory.getLogger(UmengAndroidServiceImpl.class);

    @Async
    @Override
    public void sendUnicast(AnimationMessageEntity message, String deviceToken) {

        if (message.getToUid() <= 0) {
            // "no uid comments"
            return;
        }
        try {
            AndroidUnicast cast = new AndroidUnicast(UmengConstant.Android.APPKEY, UmengConstant.Android.APP_MASTER_SECRET);
            // TODO Set your device token
            cast.setDeviceToken(deviceToken);
            cast.setIcon("R.drawable.ic_notify_logo");
            send(cast, message);
           /* cast.setTicker("Android unicast ticker");
            // 当是后台运营添加时 使用运营填写的title
            if (message.getGrade() >= GRADE || message.getSource() == 1) {
                cast.setTitle(message.getTitle());
            } else {
                cast.setTitle(UmengConstant.getUmengTitle().get(StringUtils.join(UmengConstant.TITLE, String.valueOf(message.getSource()))));
            }
            cast.setText(HtmlRegexpUtil.filterHtml(StringEscapeUtils.unescapeHtml4(message.getContent())));
            cast.goAppAfterOpen();
            cast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
            cast.setProductionMode();
            // 内容
            cast.setExtraField(UmengConstant.CUSTOMZIED_FIELD_CONTENT, "");
            // 跳转信息
            cast.setExtraField(UmengConstant.CUSTOMZIED_FIELD_TYPE_SOURCE,
                    UmengCustomized.field(message.getPushModule(), message.getTypeId(), message.getSource()));
            PushClient.send(cast);*/

        } catch (Exception e) {
            log.error("android sendUnicast", e);
        }
    }

    @Async
    @Override
    public void sendFilecast(AnimationMessageEntity message, List<String> deviceTokens) {

        try {
            if (deviceTokens == null || deviceTokens.isEmpty()) {
                return;
            }
            AndroidFilecast cast = new AndroidFilecast(UmengConstant.Android.APPKEY, UmengConstant.Android.APP_MASTER_SECRET);
            // 多个发送已“\n” 隔开

            StringBuilder sb = new StringBuilder();
            deviceTokens.forEach(x -> sb.append(x).append("\n"));
            String fileId = PushClient.uploadContents(UmengConstant.Android.APPKEY, UmengConstant.Android.APP_MASTER_SECRET, sb.toString().substring(0, sb.lastIndexOf("\n")));
            cast.setFileId(fileId);
            send(cast, message);

            /*
            // 当是后台运营添加时 使用运营填写的title
            if (message.getGrade() >= GRADE || message.getSource() == 1) {
                cast.setTitle(message.getTitle());
            }
            // 所有用户
            else {
                cast.setTitle(UmengConstant.getUmengTitle().get(StringUtils.join(UmengConstant.TITLE, String.valueOf(message.getSource()))));
            }
            cast.setText(HtmlRegexpUtil.filterHtml(StringEscapeUtils.unescapeHtml4(message.getContent())));
            cast.goAppAfterOpen();
            cast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
            cast.setProductionMode();
            // 内容
            cast.setExtraField(UmengConstant.CUSTOMZIED_FIELD_CONTENT, "");
            // 跳转信息
            cast.setExtraField(UmengConstant.CUSTOMZIED_FIELD_TYPE_SOURCE,
                    UmengCustomized.field(message.getPushModule(), message.getTypeId(), message.getSource()));
            PushClient.send(cast);*/
        } catch (Exception e) {
            log.error("android sendAndroidFilecast", e);
        }

    }

    @Override
    public void sendBroadcast(AnimationMessageEntity message) {
        try {
            AndroidBroadcast cast = new AndroidBroadcast(UmengConstant.Android.APPKEY, UmengConstant.Android.APP_MASTER_SECRET);
            send(cast, message);
        } catch (Exception e) {
            log.error("android sendBroadcast", e);
        }
    }

    private void send(AndroidNotification notification, AnimationMessageEntity message) throws Exception {
        // 当是后台运营添加时 使用运营填写的title
        notification.setTicker("Android unicast ticker");
        if (message.getGrade() >= GRADE || message.getSource() == 1) {
            notification.setTitle(message.getTitle());
        }
        // 所有用户
        else {
            notification.setTitle(UmengConstant.getUmengTitle().get(StringUtils.join(UmengConstant.TITLE, String.valueOf(message.getSource()))));
        }

        notification.setText(HtmlRegexpUtil.filterHtml(StringEscapeUtils.unescapeHtml4(message.getContent())));
        notification.goAppAfterOpen();
        notification.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        notification.setProductionMode();
        // 可选，默认为false。当为true时，表示MIUI、EMUI、Flyme系统设备离线转为系统下发
        notification.setPredefinedKeyValue("mipush", true);
        //  可选，mipush值为true时生效，表示走系统通道时打开指定页面acitivity的完整包路径。
        notification.setPredefinedKeyValue("mi_activity", "scholar.acgneta.com.acgneta.base.MipushActivity");
        // 内容
        notification.setExtraField(UmengConstant.CUSTOMZIED_FIELD_CONTENT, "");
        // 跳转信息
        notification.setExtraField(UmengConstant.CUSTOMZIED_FIELD_TYPE_SOURCE,
                UmengCustomized.field(message.getPushModule(), message.getTypeId(), message.getSource()));

        System.out.println(JSON.toJSONString(notification.getPostBody()));
        PushClient.send(notification);
    }

}
