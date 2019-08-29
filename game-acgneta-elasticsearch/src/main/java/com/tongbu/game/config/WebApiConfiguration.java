package com.tongbu.game.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author jokin
 * @date 2018/12/14 10:57
 */
@Configuration
public class WebApiConfiguration  implements WebMvcConfigurer {
    /**
     * 自定义静态文件路径
     * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        /**
         * http://localhost:8083/static/image/swf.png
         * 实际访问的就是static/image目录下的swf.png文件
         * */
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
