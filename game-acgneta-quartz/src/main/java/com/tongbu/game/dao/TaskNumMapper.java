package com.tongbu.game.dao;

import com.tongbu.game.entity.task.act10109.TaskNumEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author jokin
 * @date 2018/12/27 10:31
 */
@Mapper
public interface TaskNumMapper {

    @Select("select * from taskNum where taskStatus=1 and taskType=#{taskType};")
    List<TaskNumEntity> list(@Param("taskType") Integer taskType);

    @Update("update taskNum set maxId=#{maxId} where id=#{id};")
    int update(@Param("id") int id, @Param("maxId") Integer maxId);
}
