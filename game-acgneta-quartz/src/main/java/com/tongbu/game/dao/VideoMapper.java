package com.tongbu.game.dao;

import com.tongbu.game.entity.task.Act10107UrlEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author jokin
 * @date 2018/11/14 14:10
 */
@Mapper
public interface VideoMapper {

    @Select("SELECT id,vedioSource,`name`,url,pageUrlId,insertTime,`status` FROM `vedioAnimationsUrl` where `status`=1 ORDER BY insertTime;")
    List<Act10107UrlEntity> videoUrlList();

    @Insert("insert into vedioAnimationsReport(urlId,url,title,`view`,danmaku,pubdate,insertTime,`like`) " +
            "values(#{id},#{url},#{title},#{view},#{danmaku},#{pubdate},#{insertTime},#{like})")
    void insert(@Param("id") int id,@Param("url") String  url,@Param("title") String  title,@Param("view") int view,
                @Param("danmaku") int danmaku,@Param("pubdate") String  pubdate,@Param("insertTime") String  insertTime,@Param("like") int like);
}
