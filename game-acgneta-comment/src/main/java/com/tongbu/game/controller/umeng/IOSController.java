package com.tongbu.game.controller.umeng;

import com.tongbu.game.service.IUmengService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jokin
 * @date 2018/9/28 17:34
 */

@RestController
@RequestMapping("/umeng/ios")
public class IOSController {

    @Autowired
    @Qualifier(value = "umengiOSService")
    IUmengService umengService;

    @RequestMapping("/send/unicast")
    public String sendUnicast(int type, int commentId) {
        return umengService.sendUnicast(type, commentId);
    }
}
