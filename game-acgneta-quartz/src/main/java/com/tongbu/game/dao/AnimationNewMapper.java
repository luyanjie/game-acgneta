package com.tongbu.game.dao;

import com.tongbu.game.entity.task.act10110.GameNewEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author jokin
 * @date 2019/1/15 17:36
 */
@Mapper
public interface AnimationNewMapper {

    /**
     *
     * 是否存在已经抓取的信息
     * @param moduleId 所属模块id，对应gameAnimationsResources_Category中的id
     * @param webUrlId 抓取网页对应的id
     * @return int
     * */
    @Select("select IFNULL(MAX(Id),0) as id from AnimationNews where moduleId=#{moduleId} and webUrlId=#{webUrlId}")
    int select(@Param("webUrlId") int webUrlId,@Param("moduleId") int moduleId);

    @Insert("insert into AnimationNews(Title,BriefContent,Img,imgWeb,Author,Status,PublishTime,moduleId,webUrl,webUrlId) " +
            "values(#{title},#{about},#{img},#{imgWeb},#{author},#{status},#{pushTime},#{moduleId},#{webUrl},#{webUrlId})")
    @Options(useGeneratedKeys = true,keyColumn = "id")
    int insertNews(GameNewEntity gameNewEntity);

    /**
     * 更新状态
     * @param status 状态
     * @param id id
     */
    @Update("update AnimationNews set Status=#{status} where id=#{id}")
    int updateStatus(@Param("status") int status,@Param("id") int id);

    @Update("update AnimationNews set Author=#{author} where id=#{id}")
    int updateAuthor(@Param("id") int id,@Param("author") String  author);

    /**
     * 获取详细信息失败的新闻内容
     *
     * @return List
     */
    @Select("select * from AnimationNews where Status=-1 and moduleId>7;  ")
    List<GameNewEntity> undoneList();

    /**
     * @param newId, 新闻id
     * @param content 新闻内容
     * @return int 影响行数
     * @author jokin
     * @date 2019/1/16 9:38
    **/
    @Insert("insert into AnimationNews_Content(NewsId,Content) values(#{newId},#{content})")
    int insertNewsContent(@Param("newId") int newId,@Param("content") String content);

}
