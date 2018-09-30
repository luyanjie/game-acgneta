package com.tongbu.game.dao;

import com.tongbu.game.entity.NewsCommentsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author jokin
 * @date 2018/9/29 15:39
 */
@Mapper
public interface NewsCommentsMapper {

    @Select("SELECT id,newsId,content,fromUid,toUid,parentId,refContent,likes,dislikes,replyCount,status,insertTime,updateTime,source FROM NewsComments WHREE id=#{id}")
    NewsCommentsEntity findById(@Param("id") Integer id);
}
