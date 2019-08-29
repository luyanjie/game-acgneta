package com.tongbu.game.service;

import com.alibaba.fastjson.JSON;
import com.tongbu.game.dao.AnimationMessageMapper;
import com.tongbu.game.entity.AnimationMessageEntity;
import com.tongbu.game.entity.Request.AnimationMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.xinba.core.entity.MessageResponse;

/**
 * @author jokin
 * @date 2018/10/9 10:42
 */
@Service
public class AnimationMessageService {

    @Autowired
    AnimationMessageMapper messageMapper;

    public AnimationMessageEntity findById(int msgId) {
        // id 大于10,000,000 为所有用户信息
        return msgId < 10000000 ? messageMapper.findById(msgId) : messageMapper.findByIdAllUser(msgId);
    }

    public MessageResponse<String> insert(AnimationMessageRequest request) {
        System.out.println(JSON.toJSON(request));
        int count = messageMapper.insertMessage(request);
        System.out.println(request.getId());

        return new MessageResponse<>(count > 0 ? "0" : "1", count > 0 ? "success" : "fail", "");
    }
}
