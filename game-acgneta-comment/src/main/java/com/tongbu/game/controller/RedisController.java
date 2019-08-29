package com.tongbu.game.controller;

import com.tongbu.game.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vip.xinba.core.ResponseUtil;
import vip.xinba.core.entity.MessageResponse;

import java.util.Date;

import static com.tongbu.game.service.dynamics.IUserDynamicsService.CACHE_LOGIN_TODAY;
import static com.tongbu.game.service.dynamics.IUserDynamicsService.CACHE_SCORE_LIST_SPLIT;

@RestController
@RequestMapping("/redis")
@Api(value = "RedisController", description = "缓存Controller", tags = {"缓存处理"})
public class RedisController {

    @Autowired
    RedisService redisService;

    /**
     * curl -X GET --header 'Accept: application/json' 'http://localhost:8080/redis/remove'
     * http://localhost:8080/redis/remove
     * <p>
     * http://localhost:8080/redis/remove?key=key1
     */
    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    @ApiOperation(value = "删除指定的key", notes = "返回JSON格式")
    @ApiImplicitParam(paramType = "query", name = "key", value = "key", required = false, dataType = "string")
    public MessageResponse remove(String key) {
        if (StringUtils.isEmpty(key)) {
            // 如果没有传具体可以，清掉指定的key
            key = StringUtils.join(CACHE_LOGIN_TODAY, CACHE_SCORE_LIST_SPLIT, DateFormatUtils.format(DateUtils.addDays(new Date(), -1), "yyyyMMdd"));
        }
        redisService.del(key);
        return ResponseUtil.success(key);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "获取缓存String key的值", notes = "返回JSON格式")
    @ApiImplicitParam(paramType = "query", name = "key", value = "key", required = false, dataType = "string")
    public MessageResponse get(String key) {

        String value = redisService.get(key);
        return ResponseUtil.success(value);
    }
}
