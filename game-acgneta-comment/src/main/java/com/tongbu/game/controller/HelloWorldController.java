package com.tongbu.game.controller;

import com.tongbu.game.common.ResponseUtil;
import com.tongbu.game.entity.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author jokin
 * @date 2018/10/8 16:54
 */
@RestController
public class HelloWorldController {

    private static final Logger log = LoggerFactory.getLogger(HelloWorldController.class);

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
}
