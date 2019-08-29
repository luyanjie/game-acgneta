package com.tongbu.game.dao;

import com.tongbu.game.entity.task.act2037.ShareDownloadUrlEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author jokin
 * @date 2018/11/19 18:23
 */
@Mapper
public interface ShareDownloadUrlMapper {

    @SelectProvider(type = ShareDownloadUrlSqlProvider.class,method = "list")
    List<ShareDownloadUrlEntity> list(@Param("sku") String  sku,@Param("regisndesc") String  regisndesc);

    /**
     * 执行更新语句
     * */
    @UpdateProvider(type = ShareDownloadUrlSqlProvider.class,method = "execute")
    int execute(@Param("teamName") String teamName,@Param("startTime") String startTime,@Param("active") int active);
}
