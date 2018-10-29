package com.tongbu.game.common;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author jokin
 * @date 2018/10/29 15:20
 */
public class RequestUtil {
    /**
     *  获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     *
     * 用户真实IP为： 192.168.1.110
     *
     * @param request request
     * @return String
     * */
    public static String getIpAddress(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static void resources(HttpServletRequest request, JSONObject json){
        //获得客户端发送请求的完整url
        json.put("url",request.getRequestURL().toString());
        //返回发出请求的IP地址
        json.put("ip",getIpAddress(request));
        //返回发出请求的客户机的主机名
        json.put("host",request.getRemoteHost());
        //返回发出请求的客户机的端口号。
        json.put("port",request.getRemotePort());
        // 请求方式
        json.put("method",request.getMethod());
        JSONObject parameter = new JSONObject();
        Enumeration<String> paras = request.getParameterNames();
        while (paras.hasMoreElements()) {

            String name = paras.nextElement();
            parameter.put(name,request.getParameter(name));
        }
        // 请求参数
        json.put("parameter",parameter);
    }
}
