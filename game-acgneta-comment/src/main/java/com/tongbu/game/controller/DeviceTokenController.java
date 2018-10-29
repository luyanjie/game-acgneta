package com.tongbu.game.controller;

import com.tongbu.game.common.ResponseUtil;
import com.tongbu.game.entity.MessageResponse;
import com.tongbu.game.service.DeviceTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jokin
 * @date 2018/9/29 16:04
 */
@RestController
@RequestMapping("/device/token")
public class DeviceTokenController {

    @Autowired
    DeviceTokenService service;

    @RequestMapping("/find/one")
    public MessageResponse findByUidAndToken(int uid, String deviceToken)
    {
        return ResponseUtil.success(service.findByUidAndToken(uid,deviceToken));
    }

    @RequestMapping("/insert")
    public MessageResponse insert(int uid,
                      String deviceToken,
                      @RequestParam(value = "appSource",required = false,defaultValue = "1") int appSource){
        return ResponseUtil.success(service.insert(uid,deviceToken,appSource));
    }
}
