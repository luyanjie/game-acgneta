package com.tongbu.game.controller;

import com.tongbu.game.common.enums.UserActionEnum;
import com.tongbu.game.entity.dynamics.DynamicsNoShow;
import com.tongbu.game.entity.dynamics.DynamicsShow;
import com.tongbu.game.service.dynamics.IUserDynamicsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vip.xinba.core.ResponseUtil;
import vip.xinba.core.entity.MessageResponse;

/**
 * 玩家动态记录，添加、减少积分
 */
@RestController
@RequestMapping("/dynamics")
@Api(value = "DynamicsController", description = "捏他动态Controller", tags = {"捏他动态"})
public class DynamicsController {

    @Autowired
    IUserDynamicsService iUserDynamicsService;

    /**
     * 添加动态
     * http://localhost:8080/dynamics/insert?userActionEnum=REGISTER&uid=1&typeId=1&commentId=2&description=sd
     */
    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    @ApiOperation(value = "添加用户动态信息", notes = "返回JSON格式")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userActionEnum", value = "用户行为", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "uid", value = "用户id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "typeId", value = "当行为为 动画、问答、捏报相关时的id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "commentId", value = "评论id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "description", value = "动态描述", required = false, dataType = "string")
    })
    public MessageResponse insert(DynamicsShow dynamics) {
        // 如果没有传时间，取当前系统时间戳
        if (dynamics.getTimestamp() <= 0) {
            dynamics.setTimestamp(System.currentTimeMillis() / 1000);
        }
        DynamicsShow dynamicsA = iUserDynamicsService.insert(dynamics);
        return ResponseUtil.success(dynamicsA);
    }

    @RequestMapping(value = "/add/score", method = RequestMethod.GET)
    @ApiOperation(value = "添加用户积分", notes = "返回JSON格式")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userActionEnum", value = "用户行为", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "uid", value = "用户id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "typeId", value = "当行为为 动画、问答、捏报相关时的id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "commentId", value = "评论id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "description", value = "动态描述", required = false, dataType = "string")
    })
    public MessageResponse addScore(DynamicsNoShow dynamics)
    {
        // 如果没有传时间，取当前系统时间戳
        if (dynamics.getTimestamp() <= 0) {
            dynamics.setTimestamp(System.currentTimeMillis() / 1000);
        }
        DynamicsNoShow dynamicsA = iUserDynamicsService.addScore(dynamics);
        return ResponseUtil.success(dynamicsA);
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "根据Id删除动态", notes = "返回JSON格式")
    @ApiImplicitParam(paramType = "query", name = "id", value = "主键id", required = true, dataType = "string")
    public MessageResponse deleteById(String id) {
        iUserDynamicsService.deleteById(id);
        return ResponseUtil.success(true);
    }

    /**
     * http://localhost:8080/dynamics/delete/by/10/1010/1011
     */
    @RequestMapping(value = "/delete/by/{userActionEnum}/{uid}/{typeId}/{commentId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据用户Id分类id删除动态", notes = "返回JSON格式")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "uid", value = "用户id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "typeId", value = "当行为为 动画、问答、捏报相关时的id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "commentId", value = "评论id", required = true, dataType = "int")
    })
    public MessageResponse deleteByUid(@PathVariable("uid") int uid, @PathVariable("typeId") int typeId, @PathVariable("commentId") int commentId,
                                       @PathVariable("userActionEnum") UserActionEnum userActionEnum) {
        iUserDynamicsService.deleteByUidAndTypeIdAndCommentId(uid, typeId, commentId);
        return ResponseUtil.success(true);
    }

    @RequestMapping(value = "/find/{uid}", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户所有的动态信息", notes = "返回JSON格式")
    @ApiImplicitParam(paramType = "path", name = "uid", value = "用户id", required = true, dataType = "int")
    public MessageResponse findByUid(@PathVariable("uid") int uid) {
        return ResponseUtil.success(iUserDynamicsService.findByUid(uid));
    }

    @RequestMapping(value = "/page/find/{uid}/{page}/{size}", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户动态分页信息", notes = "返回JSON格式")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "uid", value = "用户id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "page", value = "页码，从1开始，1表示第一页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页数据", required = true, dataType = "int")
    })
    public MessageResponse findByUidPage(@PathVariable("uid") int uid, @PathVariable("page") int page, @PathVariable("size") int size) {
        if (page <= 0) page = 1;
        if (size <= 0) size = 10;
        return ResponseUtil.success(iUserDynamicsService.findByUid(uid, page, size));
    }

    /**
     * http://localhost:8080/dynamics/update/score
     */
    @RequestMapping(value = "/update/score", method = RequestMethod.GET)
    @ApiOperation(value = "更新用户积分，这个可以作为定时执行", notes = "返回JSON格式")
    public MessageResponse updateScore() {
        iUserDynamicsService.updateScore();
        return ResponseUtil.success(true);
    }
}
