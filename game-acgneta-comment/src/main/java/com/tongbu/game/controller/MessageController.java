package com.tongbu.game.controller;

import com.alibaba.fastjson.JSON;
import com.tongbu.game.entity.MessageResponse;
import com.tongbu.game.entity.Request.AnimationMessageRequest;
import com.tongbu.game.service.AnimationMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jokin
 * @date 2018/10/24 15:01
 */

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    AnimationMessageService service;

    /**
     * 发送消息
     * */
    @RequestMapping("/send")
    public MessageResponse send(AnimationMessageRequest request)
    {
        return service.insert(request);
    }
}
