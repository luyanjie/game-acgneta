package com.tongbu.game.dao;

import com.tongbu.game.entity.AnimationMessageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author jokin
 * @date 2018/10/8 15:38
 */
@Mapper
public interface AnimationMessageMapper {

    @Select("SELECT id,title,content,linkUrl,msgType,source,status,insertTime,fromUid,toUid,commentId,rewardScore,grade,typeSource,typeId FROM AnimationMessages WHERE Id=#{id}")
    AnimationMessageEntity findById(@Param("id") Integer id);
}
