package com.tongbu.game.service;

import com.tongbu.game.common.UmengCustomized;
import com.tongbu.game.common.constant.ResponseCodeEnum;
import com.tongbu.game.common.constant.UmengConstant;
import com.tongbu.game.common.exception.ExceptionUtil;
import com.tongbu.game.common.exception.ServiceException;
import com.tongbu.game.entity.AnimationMessageEntity;
import com.tongbu.game.entity.MessageResponse;
import com.tongbu.game.service.umeng.PushClient;
import com.tongbu.game.service.umeng.ios.IOSUnicast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
    private static boolean mode;

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

            //unicast.setDeviceToken("a5e8266a23a90e44cb35b1e8505cc34a7039745d339fa494e09243dac7146fe4");
            unicast.setDeviceToken(deviceToken);
            // 屏幕弹出的内容
            unicast.setAlert(message.getTitle());
            unicast.setBadge(0);
            // 描述信息，官方建议填写
            unicast.setDescription(message.getSource() == 2 ? "点赞" : "新的回复");
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
                    UmengCustomized.field(message.getTypeSource(), message.getTypeId(), message.getCommentId()));
            // {"ret":"SUCCESS","data":{"msg_id":"uujt5gw153810170011200"}}
            String responseContent = PushClient.send(unicast);
            /*return StringUtils.isEmpty(responseContent)
                    ? "fail"
                    : String.valueOf(JSON.parseObject(responseContent).get("ret"));*/
        } catch (Exception e) {
            log.error("iOS sendUnicast", e);
            ServiceException serviceException = (ServiceException) ExceptionUtil.handlerException4biz(e);
            response.setCode(serviceException.getErrorCode());
            response.setMsg(serviceException.getErrorMessage());
        }
    }
}
