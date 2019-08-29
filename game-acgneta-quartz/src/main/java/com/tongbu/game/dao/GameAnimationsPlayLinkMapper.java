package com.tongbu.game.dao;

import com.tongbu.game.entity.task.act10106.AnimationsUpdatePlanEntity;
import com.tongbu.game.entity.task.act10109.GameAnimationsPlayLinkEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author jokin
 * @date 2018/12/27 10:51
 */
@Mapper
public interface GameAnimationsPlayLinkMapper {

    @Select("select id,animationId,platformCode,platformName,episode,orderBy from gameAnimationsPlayLink where Id>#{maxId} and source=1 ORDER BY AnimationId,OrderBy DESC;")
    List<GameAnimationsPlayLinkEntity> list(@Param("maxId") Integer maxId);

    /**
     * 重跑已过更新日期(当前时间)未更新的
     * @param today 指定的日期，一般是今天
     * @return List
     */
    @SelectProvider(type = GameAnimationsUpdatePlanSqlProvider.class,method = "updatePlanWeek")
    List<AnimationsUpdatePlanEntity> updatePlanWeek(@Param("today") String today);

    /**
     * 当天要更新
     * @param today 指定的日期，一般是今天
     * @return List
     */
    @SelectProvider(type = GameAnimationsUpdatePlanSqlProvider.class,method = "updatePlanToday")
    List<AnimationsUpdatePlanEntity> updatePlanToday(@Param("today") String today);
}
