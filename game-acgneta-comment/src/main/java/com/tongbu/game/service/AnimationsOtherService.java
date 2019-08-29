package com.tongbu.game.service;

import com.tongbu.game.dao.AnimationsOtherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AnimationsOtherService {

    @Autowired
    AnimationsOtherMapper otherMapper;

    @Async
    public void updateLoveAssociateId(int id, String associateId){
        otherMapper.updateLove(associateId,id);
    }

    @Async
    public void updateCommentAssociateId(int id, String associateId){
        otherMapper.updateComment(associateId,id);
    }

    @Async
    public void updateQuestionAssociateId(int id,String associateId)
    {
        otherMapper.updateQuestion(associateId,id);
    }
}
