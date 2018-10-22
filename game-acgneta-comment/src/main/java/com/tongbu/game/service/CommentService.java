package com.tongbu.game.service;

import com.alibaba.fastjson.JSONObject;
import com.tongbu.game.dao.AnimationCommentsMapper;
import com.tongbu.game.dao.NewsCommentsMapper;
import com.tongbu.game.entity.AnimationCommentsEntity;
import com.tongbu.game.entity.NewsCommentsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    AnimationCommentsMapper animation;

    @Autowired
    NewsCommentsMapper news;

    /**
     * umeng comments 组合
     *
     * @param type      0：动画 1：咨询
     * @param commentId 评论id
     */
    public JSONObject comment(int type, int commentId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", type);
        jsonObject.put("commentId", commentId);
        if (type == 0) {
            AnimationCommentsEntity commentsEntity = animation.findById(commentId);
            if (commentsEntity == null) {
                return jsonObject;
            }
            jsonObject.put("content", commentsEntity.getContent());
            jsonObject.put("fromUid", commentsEntity.getFromUid());
            jsonObject.put("source", commentsEntity.getSource());
            jsonObject.put("id", commentsEntity.getAnimationId());
        } else {
            NewsCommentsEntity commentsEntity = news.findById(commentId);
            if (commentsEntity == null) {
                return jsonObject;
            }
            jsonObject.put("content", commentsEntity.getContent());
            jsonObject.put("fromUid", commentsEntity.getFromUid());
            jsonObject.put("source", commentsEntity.getSource());
            jsonObject.put("id", commentsEntity.getNewsId());
        }
        return jsonObject;
    }

    public AnimationCommentsEntity detail(Integer id) {
        return animation.findById(id);
    }
}
