package com.tongbu.game.service;

import com.alibaba.fastjson.JSONObject;
import com.tongbu.game.entity.AnimationMessageEntity;

/**
 * @author jokin
 * @date 2018/9/28 17:13
 */
public interface IUmengService {
    String sendUnicast(AnimationMessageEntity message,String deviceToken);
}
