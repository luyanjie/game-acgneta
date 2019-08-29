package com.tongbu.game.dao;

import com.tongbu.game.entity.task.act10111.GameAnimationsEntity;
import com.tongbu.game.entity.task.act10701.RunEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GameAnimationsMapper {
    /**
     * BGM分数更新获取数据
     * @param id 当前id
     * @return list
     */
    @Select("SELECT id,source,`status` FROM `gameAnimations`  where `Status`=1 and id>#{id} order by id LIMIT 100;  ")
    List<GameAnimationsEntity> gameAnimationsSmallList(@Param("id") int id);

    @Update("update gameAnimations set Score=#{bgmScore} where id=#{id};" +
            "update gameAnimationsScore set DisplayScore=#{bgmScore} where AnimationId=#{id} and ScoreCount<10;")
    int updateScore(@Param("id") int id,@Param("bgmScore") double bgmScore);

    /**
     * 人气榜
     */
    @Select("SELECT a.Id,b.ScoreCount as count\n" +
            "FROM `gameAnimations` a\n" +
            "LEFT JOIN gameAnimationsScore b on a.Id=b.AnimationId\n" +
            "where a.`Status`=1 and a.FirstPlayTime>date_add(NOW(), interval -1 YEAR)\n" +
            "ORDER BY b.ScoreCount DESC,a.FirstPlayTime desc\n" +
            "LIMIT 50")
    List<RunEntity> runCollection();

    @Insert("insert into gameAnimationRankByCollection(AnimationId,Count,orderBy,Type) values(#{animationId},#{count},#{orderBy},0)")
    int insertRunCollection(@Param("animationId") int animationId,@Param("count") int count,@Param("orderBy") int orderBy);

    @Delete("delete from gameAnimationRankByCollection where type=2;")
    int deleteRunCollection();

    @Update("update gameAnimationRankByCollection set Type=2 where Type=1;" +
            "update gameAnimationRankByCollection set Type=1 where Type=0")
    int updateRunCollection();
}
