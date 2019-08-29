package com.tongbu.game.dao;

import com.tongbu.game.entity.task.act10110.GameResourcesCategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author jokin
 * @date 2019/1/15 15:33
 */
@Mapper
public interface GameResourcesCategoryMapper {

    @Select("select id,`name`,icon,module from gameAnimationsResources_Category where `status`=1 and module=#{module}")
    List<GameResourcesCategoryEntity> list(@Param("module") String module);
}
