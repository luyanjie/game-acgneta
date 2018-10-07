package com.tongbu.game.service;

import com.alibaba.fastjson.JSONObject;
import com.tongbu.game.common.constant.UmengConstant;
import com.tongbu.game.service.umeng.android.AndroidUnicast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author jokin
 * @date 2018/9/28 17:12
 */
@Service
public class UmengAndroidService implements IUmengService
{
    private static final Logger log = LoggerFactory.getLogger(UmengAndroidService.class);
    @Override
    public String sendUnicast(JSONObject jsonObject) {
        try {
            AndroidUnicast unicast = new AndroidUnicast(UmengConstant.IOS.APPKEY,UmengConstant.IOS.APP_MASTER_SECRET);

            return "success;";
        }
        catch (Exception e){
            log.error("android sendUnicast",e);
        }
        return "fail";
    }
}
