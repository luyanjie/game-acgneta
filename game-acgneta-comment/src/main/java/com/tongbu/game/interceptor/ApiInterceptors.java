package com.tongbu.game.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * @author jokin
 * @date 2018/10/8 16:50
 */
@Component
public class ApiInterceptors implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ApiInterceptors.class);

    /**
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，
     * SpringMVC中的Interceptor拦截器是链式的，可以同时存在多个Interceptor，
     * 然后SpringMVC会根据声明的前后顺序一个接一个的执行，
     * 而且所有的Interceptor中的preHandle方法都会在Controller方法调用之前调用。
     * SpringMVC的这种Interceptor链式结构也是可以进行中断的，
     * 这种中断方式是令preHandle的返回值为false，当preHandle的返回值为false的时候整个请求就结束了。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        //比如在访问不是指定路径/user/login的时候进行拦截帕判断
//        if(!request.getRequestURI().equals("/user/login"))
//        {
//            //判断session是否存在
//            Object obj = request.getSession().getAttribute("session_user");
//            if(null == obj)
//            {
//                //不存在session 跳转到登陆页面
//                //response.sendRedirect("/user/login_view");
//                //或者直接返回错误信息
//                response.getOutputStream().write("no login".getBytes());
//                return false;
//            }
//        }
        requestLog(request);

        // 记录开始时间
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        //只有返回true才会继续向下执行，返回false取消当前请求
        return true;
    }

    void requestLog(HttpServletRequest request) {
        //请求log记录
        StringBuilder sb = new StringBuilder();
        //请求url
        sb.append("ip:").append(request.getRemoteAddr()).append("\n");
        sb.append("url:").append(request.getRequestURI()).append("\n");
        //请求类型
        sb.append("method:").append(request.getMethod()).append("\n");
        sb.append("QueryString:").append(request.getQueryString()).append("\n");
        Enumeration<String> paras = request.getParameterNames();
        while (paras.hasMoreElements()) {
            String name = paras.nextElement();
            String value = request.getParameter(name);
            sb.append(name).append(":").append(value).append("\n");
        }
        log.info(sb.toString());
    }

    /**
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。
     * postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之
     * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行，
     * 也就是说在这个方法中你可以对ModelAndView进行操
     * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用，
     * 这跟Struts2里面的拦截器的执行过程有点像，只是Struts2里面的intercept方法中
     * 要手动的调用ActionInvocation的invoke方法，Struts2中调用ActionInvocation的invoke方法
     * 就是调用下一个Interceptor或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前，
     * 要在Interceptor之后调用的内容都写在调用invoke方法之后。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        if (handler instanceof HandlerMethod) {
            log.info("CostTime  : " + executeTime + "ms" + "\n");
        }
    }

    /**
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。
     * 该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行，
     * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的
     * 返回值为true时才会执行。
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
