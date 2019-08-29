package com.tongbu.game.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface GameAnimationsOneWeekBaseMapper {

    /**
     * 获取最大的id
     * @return int
     */
    @Select("select Id from gameAnimationsOneWeekBase where status=1 order by id DESC limit 1")
    int getMaxId();

    /**
     * 更新集数
     * @param baseId 最大id
     * @param curWeek 星期几
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return int
     */
    @Update("update gameAnimationsOneWeek set curWord=CurWord+1,UpdateTime=NOW() where baseId=#{baseId} and status=1" +
            " and playDate<=NOW() and playWeek=#{curWeek}  and PlayTime>=#{beginTime} and PlayTime<#{endTime};")
    int updateNum(@Param("baseId") int baseId,@Param("curWeek") int curWeek,@Param("beginTime") String beginTime,@Param("endTime") String endTime);
}
