package com.tongbu.game.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongbu.game.common.RequestUtil;
import com.tongbu.game.common.ResponseUtil;
import com.tongbu.game.common.exception.ServiceException;
import com.tongbu.game.entity.MessageResponse;
import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

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
        JSONObject json = new JSONObject();
        RequestUtil.resources(request, json);
        logger.error(JSON.toJSONString(json), e);
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            // 资源未找到 404
            return ResponseUtil.error(String.valueOf(Response.SC_NOT_FOUND), e.getMessage());
        }
        if (e instanceof ServiceException) {
            // 104 为 前端和后端约定好的返回码
            String code = "104";
            ServiceException exception = (ServiceException) e;
            if (!Objects.equals(exception.getErrorCode(), "0")) {
                code = exception.getErrorCode();
            }
            return ResponseUtil.error(code, e.getMessage());
        }
        return ResponseUtil.error(String.valueOf(Response.SC_INTERNAL_SERVER_ERROR),
                e.getMessage() == null
                        ? "服务器内部错误"
                        : e.getMessage());
    }
}
