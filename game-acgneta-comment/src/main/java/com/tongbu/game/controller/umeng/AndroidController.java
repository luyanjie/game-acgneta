package com.tongbu.game.controller.umeng;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jokin
 * @date 2018/9/28 17:05
 */
@RestController
@RequestMapping("/umeng/android")
public class AndroidController {

    @RequestMapping("/send/unicast")
    public String sendUnicast(int commentId)
    {
        return "";
    }
}
