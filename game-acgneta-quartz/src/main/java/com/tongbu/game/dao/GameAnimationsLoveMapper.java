package com.tongbu.game.dao;

import com.tongbu.game.entity.task.act10109.GameAnimationsLoveEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author jokin
 * @date 2018/12/27 11:13
 *
 * 订阅  追番-看完
 */
@Mapper
public interface GameAnimationsLoveMapper {

    @Select("select a.id,a.uid,a.animationId,b.name as animationName,a.subscribe from gameAnimationsLove a " +
            "left join gameAnimations b on a.animationId=b.Id " +
            "where a.subscribe=1 and a.animationId=#{animationId} and a.status=1;")
    List<GameAnimationsLoveEntity> list(@Param("animationId") int animationId);
}
