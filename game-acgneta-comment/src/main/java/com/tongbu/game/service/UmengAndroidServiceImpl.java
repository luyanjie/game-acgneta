package com.tongbu.game.service;

import com.tongbu.game.common.UmengCustomized;
import com.tongbu.game.common.constant.UmengConstant;
import com.tongbu.game.entity.AnimationMessageEntity;
import com.tongbu.game.service.umeng.AndroidNotification;
import com.tongbu.game.service.umeng.PushClient;
import com.tongbu.game.service.umeng.android.AndroidUnicast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author jokin
 * @date 2018/9/28 17:12
 */
@Service(value = "umengAndroidService")
public class UmengAndroidServiceImpl implements IUmengService
{
    private static final Logger log = LoggerFactory.getLogger(UmengAndroidServiceImpl.class);

    @Async
    @Override
    public void sendUnicast(AnimationMessageEntity message, String deviceToken) {

        if (message.getToUid() <= 0) {
            // "no uid comments"
            return;
        }

        try {
            AndroidUnicast unicast = new AndroidUnicast(UmengConstant.IOS.APPKEY,UmengConstant.IOS.APP_MASTER_SECRET);

            // TODO Set your device token
            unicast.setDeviceToken(deviceToken);
            unicast.setTicker("Android unicast ticker");
            unicast.setTitle(message.getTitle());
            unicast.setText(message.getContent());
            unicast.goAppAfterOpen();
            unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
            // TODO Set 'production_mode' to 'false' if it's a test device.
            // For how to register a test device, please see the developer doc.
            unicast.setProductionMode();
            // Set customized fields
            // 内容
            unicast.setExtraField(UmengConstant.CUSTOMZIED_FIELD_CONTENT, "");
            // 跳转信息
            unicast.setExtraField(UmengConstant.CUSTOMZIED_FIELD_TYPE_SOURCE,
                    UmengCustomized.field(message.getTypeSource(), message.getTypeId(), message.getCommentId()));

            // {"ret":"SUCCESS","data":{"msg_id":"uujt5gw153810170011200"}}
            String response = PushClient.send(unicast);
           /* return StringUtils.isEmpty(response)
                    ? "fail"
                    : String.valueOf(JSON.parseObject(response).get("ret"));*/

        }
        catch (Exception e){
            log.error("android sendUnicast",e);
        }
    }
}
