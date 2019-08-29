package com.tongbu.common.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongbu.common.RequestUtil;
import com.tongbu.common.ResponseUtil;
import com.tongbu.common.constant.ResponseCodeEnum;
import com.tongbu.common.entity.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author jokin
 * @date 2018/10/9 16:41
 */
public class ExceptionUtil {
    private static Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);

    /**
     * 将下层抛出的异常转换为resp返回码
     *
     * @param e Exception
     * @return
     */
    public static Exception handlerException4biz(Exception e) {
        Exception ex = null;
        if (e == null) {
            return null;
        }
        if (e instanceof ValidateException) {
            ex = new ServiceException(((ValidateException) e).getErrorCode(), ((ValidateException) e).getErrorMessage());
        }else if (e instanceof Exception) {
            ex = new ServiceException(ResponseCodeEnum.SYSTEM_BUSY.getCode(),
                    ResponseCodeEnum.SYSTEM_BUSY.getMsg());
        }
        logger.error("ExceptionUtil.handlerException4biz,Exception=" + e.getMessage(), e);
        return ex;
    }

    public static <T> MessageResponse<T> handle(HttpServletRequest request, Exception e)
    {
        JSONObject json = new JSONObject();
        RequestUtil.resources(request, json);
        logger.error(JSON.toJSONString(json), e);
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            // 资源未找到 404
            return ResponseUtil.error(String.valueOf(404), e.getMessage());
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
        return ResponseUtil.error(String.valueOf(500),
                e.getMessage() == null
                        ? "服务器内部错误"
                        : e.getMessage());
    }
}
