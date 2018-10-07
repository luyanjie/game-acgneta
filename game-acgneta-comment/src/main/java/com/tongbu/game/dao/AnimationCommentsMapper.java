package com.tongbu.game.dao;

import com.tongbu.game.entity.AnimationCommentsEntity;
import com.tongbu.game.entity.AnimationMessageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author jokin
 * @date 2018/9/29 15:25
 */
@Mapper
public interface AnimationCommentsMapper {
    @Select("SELECT id,animationId,content,fromUid,toUid,parentId,refContent,isSpoiled,likes,dislikes,replyCount,plotScore," +
            "styleScore,cvScore,musicScore,status,insertTime,updateTime,isTop,isGood,source FROM AnimationComments WHERE id=#{id}")
    AnimationCommentsEntity findById(@Param("id") Integer id);
}
