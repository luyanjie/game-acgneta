package com.tongbu.game.controller;

import com.tongbu.game.entity.AnimationCommentsEntity;
import com.tongbu.game.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jokin
 * @date 2018/10/17 17:24
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;
    /**
     * 获取动画评论信息
     * @param id 评论id
     */
    @RequestMapping("/{id}")
    public AnimationCommentsEntity detail(@PathVariable("id") Integer id)
    {
        return commentService.detail(id);
    }
}
