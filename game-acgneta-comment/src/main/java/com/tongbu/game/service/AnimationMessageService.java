package com.tongbu.game.service;

import com.tongbu.game.dao.AnimationMessageMapper;
import com.tongbu.game.entity.AnimationMessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jokin
 * @date 2018/10/9 10:42
 */
@Service
public class AnimationMessageService {

    @Autowired
    AnimationMessageMapper messageMapper;

    public AnimationMessageEntity findById(int msgId)
    {
        AnimationMessageEntity message = messageMapper.findById(msgId);
        return message;
    }
}
