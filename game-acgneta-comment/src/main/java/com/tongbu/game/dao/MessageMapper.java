package com.tongbu.game.dao;

import com.tongbu.game.entity.AnimationMessageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author jokin
 * @date 2018/9/29 15:02
 *
 * 消息获取
 */
@Mapper
public interface MessageMapper {
    @Select("SELECT id,title,content,linkUrl,msgType,source,status,insertTime,fromUid,toUid,commentId,rewardScore,grade,typeSource FROM AnimationMessages WHERE id=#{id}")
    AnimationMessageEntity findById(@Param("id") Integer id);
}
