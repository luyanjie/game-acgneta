package com.tongbu.game.service;

import com.tongbu.game.common.constant.MessageConstant;
import com.tongbu.game.dao.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

/**
 * 消息处理
 */
@EnableBinding(ConsumerDynamicInput.class)
public class ReceiveDynamicService {

    @Autowired
    AccountMapper mapper;

    /**
     * 处理积分
     *
     * @param message 消息体
     */
    @StreamListener(ConsumerDynamicInput.INPUT)
    public void receive(Message<String> message) {
        // 消息内容是uid:积分的拼装，比如10000:1(就是uid为10000的用户增加积分1)
        String[] str = message.getPayload().split(MessageConstant.CACHE_SCORE_LIST_SPLIT);
        if (str.length == 2) {
            mapper.updateScoreOne(Integer.valueOf(str[0]), Integer.valueOf(str[1]));
        }
    }
}
