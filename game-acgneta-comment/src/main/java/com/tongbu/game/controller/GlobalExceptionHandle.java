package com.tongbu.game.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import vip.xinba.core.entity.MessageResponse;
import vip.xinba.core.exception.ExceptionUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jokin
 * @date 2018/10/29 11:16
 * <p>
 * 设置全局异常返回
 */
@ControllerAdvice
public class GlobalExceptionHandle {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public <T> MessageResponse<T> handle(HttpServletRequest request, Exception e) {
        return ExceptionUtil.handle(request,e);
    }
}
