package com.tongbu.game.config;

import com.tongbu.game.common.enums.DataSourceType;

/**
 * @author jokin
 * @date 2018/9/29 13:58
 * <p>
 * 动态数据源的上下文
 */
public class JdbcContextHolder {
    private final static ThreadLocal<String> local = new ThreadLocal<>();

    public static void putDataSource(String name) {
        local.set(name);
    }

    public static String getDataSource() {
        String dbName = local.get();
        return dbName != null ? dbName : DataSourceType.Master.getName();
    }
}
