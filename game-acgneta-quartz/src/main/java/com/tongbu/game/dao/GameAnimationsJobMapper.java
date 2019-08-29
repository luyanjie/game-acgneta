package com.tongbu.game.dao;

import com.tongbu.game.entity.JobEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GameAnimationsJobMapper {

    /**
     * 根据任务名称返回任务信息
     * @param jobName 任务名称
     * @return job
     */
    @Select("select *,'acgneta' as jobGroup from gameAnimationsJob where jobName=#{jobName} limit 1")
    JobEntity selectOne(@Param("jobName") String jobName);
}
