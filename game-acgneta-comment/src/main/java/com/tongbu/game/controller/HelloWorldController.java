package com.tongbu.game.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jokin
 * @date 2018/10/8 16:54
 */
@RestController
public class HelloWorldController {

    @RequestMapping("/hello")
    public String hello()
    {
        return "hello world!";
    }
}
