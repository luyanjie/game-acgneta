package com.tongbu.game.controller;

import com.tongbu.game.entity.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jokin
 * @date 2018/10/8 16:54
 */
@RestController
public class HelloWorldController {

    private static final Logger log = LoggerFactory.getLogger(HelloWorldController.class);

    @RequestMapping("/")
    public MessageResponse start()
    {
        return new MessageResponse<>("0", "success", "are you ok?");
    }

    @RequestMapping("/hello")
    public MessageResponse hello()
    {
        log.info("hello info");
        log.warn("hello warn");
        log.error("hello error");
        return new MessageResponse<>("0", "success", "hello world!");
    }

    @RequestMapping("/time")
    public MessageResponse currentTime()
    {
        return new MessageResponse<>("0", "success", System.currentTimeMillis());
    }
}
