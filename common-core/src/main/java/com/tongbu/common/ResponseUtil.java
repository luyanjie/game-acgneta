package com.tongbu.common;

import com.tongbu.common.entity.MessageResponse;

/**
 * @author jokin
 * @date 2018/10/29 11:02
 * <p>
 * 统一结果返回
 */
public class ResponseUtil {

    /**
     * 成功返回
     *
     * @param data 指定输出内容
     * @return 返回实体类
     */
    public static <T> MessageResponse<T> success(T data) {
        return commonResponse("0", "success", data);
    }

    /**
     * 成功指定返回
     * @param code 状态码
     * @param data 指定数据内容
     * @param message 状态信息描述
     *
     * @return 返回实体类
     * */
    public static <T> MessageResponse<T> success(String code, String message, T data) {
        return commonResponse(code, message, data);
    }

    /**
     * 异常返回
     * @param code 状态码
     * @param message 状态信息描述
     *
     * @return 返回实体类
     * */
    public static <T> MessageResponse<T> error(String code, String message) {
        return commonResponse(code, message, null);
    }

    private static <T> MessageResponse<T> commonResponse(String code, String message, T data) {
        MessageResponse<T> response = new MessageResponse<>();
        response.setCode(code);
        response.setMsg(message);
        response.setData(data);
        return response;
    }
}
