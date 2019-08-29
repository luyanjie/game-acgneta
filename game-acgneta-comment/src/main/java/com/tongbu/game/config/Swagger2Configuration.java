package com.tongbu.game.config;

import com.google.common.base.Predicate;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;


/**
 * Restful API 访问路径:
 * http://IP:port/{context-path}/swagger-ui.html
 * eg:http://localhost:8080/jd-config-web/swagger-ui.html
 */
@Configuration// 配置注解，自动在本类上下文加载一些环境变量信息
@EnableSwagger2// 使swagger2生效
public class Swagger2Configuration {

    private static final String BASEPACKAGE = "com.tongbu.game.controller";

    @Value("${swagger.enable}")
    private boolean enableSwagger;

    @Bean
    public Docket docket() {

        Predicate<RequestHandler> predicate = new Predicate<RequestHandler>() {
            @Override
            public boolean apply(RequestHandler input) {
                //只有添加了ApiOperation注解的method才在API中显示
                return input.isAnnotatedWith(ApiOperation.class);
            }
        };

        // 过滤方案参考：https://www.daxiblog.com/swagger%E4%B8%AD%E8%BF%87%E6%BB%A4%E6%8E%89%E4%BB%BB%E6%84%8Fapi%E6%8E%A5%E5%8F%A3%E7%9A%84%E6%96%B9%E6%B3%95/
        // https://blog.csdn.net/sinat_27639721/article/details/81218551

        return new Docket(DocumentationType.SWAGGER_2)
                // 是否启用，可以设置
                .enable(enableSwagger)
                .apiInfo(apiInfo())
                .groupName("DefaultApi")
                .select()
                .apis(predicate) // 设置显示API的过滤条件
                .apis(RequestHandlerSelectors.basePackage(BASEPACKAGE)) // 设置扫描包
                .paths(PathSelectors.any())

                .build()
                .securitySchemes(apiKey())
                .securityContexts(securityContexts());
    }

    /**
     * 配置描述信息
     *
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("服务: acgneta 评论 APIs")
                .description("服务:acgneta 评论")
                .termsOfServiceUrl("http://192.168.40.155:8081/")
                .version("1.0")
                .contact(new Contact("jokin", "", ""))
                .build();
        /*
        title=标题
        description=描述
        version=版本
        license=许可证
        licenseUrl=许可证URL
        termsOfServiceUrl=服务条款URL
        contact=维护人信息 有名称、url、email
        */
    }

    /**
     * Swagger2 全局、无需重复输入的Head参数配置
     *
     * 可以在界面 设置
     *
     * http://mao-java.oss-cn-hangzhou.aliyuncs.com/swagger/20190411005.png
     *
     * @return List<SecurityScheme>
     */
    private List<SecurityScheme> apiKey() {
        return Collections.singletonList(new ApiKey("access_token", "accessToken", "header"));
    }
    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        // 非auth 认证接口有效（所有包含”auth”的接口不需要使用securitySchemes）
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build()
        );
    }
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(
                new SecurityReference("access_token", authorizationScopes));
    }

}
