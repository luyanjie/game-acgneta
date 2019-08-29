package com.tongbu.game.controller;

import com.tongbu.game.entity.AnimationCommentsEntity;
import com.tongbu.game.entity.SwaggerEntity;
import com.tongbu.game.service.CommentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vip.xinba.core.ResponseUtil;
import vip.xinba.core.entity.MessageResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jokin
 * @date 2018/10/17 17:24
 */
@RestController
@RequestMapping("/comment")

@Api(value = "CommentController|一个swagger注解的控制器", description = "捏他评论Controller", tags = {"捏他评论"})
public class CommentController {

    @Autowired
    CommentService commentService;

    /**
     * 获取动画评论信息
     *
     * @param id 评论id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据评论id获取评论信息", notes = "返回JSON格式")
    @ApiImplicitParam(paramType = "path", name = "id", value = "评论id", required = true, dataType = "int")
    public MessageResponse detail(@PathVariable("id") Integer id) {
        return ResponseUtil.success(commentService.detail(id));
    }

    @RequestMapping("/info")
    @ApiOperation(value = "根据输入内容返回评论信息", notes = "返回JSON格式")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "评论id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "animationId", value = "动画id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "content", value = "评论内容", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "fromUid", value = "来源用户", required = true, dataType = "int")
    })
    public MessageResponse info(Integer id, int animationId, String content, int fromUid) {
        AnimationCommentsEntity entity = new AnimationCommentsEntity();
        entity.setAnimationId(animationId);
        entity.setId(id);
        entity.setContent(content);
        entity.setFromUid(fromUid);
        return ResponseUtil.success(entity);
    }

    /**
     * curl -X GET --header 'Accept: application/json' --header 'token: header' 'http://localhost:8080/comment/header/swagger?id=2&name=%E5%8D%A2%E7%82%8E%E6%9D%B0'
     * http://mao-java.oss-cn-hangzhou.aliyuncs.com/swagger/20190411003.png
     *
     * @param request request
     * @param id      id
     * @param name    姓名
     * @return MessageResponse
     */
    @RequestMapping(value = "/header/swagger", method = RequestMethod.GET)
    @ApiOperation(value = "获取Swagger列表", notes = "目前一次全部取，不分页")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "name", value = "姓名", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    public MessageResponse headerSwagger(HttpServletRequest request, Integer id, String name) {
        String token = request.getHeader("token");

        String accessToken = request.getHeader("accessToken");
        System.out.println(accessToken);

        SwaggerEntity obj = new SwaggerEntity();
        obj.setName(name + " " + token);
        obj.setId(id);
        return ResponseUtil.success(obj);
    }

    /**
     * 对象的方式执行返回
     * 在使用对象封装参数进行传参时，需要在该对象添加注解，将其注册到swagger中
     * 注意： 在后台采用对象接收参数时，Swagger自带的工具采用的是JSON传参，测试时需要在参数上加入@RequestBody,正常运行采用form或URL提交时候请删除
     *
     * @param obj 对象
     * @return MessageResponse
     */
    @RequestMapping(value = "/swagger", method = RequestMethod.POST)
    @ApiOperation(value = "Swagger信息", notes = "执行返回信息")
    public MessageResponse addSwagger(SwaggerEntity obj) {

        if (obj == null || obj.getId() == null) {
            return ResponseUtil.error("120", "object is null");
        }

        obj.setName(obj.getName() + " 执行返回");
        return ResponseUtil.success(obj);
    }
}
