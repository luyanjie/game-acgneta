package com.tongbu.game.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * 账号相关更新
 */
@Mapper
public interface AccountMapper {

    /**
     * 更新积分
     * @param list 用户与积分
     */
    @UpdateProvider(type = AccountSqlProvider.class,method = "updateScore")
    void updateScore(@Param("list") List<String> list);
}
