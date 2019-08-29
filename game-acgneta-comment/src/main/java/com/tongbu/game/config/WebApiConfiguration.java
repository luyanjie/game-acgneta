package com.tongbu.game.config;

import com.tongbu.game.interceptor.ApiInterceptors;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author jokin
 * @date 2018/10/8 16:43
 */
@Configuration
public class WebApiConfiguration implements WebMvcConfigurer {

    /**
     * 配置拦截器
     * 将LoginInterceptor拦截器添加到SpringBoot的配置中
     * */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        //registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");//对来自跟目录下的都进行拦截，如果是/user/**  对/user/** 这个链接来的请求进行拦截  使用Ant 通配符匹配规则
        registry.addInterceptor(new ApiInterceptors()).addPathPatterns("/**");
    }

    public void  addResourceHandlers(ResourceHandlerRegistry registry)
    {
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        // Spring Boot自动配置本身不会自动把/swagger-ui.html这个路径映射到对应的目录META-INF/resources/下面。我们加上这个映射即可
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
                .allowCredentials(true).maxAge(3600);
    }
}
