package com.tongbu.game.dao;

import com.tongbu.game.entity.UmengDeviceTokenEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author jokin
 * @date 2018/9/29 15:46
 */
@Mapper
public interface UmengDeviceTokenMapper {

    @Select("SELECT * FROM UmengDeviceToken WHERE uid=#{uid} and appSource=#{appSource} and status=1")
    UmengDeviceTokenEntity findByUidAndSource(@Param("uid") Integer uid,@Param("appSource") Integer appSource);

    @Select("SELECT * FROM UmengDeviceToken WHERE uid=#{uid} and deviceToken=#{deviceToken} and status=1")
    UmengDeviceTokenEntity findByUidAndToken(@Param("uid") Integer uid,@Param("deviceToken") String deviceToken);

    @Insert("INSERT INTO UmengDeviceToken(uid,deviceToken,appSource) VALUES(#{uid},#{deviceToken},#{appSource})")
    @Options(useGeneratedKeys = true,keyColumn = "id")
    int insert(UmengDeviceTokenEntity umengDeviceTokenEntity);

    @Select("SELECT * FROM UmengDeviceToken WHERE uid=#{uid} and status=1")
    List<UmengDeviceTokenEntity> findByUid(@Param("uid") Integer uid);
}
