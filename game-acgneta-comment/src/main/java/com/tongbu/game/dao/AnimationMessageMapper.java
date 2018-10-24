package com.tongbu.game.dao;

import com.tongbu.game.entity.AnimationMessageEntity;
import com.tongbu.game.entity.Request.AnimationMessageRequest;
import org.apache.ibatis.annotations.*;

/**
 * @author jokin
 * @date 2018/10/8 15:38
 */
@Mapper
public interface AnimationMessageMapper {

    @Select("SELECT id,title,content,linkUrl,msgType,source,status,insertTime,fromUid,toUid,commentId,rewardScore,grade,typeSource,typeId FROM AnimationMessages WHERE Id=#{id}")
    AnimationMessageEntity findById(@Param("id") Integer id);

    /*@Insert("INSERT INTO AnimationMessages(Title,Content,MsgType,Source,LinkUrl,StaffName,fromUid,toUid,commentId,RewardScore,Grade,typeSource,typeId) " +
            "VALUES(#{title},#{message),1,#{source},#{linkUrl},'system',#{fromUid},#{toUid},#{commentId},#{rewardScore},#{grade},#{typeSource},#{typeId})")*/

    @Insert("INSERT INTO AnimationMessages(Title,Content,MsgType,Source,LinkUrl,StaffName,fromUid,toUid,commentId,RewardScore,Grade,typeSource,typeId) " +
            "VALUES(#{title},#{message},1,#{source},#{linkUrl},'system',#{fromUid},#{toUid},#{commentId},#{rewardScore},#{grade},#{typeSource},#{typeId})")
    @Options(useGeneratedKeys = true,keyColumn = "id")
    int insertMessage(AnimationMessageRequest request);
}
