package com.tongbu.game.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xinba.core.ResponseUtil;
import vip.xinba.core.entity.MessageResponse;

/**
 * @author jokin
 * @date 2018/10/8 16:54
 */
@RestController
public class HelloWorldController {

    private static final Logger log = LoggerFactory.getLogger(HelloWorldController.class);

//    @Autowired
//    DynamicOutputService dynamic;

    @RequestMapping("/")
    public MessageResponse start() {
        return ResponseUtil.success("are you ok?");
    }

    @RequestMapping("/hello")
    public MessageResponse hello() {
        log.info("hello info");
        log.warn("hello warn");


        log.error("hello error");
        return ResponseUtil.success("hello world!");
    }

    @RequestMapping("/time")
    public MessageResponse currentTime() {
        return ResponseUtil.success(System.currentTimeMillis());
    }


//    @RequestMapping("/send")
//    public MessageResponse send() {
//        for (int i = 0; i < 10; i++) {
//
//            try {
//                Thread.sleep(5 * 100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            dynamic.sendMessage(1000318 + ":" + i);
//        }
//        return ResponseUtil.success(true);
//    }
}
