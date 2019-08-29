package com.tongbu.game.service;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ConsumerDynamicInput {

    /**
     * 消息的订阅通道名称
     */
    String INPUT = "dynamicInput";

    /**
     * 收消息的通道
     * @return
     */
    @Input(INPUT)
    SubscribableChannel input();
}
