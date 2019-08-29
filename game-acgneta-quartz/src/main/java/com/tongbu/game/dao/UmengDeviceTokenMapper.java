package com.tongbu.game.dao;

import com.tongbu.game.entity.task.act10109.UmengDeviceTokenEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author jokin
 * @date 2018/12/29 18:11
 */
@Mapper
public interface UmengDeviceTokenMapper {

    @Select("select id,uid,deviceToken,appSource,insertTime from UmengDeviceToken where status=1 and id>#{maxId};")
    List<UmengDeviceTokenEntity> list(@Param("maxId") Integer maxId);

    @Insert("insert into UmengDeviceTokenRpt(deviceToken,appSource,insertTime) values(#{deviceToken},#{appSource},#{insertTime})")
    int insert(@Param("deviceToken") String  deviceToken, @Param("appSource") int appSource,@Param("insertTime") String  insertTime);
}
