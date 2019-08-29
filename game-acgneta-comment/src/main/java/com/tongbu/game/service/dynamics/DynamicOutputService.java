package com.tongbu.game.service.dynamics;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.messaging.support.MessageBuilder;
//
//@EnableBinding(DynamicOutput.class)
//public class DynamicOutputService {
//
//    @Autowired
//    DynamicOutput output;
//
//    /**
//     * 发送消息到消息队列
//     * @param message 消息内容
//     * @return 是否成功
//     */
//    public boolean sendMessage(String message) {
//        return output.myOutput().send(MessageBuilder.withPayload(message).build());
//    }
//}
