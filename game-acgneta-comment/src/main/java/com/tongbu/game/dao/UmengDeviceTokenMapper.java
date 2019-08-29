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

    @Select("SELECT * FROM UmengDeviceToken WHERE uid=#{uid} and status=1 order by insertTime desc limit 1")
    UmengDeviceTokenEntity findByUidLaster(@Param("uid") Integer uid);

    @Select("SELECT * FROM UmengDeviceToken WHERE uid=#{uid} and appSource=#{appSource} and status=1 order by insertTime desc limit 1")
    UmengDeviceTokenEntity findByUidAndSource(@Param("uid") Integer uid,@Param("appSource") Integer appSource);

    @Select("SELECT * FROM UmengDeviceToken WHERE uid=#{uid} and deviceToken=#{deviceToken} and status=1 limit 1;")
    UmengDeviceTokenEntity findByUidAndToken(@Param("uid") Integer uid,@Param("deviceToken") String deviceToken);

    @Insert("INSERT INTO UmengDeviceToken(uid,deviceToken,appSource) VALUES(#{uid},#{deviceToken},#{appSource})")
    @Options(useGeneratedKeys = true,keyColumn = "id")
    int insert(UmengDeviceTokenEntity umengDeviceTokenEntity);

    @Update("update UmengDeviceToken set updateTime=Now() where id=#{id}")
    int update(@Param("id") Integer id);

    @Select("SELECT * FROM UmengDeviceToken WHERE uid=#{uid} and status=1 order by updateTime desc")
    List<UmengDeviceTokenEntity> findByUid(@Param("uid") Integer uid);

    @Select("SELECT deviceToken FROM `UmengDeviceTokenRpt` where  appSource=#{appSource} limit #{page},#{pageSize} ")
    List<String> findByAppSource(@Param("appSource") Integer appSource,@Param("page") Integer page,@Param("pageSize") Integer pageSize);
}
